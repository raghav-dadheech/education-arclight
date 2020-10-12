package com.study.question.entity.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.study.exceptions.FacadeException;
import com.study.exceptions.ServiceException;
import com.study.question.converter.ImagesConvertor;
import com.study.question.converter.QuestionFilterConvertor;
import com.study.question.converter.QuestionSettingConvertor;
import com.study.question.converter.QuestionnaireConverter;
import com.study.question.dao.SearchParam;
import com.study.question.dto.ImagesDto;
import com.study.question.dto.LongListDto;
import com.study.question.dto.QuestionCounterDto;
import com.study.question.dto.QuestionListFilter;
import com.study.question.dto.QuestionListFilterDto;
import com.study.question.dto.QuestionSettingDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.QuestionnaireListDto;
import com.study.question.entity.Images;
import com.study.question.entity.QuestionCounter;
import com.study.question.entity.QuestionImages;
import com.study.question.entity.QuestionSetting;
import com.study.question.entity.QuestionSolutions;
import com.study.question.entity.QuestionnaireModel;
import com.study.service.AmazonClient;
import com.study.question.service.ImagesService;
import com.study.question.service.QuestionCounterService;
import com.study.question.service.QuestionImagesService;
import com.study.question.service.QuestionSettingService;
import com.study.question.service.QuestionSolutionsService;
import com.study.question.service.QuestionnaireService;

@Component
@Transactional
public class DefaultQuestionnaireFacade implements QuestionnaireFacade {
	
	private final String TEMP_QUES_IMG_STRING = "temp_ques_img_";
	
	@Value("${amazonProperties.question_images_bucketName}")
	private String question_images_bucketName;

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private QuestionnaireConverter questionnaireConverter;

	@Autowired
	private QuestionCounterService questionCounterService;

	@Autowired
	private QuestionSettingService questionSettingService;

	@Autowired
	private QuestionSettingConvertor questionSettingConvertor;
	
	@Autowired
	private QuestionFilterConvertor questionFilterConvertor;
	
	@Autowired
	private ImagesService imagesService;
	
	@Autowired
	private QuestionImagesService questionImagesService;
	
	@Autowired
	private ImagesConvertor imagesConvertor;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private QuestionSolutionsService questionSolutionsService;

	@Override
	public QuestionnaireDto addQuestionnaire(QuestionnaireDto questionnaireDto) throws FacadeException {
		try {
			QuestionnaireModel questionnaireModel = new QuestionnaireModel();
			questionnaireConverter.convert(questionnaireDto, questionnaireModel);
			boolean updateCounter = questionnaireDto.getId() == 0;
			List<TemporaryImage> images = saveQuestionImages(questionnaireModel);
			
			questionnaireService.addQuestion(questionnaireModel);
			
			if(images.size() > 0) {
				// upload question images to s3.
				deleteExistingImages(questionnaireModel);
				saveQuestionImages(questionnaireModel, images);
				
				// update question with image urls
				String q = questionnaireModel.getQuestion();
				for(int i = 0;i< images.size();i++) {
					TemporaryImage img = images.get(i);
					q = q.replace(img.key, "src="+img.url+"");
				}
				questionnaireModel.setQuestion(q);
				questionnaireService.addQuestion(questionnaireModel);
			}
//			if(questionnaireDto.getSolution() != null)
//				uploadSolutions(questionnaireDto.getSolution(), questionnaireModel.getId());
			// updating question setting
			QuestionSettingDto questionSettingDto = questionnaireDto.getQuestionSettings();
			QuestionSetting questionSetting = questionSettingConvertor.convert(questionSettingDto,
					new QuestionSetting());
			questionSettingService.updateQuestionSetting(questionSetting);
			if (updateCounter)
				questionCounterService.updateQuestionCounter();
			questionnaireConverter.convert(questionnaireModel, questionnaireDto);
			return questionnaireDto;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to add question");
		} catch (Exception e) {
			// handle amazon upload images failed cases here
			e.printStackTrace();
			throw new FacadeException(e.getMessage());
		}
	}


	@Override
	public QuestionnaireListDto listAll(final QuestionListFilterDto filterDto) throws FacadeException {
		QuestionnaireListDto response = new QuestionnaireListDto();
		try {
			QuestionListFilter filter = questionFilterConvertor.convert(filterDto, new QuestionListFilter());
			SearchParam searchParam = new SearchParam();
			searchParam.getSearchMap().put("enabled", true);
			if(filter.getSubject() != null)
				searchParam.getSearchMap().put("subject", filter.getSubject());
			if(filter.getChapter() != null)
				searchParam.getSearchMap().put("chapter", filter.getChapter());
			if(filter.getQuestionType() != null)
				searchParam.getSearchMap().put("questionType", filter.getQuestionType());
			if(filter.getDifficultyLevel() != null)
				searchParam.getSearchMap().put("difficultyLevel", filter.getDifficultyLevel());
			if(filter.getApproval() > 0)
				searchParam.getSearchMap().put("approved", filter.getApproval() == 1);
			
			List<QuestionnaireModel> questions = questionnaireService.listAll(searchParam);
			List<QuestionnaireDto> questionDtos = new ArrayList<>(0);
			questions.forEach(source -> {
				QuestionnaireDto target = new QuestionnaireDto();
				questionnaireConverter.convert(source, target);
				questionDtos.add(target);
			});
			Map<Long, Map<String, ImagesDto>> questionImages = setQuestionInages(questionDtos);
			response.setQuestions(questionDtos);
			response.setQuestionImages(questionImages);
			return response;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public QuestionnaireListDto listAllTrash() throws FacadeException {
		QuestionnaireListDto response = new QuestionnaireListDto();
		try {
			List<QuestionnaireModel> questions = questionnaireService.listAllTrash();
			List<QuestionnaireDto> questionDtos = new ArrayList<>(0);
			questions.forEach(source -> {
				QuestionnaireDto target = new QuestionnaireDto();
				questionnaireConverter.convert(source, target);
				questionDtos.add(target);
			});
			Map<Long, Map<String, ImagesDto>> questionImages = setQuestionInages(questionDtos);
			response.setQuestions(questionDtos);
			response.setQuestionImages(questionImages);
			return response;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public QuestionnaireDto getQuestion(final Long questionId) throws FacadeException {
		try {

			QuestionnaireModel source = questionnaireService.getQuestion(questionId);
			QuestionnaireDto target = new QuestionnaireDto();
			questionnaireConverter.convert(source, target);
			return target;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public QuestionCounterDto getQuestionCounter() throws FacadeException {
		try {

			List<QuestionCounter> counters = questionCounterService.listAll();
			QuestionCounterDto target = new QuestionCounterDto();
			target.setCounter(counters.get(0).getCounter());
			return target;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new FacadeException("Failed to fetch question", e);
		}
	}

	@Override
	public void deleteQuestion(List<Long> questionList) throws FacadeException {
		try {
			for (int i = 0; i < questionList.size(); i++)
				questionnaireService.deleteQuestion(questionList.get(i));
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}

	@Override
	public void restoreQuestion(List<Long> questionList) throws FacadeException {
		try {
			for (int i = 0; i < questionList.size(); i++)
				questionnaireService.restoreQuestion(questionList.get(i));
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}
	
	@Override
	public void updateApprovalStatus(LongListDto object) throws FacadeException {
		try {
			List<Long> questionList = object.getList();
			for (int i = 0; i < questionList.size(); i++)
				questionnaireService.updateApprovalStatus(questionList.get(i), object.isApprovalStatus());
		} catch (ServiceException e) {
			throw new FacadeException(e.getMessage());
		}
	}
	
	@Override
	public Map<Long, Map<String, ImagesDto>> setQuestionInages(List<QuestionnaireDto> questions) throws FacadeException {
		Map<Long, Map<String, ImagesDto>> imageMap = new LinkedHashMap<>();
		try {
			long image_counter = 1;
			for (int i = 0; i < questions.size(); i++) {
				String question = questions.get(i).getQuestion();
				while (question.contains("question_image_")) {
					String temp = question.substring(question.indexOf("question_image_"),
							question.indexOf(" ", question.indexOf("question_image_")));
					String[] tempArray = temp.split("_");
					long imageId = Long.parseLong(tempArray[2]);
					String newString = "replace_image_" + image_counter++; 
					question = question.replace(temp, newString);
					Images image = imagesService.get(imageId);
					ImagesDto imageDto = imagesConvertor.convert(image, new ImagesDto());
					if(!imageMap.containsKey(questions.get(i).getId()))
						imageMap.put(questions.get(i).getId(), new LinkedHashMap<>());
					imageMap.get(questions.get(i).getId()).put(newString, imageDto);
				}
				questions.get(i).setQuestion(question);

			} 
		} catch (Exception e) {
			throw new FacadeException(e.getMessage());
		}
		return imageMap;
	}
	
	public static void main(String[] args) throws ServiceException {
		
//		String q = "<img src=\"data:image/jpeg;base64,adbjhdjahjdsbahbdjha\">jhjbjhb<img src=\"data:image/png;base64,adbjhdjahjdsbwewrweew231321ahbdjha\">";
//		QuestionnaireModel question = new QuestionnaireModel();
//		question.setQuestion(q);
//		new DefaultQuestionnaireFacade().saveQuestionImages(question);
		
//		String question = "ajda question_image_1202 lkmal question_image_2102 adnkjnkjaw";
//		System.out.println(question);
//		long image_counter = 1;
//		while(question.contains("question_image_")) {
//			String temp = question.substring(question.indexOf("question_image_"), question.indexOf(" ", question.indexOf("question_image_")));
//			question = question.replace(temp, "replace_image_"+image_counter++);
//			String[] tempArray = temp.split("_");
//			long imageId = Long.parseLong(tempArray[2]);
//			System.out.println(imageId);
//			System.out.println(temp);
//			System.out.println(question);
////			break;
//			
//		}
		
		String string = "abcd.png";
		System.out.println(string.substring(string.indexOf(".") + 1));
		 
	}
	
	
	private List<TemporaryImage> saveQuestionImages(QuestionnaireModel question) throws ServiceException{
		String q = question.getQuestion();
		List<TemporaryImage> list = new ArrayList<>();
		int counter = 1;
		while(q.contains("data:image")) {
			String str = q.substring(q.indexOf("src=\"data:image"), q.indexOf("\"", q.indexOf("src=\"data:image")+15)+1);
			String[] temp = str.substring(str.indexOf("src=\"")+5, str.indexOf("\"", str.indexOf("src=\"")+5)).split(",");
			String imageFormate = temp[0].substring(temp[0].indexOf("/")+1, temp[0].indexOf(";"));
			String imgString = temp[1];
			byte[] questionImage = Base64.decodeBase64(imgString.getBytes());
			String key = TEMP_QUES_IMG_STRING + counter++;
			q = q.replace(str, key);
			list.add(new TemporaryImage(key, questionImage, imageFormate));
		}
		question.setQuestion(q);
		return list;
	}
	
	private void saveQuestionImages(QuestionnaireModel question, List<TemporaryImage> images) throws Exception {
		try {
			for (int i = 0; i < images.size(); i++) {
				TemporaryImage img = images.get(i);
				File tempFile;
				tempFile = File.createTempFile("QUES_" + question.getId() + "_IMG_" + new Date().getTime(), "." + img.imageFormat);
				FileOutputStream imageOutFile = new FileOutputStream(tempFile);
				imageOutFile.write(img.image);
				img.url = amazonClient.uploadQuestionImage(question_images_bucketName, tempFile);
				imageOutFile.close();
				tempFile.delete();
				
				QuestionImages qImg = new QuestionImages();
				qImg.setQuestion(question);
				qImg.setImageUrl(img.url);
				questionImagesService.add(qImg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to upload images on aws s3.");
		}
	}
	

	private void deleteExistingImages(QuestionnaireModel question) throws Exception {
		// remove previous entries for the question images
		SearchParam searchParam = new SearchParam();
		searchParam.getSearchMap().put("question", question);
		try {
			List<QuestionImages> images = questionImagesService.listQUestionImages(searchParam);
			for( int i = 0 ; i < images.size() ; i++ ) {
				amazonClient.deleteFileFromS3Bucket(question_images_bucketName, images.get(i).getImageUrl());
			}
			questionImagesService.remove(searchParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to delete existing images on aws s3 or table question_image");
		}
	}
	
	@Override
	public QuestionnaireDto uploadSolutions(MultipartFile file,String fileName, Long questionId) throws FacadeException {
		File tempFile;
		try {
			QuestionnaireModel question = questionnaireService.getQuestion(questionId);
			deleteExistingSolutionImages(question);
			
			
			String imageFormate = fileName.substring(fileName.indexOf(".") + 1);
			tempFile = File.createTempFile("QUES_SOLUTION_" + questionId + "_IMG_" + new Date().getTime(), "." + imageFormate);
			FileOutputStream imageOutFile = new FileOutputStream(tempFile);
			imageOutFile.write(file.getBytes());
			String url = amazonClient.uploadQuestionImage(question_images_bucketName, tempFile);
			System.out.println("Solution url :- " + url);
			QuestionSolutions image = new QuestionSolutions();
			image.setImageUrl(url);
			image.setQuestion(questionnaireService.getQuestion(questionId));
			questionSolutionsService.add(image);
			imageOutFile.close();
			tempFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FacadeException("Failed to upload images on aws s3.");
		}
		return getQuestion(questionId);
	}
	
	private void deleteExistingSolutionImages(QuestionnaireModel question) throws Exception {
		// remove previous entries for the question images
		SearchParam searchParam = new SearchParam();
		searchParam.getSearchMap().put("question", question);
		try {
			List<QuestionSolutions> images = questionSolutionsService.listSolutionImages(searchParam);
			for( int i = 0 ; i < images.size() ; i++ ) {
				amazonClient.deleteFileFromS3Bucket(question_images_bucketName, images.get(i).getImageUrl());
			}
			questionSolutionsService.remove(searchParam);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to delete existing images on aws s3 or table question_image");
		}
	}
	
	@Override
	public QuestionnaireDto deleteSolution(Long questionId) throws FacadeException {
		try {
			QuestionnaireModel question = questionnaireService.getQuestion(questionId);
			deleteExistingSolutionImages(question);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FacadeException("Failed to delete solutions");
		}
		return getQuestion(questionId);
	}
	
	
	private class TemporaryImage {
		
		String key;
		
		byte[] image;
		
		String imageFormat;
		
		String url;

		public TemporaryImage(String key, byte[] image, String imageFormat) {
			super();
			this.key = key;
			this.image = image;
			this.imageFormat = imageFormat;
		}
		
	}
	
}
