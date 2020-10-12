package com.study.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.study.enums.ExamPaperType;
import com.study.enums.QuestionType;
import com.study.question.dto.ExamDto;
import com.study.question.entity.Chapter;
import com.study.question.entity.QuestionnaireModel;

@Component
public class ExamFilter {

	private final int numberQuestionPercent = 20;

	private final int MultiSelectQuestionPercent = 20;

	private Map<Long, List<QuestionnaireModel>> filterMap;

	private Map<Long, Long> numberChapters = new LinkedHashMap<>();

	private Map<Long, Long> multipleSelectChapters = new LinkedHashMap<>();

	private Map<Long, ChapterClass> processingMap;

	private ExamDto examDto;

	private ParentClass parentObject;
	
	private final int MAX_QUESTIONS = 100;

	public List<QuestionnaireModel> filter(List<QuestionnaireModel> questionModals, ExamDto examDto) {

		if(examDto.getExamType() == ExamPaperType.PAPER)
			examDto.setPageSize(MAX_QUESTIONS);
		if(questionModals == null || questionModals.size() == 0)
			return questionModals;
		
		this.examDto = examDto;
		System.out.println("Filtering..............");
		System.out.println(examDto);
		Map<Long, List<QuestionnaireModel>> filterMap = questionModals.stream()
				.collect(Collectors.groupingBy(question -> {
					return question.getChapter().getId();
				}));
		System.out.println("Chapter Length  "+ filterMap.size());
		if(filterMap.size() == 0)
			return questionModals;
		filterChapters(filterMap, examDto.getPageSize());
		prepareParentObject();
		return assignQuestions();
	}

	private void filterChapters(Map<Long, List<QuestionnaireModel>> filterMap, int size) {
		System.out.println("size  "+size);
		if (filterMap.size() > size) {
			Set<Long> keys = filterMap.keySet();
			List<Long> keyList = new ArrayList<>(keys);
			Random rand = new Random();
			this.filterMap = new LinkedHashMap<>();
			for (int i = 0; i < size; i++) {
				int randomIndex = rand.nextInt(keyList.size());
				long key = keyList.get(randomIndex);
				this.filterMap.put(key, filterMap.get(key));
				keyList.remove(randomIndex);
			}
		} else {
			this.filterMap = filterMap;
		}
		System.out.println("Chapter Length  "+ this.filterMap.size());
		for (Entry<Long, List<QuestionnaireModel>> entry : this.filterMap.entrySet()) {
			long numberQuestions = entry.getValue().stream().filter(q -> {
				return q.getQuestionType().getName().equalsIgnoreCase(QuestionType.NUMBER.getValue());
			}).count();
			System.out.println(entry.getKey()+" -NQ-  "+numberQuestions);
			if(numberQuestions > 0)
				numberChapters.put(entry.getKey(), numberQuestions);
			
			long multipleSelectQuestions = entry.getValue().stream().filter(q -> {
				return q.getQuestionType().getName().equalsIgnoreCase(QuestionType.MULTIPLE_SELECT.getValue());
			}).count();
			System.out.println(entry.getKey()+" -MS-  "+multipleSelectQuestions);
			if(multipleSelectQuestions > 0)
				multipleSelectChapters.put(entry.getKey(), multipleSelectQuestions);
		}
		System.out.println(numberChapters);
		System.out.println(multipleSelectChapters);
	}

	private void prepareParentObject() {
		parentObject = new ParentClass();
		int totalQuestionsAvailable = 0;
		for(Entry<Long, List<QuestionnaireModel>> entry : filterMap.entrySet()) {
			totalQuestionsAvailable += entry.getValue().size();
		}
		if(examDto.getPageSize() <= totalQuestionsAvailable)
			parentObject.setTotalQuestions(examDto.getPageSize());
		else
			parentObject.setTotalQuestions(totalQuestionsAvailable);

		int numberQuestion = 0;
		int multipleSelectQuestions = 0;
		int singleSelectQuestion = 0;

		if (examDto.getQuestionTypes().size() > 0) {
			boolean isNumber = examDto.getQuestionTypes().stream().anyMatch(qt -> {
				return qt.getName().equalsIgnoreCase(QuestionType.NUMBER.getValue());
			});
			boolean isMulSelect = examDto.getQuestionTypes().stream().anyMatch(qt -> {
				return qt.getName().equalsIgnoreCase(QuestionType.MULTIPLE_SELECT.getValue());
			});
			if (isNumber && !isMulSelect) {
				numberQuestion = ((examDto.getPageSize() * numberQuestionPercent) / 100) * 2;
			} else if (!isNumber && isMulSelect) {
				multipleSelectQuestions = ((examDto.getPageSize() * MultiSelectQuestionPercent) / 100) * 2;
			} else if (isNumber && isMulSelect) {
				numberQuestion = (examDto.getPageSize() * numberQuestionPercent) / 100;
				multipleSelectQuestions = (examDto.getPageSize() * MultiSelectQuestionPercent) / 100;
			}
		}
		singleSelectQuestion = examDto.getPageSize() - numberQuestion - multipleSelectQuestions;
		parentObject.setMultipleSelectQuestions(multipleSelectQuestions);
		parentObject.setSingleSelectQuestions(singleSelectQuestion);
		parentObject.setNumberQuestions(numberQuestion);
		System.out.println(parentObject);
		prepareProcessingMap();
	}

	private void prepareProcessingMap() {
		processingMap = new LinkedHashMap<>();
		filterMap.forEach((chapterId, questions) -> {
			ChapterClass cc = new ChapterClass();
			processingMap.put(chapterId, cc);
		});
		assignTotalNumberOfQuestionsToChapter();
		assignSpecialQuestionsToChapter();
	}

	private void assignTotalNumberOfQuestionsToChapter() {
		int chapterWiseQuestion = parentObject.totalQuestions / filterMap.size();
		int chapterWiseQuestionBonus = parentObject.totalQuestions % filterMap.size();
		for(Entry<Long, ChapterClass> entry : processingMap.entrySet()) {
			long chapterId = entry.getKey();
			ChapterClass chapter = entry.getValue();
			int availableQuestions = filterMap.get(chapterId).size();
			if(availableQuestions<chapterWiseQuestion) {
				chapter.totalQuestions = availableQuestions;
				chapter.singleSelectQuestions = availableQuestions;
				chapterWiseQuestionBonus += chapterWiseQuestion - availableQuestions;
			} else {
				chapter.totalQuestions = chapterWiseQuestion;
				chapter.singleSelectQuestions = chapterWiseQuestion;				
			}
		}
		Set<Long> keys = processingMap.keySet();
		List<Long> keyList = new ArrayList<>(keys);
		Random rand = new Random();
		while(chapterWiseQuestionBonus > 0) {
			int randomIndex = rand.nextInt(keyList.size());
			long key = keyList.get(randomIndex);
			int availableQuestions = filterMap.get(key).size();
			if(availableQuestions > processingMap.get(key).totalQuestions) {
				processingMap.get(key).totalQuestions++;
				processingMap.get(key).singleSelectQuestions++;	
				chapterWiseQuestionBonus--;
			} else {
				
			}
		}
		/*
		 * for (int i = 0; i < chapterWiseQuestionBonus; i++) { int randomIndex =
		 * rand.nextInt(keyList.size()); long key = keyList.get(randomIndex);
		 * processingMap.get(key).totalQuestions++;
		 * processingMap.get(key).singleSelectQuestions++; }
		 */
		System.out.println(processingMap);
	}

	private void assignSpecialQuestionsToChapter() {
		assignNumberQuestionsToChapter();
		assignMultipleSelectQuestionsToChapter();
	}

	private void assignNumberQuestionsToChapter() {
		int numberQuestions = parentObject.getNumberQuestions();
		if (numberQuestions > 0) {
			
			
			for(Entry<Long, ChapterClass> entry : processingMap.entrySet()) {
				ChapterClass chapter = entry.getValue();
				if(chapter.totalQuestions == chapter.numberQuestions) {
					numberChapters.remove(entry.getKey());
				}
			}
			
			int remainingQuestions = 0;
			Random rand = new Random();
			Set<Long> numberKeys = numberChapters.keySet();
			List<Long> numberKeyList = new ArrayList<>(numberKeys);
			if(numberKeyList.size()>0) {
				for (int i = 0; i < numberQuestions; i++) {
					// CHECK POINT INFINITE
					if( numberKeyList.size() > 0 ) {
						int randomIndex = rand.nextInt(numberKeyList.size());
						long key = numberKeyList.get(randomIndex);
						processingMap.get(key).numberQuestions++;
						processingMap.get(key).singleSelectQuestions--;
						parentObject.numberQuestions--;
						numberChapters.put(key, numberChapters.get(key)-1);
						if(numberChapters.get(key) == 0 || processingMap.get(key).totalQuestions == processingMap.get(key).numberQuestions)
						{
							numberKeyList.remove(randomIndex);
							numberChapters.remove(key);
						}
					} else {
//						parentObject.multipleSelectQuestions += numberQuestions - i;
						remainingQuestions = numberQuestions - i;
						break;
					}
				}
			} else {
//				parentObject.multipleSelectQuestions += numberQuestions;
				remainingQuestions = numberQuestions;
			}
			if(remainingQuestions > 0)
				parentObject.multipleSelectQuestions += remainingQuestions;
		}
	}
	
	private void assignMultipleSelectQuestionsToChapter() {
		int multipleSelectQuestions = parentObject.getMultipleSelectQuestions();
		if (multipleSelectQuestions > 0) {
			
			for(Entry<Long, ChapterClass> entry : processingMap.entrySet()) {
				ChapterClass chapter = entry.getValue();
				if(chapter.totalQuestions == chapter.numberQuestions) {
					multipleSelectChapters.remove(entry.getKey());
				}
			}
			
			int remainingQuestions = 0;
			Random rand = new Random();
			Set<Long> multipleSelectKeys = multipleSelectChapters.keySet();
			List<Long> multipleSelectKeysList = new ArrayList<>(multipleSelectKeys);
			if(multipleSelectKeysList.size()>0) {
				for (int i = 0; i < multipleSelectQuestions; i++) {
					// CHECK POINT INFINITE
					if( multipleSelectKeysList.size() > 0 ) {
						int randomIndex = rand.nextInt(multipleSelectKeysList.size());
						long key = multipleSelectKeysList.get(randomIndex);
						processingMap.get(key).multipleSelectQuestions++;
						processingMap.get(key).singleSelectQuestions--;
						parentObject.multipleSelectQuestions--;
						multipleSelectChapters.put(key, multipleSelectChapters.get(key)-1);
						if(multipleSelectChapters.get(key) == 0 || processingMap.get(key).totalQuestions == processingMap.get(key).numberQuestions)
						{
							multipleSelectKeysList.remove(randomIndex);
							multipleSelectChapters.remove(key);
						}
					} else {
//						parentObject.multipleSelectQuestions += multipleSelectQuestions - i;
						remainingQuestions = multipleSelectQuestions - i;
						break;
					}
				}
			} else {
//				parentObject.multipleSelectQuestions += multipleSelectQuestions;
				remainingQuestions = multipleSelectQuestions;
			}
			if(remainingQuestions > 0 && numberChapters.size() > 0) {
				parentObject.numberQuestions += remainingQuestions;
				assignNumberQuestionsToChapter();
			}
		}
	}
	private List<QuestionnaireModel> assignQuestions() {
		List<QuestionnaireModel> newList = new ArrayList<>();
		List<QuestionnaireModel> sList = new ArrayList<>();
		List<QuestionnaireModel> nList = new ArrayList<>();
		List<QuestionnaireModel> mList = new ArrayList<>();
		
		Random rand = new Random();
		System.out.println("QUESTIONS ASSIGNMENTS");
		processingMap.forEach((chapterId, chapter)->{
			List<QuestionnaireModel> availableQuestions = filterMap.get(chapterId);
			System.out.println(chapter);
			
			// number questions
			
			int numberQuestions = chapter.numberQuestions;
			System.out.println(chapterId + " -NQ-  " + numberQuestions);
			List<QuestionnaireModel> tempList = availableQuestions.stream().filter(q->{
				return q.getQuestionType().getName().equalsIgnoreCase(QuestionType.NUMBER.getValue());
			}).collect(Collectors.toList());
			System.out.println("tempList.size :-  "+ tempList.size());
			for(int i = 0;i< numberQuestions && tempList.size() > 0;i++) {
				int randomIndex = rand.nextInt(tempList.size());
				nList.add(tempList.get(randomIndex));
				tempList.remove(randomIndex);
			}
//			Collections.shuffle(nList);
//			newList.addAll(tList);
			
			// multiple select questions
			
			int multipleSelectQuestions = chapter.multipleSelectQuestions;
			System.out.println(chapterId + " -MQ-  " + multipleSelectQuestions);
			tempList = availableQuestions.stream().filter(q->{
				return q.getQuestionType().getName().equalsIgnoreCase(QuestionType.MULTIPLE_SELECT.getValue());
			}).collect(Collectors.toList());
			System.out.println("tempList.size :-  "+ tempList.size());
			for(int i = 0;i< multipleSelectQuestions && tempList.size() > 0;i++) {
				int randomIndex = rand.nextInt(tempList.size());
				mList.add(tempList.get(randomIndex));
				tempList.remove(randomIndex);
			}
//			Collections.shuffle(mList);
//			newList.addAll(tList);
			
			// single select questions
			
			int singleSelectQuestions = chapter.singleSelectQuestions;
			System.out.println(chapterId + " -SQ-  " + singleSelectQuestions);
			tempList = availableQuestions.stream().filter(q->{
				return q.getQuestionType().getName().equalsIgnoreCase(QuestionType.SINGLE_SELECT.getValue());
			}).collect(Collectors.toList());
			System.out.println("tempList.size :-  "+ tempList.size());
			for(int i = 0;i< singleSelectQuestions && tempList.size() > 0;i++) {
				int randomIndex = rand.nextInt(tempList.size());
				sList.add(tempList.get(randomIndex));
				tempList.remove(randomIndex);
			}
//			Collections.shuffle(sList);
		});
		System.out.println("s list "+sList.size());
		System.out.println("m list "+mList.size());
		System.out.println("n list "+nList.size());
		newList.addAll(sList);
		newList.addAll(mList);
		newList.addAll(nList);		
		return newList;
	}

	@SuppressWarnings("unused")
	private List<QuestionnaireModel> getRandomElement(List<QuestionnaireModel> list, int totalItems) {
		if (list.size() <= totalItems)
			return list;
		Random rand = new Random();
		List<QuestionnaireModel> newList = new ArrayList<>();
		for (int i = 0; i < totalItems; i++) {
			int randomIndex = rand.nextInt(list.size());
			newList.add(list.get(randomIndex));
			list.remove(randomIndex);
		}
		return newList;
	}

	private class ParentClass {

		int totalQuestions;

		int numberQuestions;

		int multipleSelectQuestions;

		int singleSelectQuestions;

		public int getTotalQuestions() {
			return totalQuestions;
		}

		public void setTotalQuestions(int totalQuestions) {
			this.totalQuestions = totalQuestions;
		}

		public int getNumberQuestions() {
			return numberQuestions;
		}

		public void setNumberQuestions(int numberQuestions) {
			this.numberQuestions = numberQuestions;
		}

		public int getMultipleSelectQuestions() {
			return multipleSelectQuestions;
		}

		public void setMultipleSelectQuestions(int multipleSelectQuestions) {
			this.multipleSelectQuestions = multipleSelectQuestions;
		}

		public int getSingleSelectQuestions() {
			return singleSelectQuestions;
		}

		public void setSingleSelectQuestions(int singleSelectQuestions) {
			this.singleSelectQuestions = singleSelectQuestions;
		}
	}

	private class ChapterClass {

		int totalQuestions;

		int numberQuestions;

		int multipleSelectQuestions;

		int singleSelectQuestions;

		public int getTotalQuestions() {
			return totalQuestions;
		}

		public void setTotalQuestions(int totalQuestions) {
			this.totalQuestions = totalQuestions;
		}

		public int getNumberQuestions() {
			return numberQuestions;
		}

		public void setNumberQuestions(int numberQuestions) {
			this.numberQuestions = numberQuestions;
		}

		public int getMultipleSelectQuestions() {
			return multipleSelectQuestions;
		}

		public void setMultipleSelectQuestions(int multipleSelectQuestions) {
			this.multipleSelectQuestions = multipleSelectQuestions;
		}

		public int getSingleSelectQuestions() {
			return singleSelectQuestions;
		}

		public void setSingleSelectQuestions(int singleSelectQuestions) {
			this.singleSelectQuestions = singleSelectQuestions;
		}

	}
	
}
