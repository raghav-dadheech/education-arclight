package com.study.question.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.app.ApiResponse;
import com.study.question.dto.ExamDto;
import com.study.question.dto.LongListDto;
import com.study.question.dto.QuestionListFilterDto;
import com.study.question.dto.QuestionnaireDto;
import com.study.question.dto.ResponseDto;
import com.study.question.entity.facade.QuestionnaireFacade;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

	@Autowired
	private QuestionnaireFacade questionnaireFacade;

	@PostMapping(value = "/add-question")
	public ResponseEntity<ApiResponse> addQuestion(@RequestBody final QuestionnaireDto questionnaireDto)
			throws Exception {
		QuestionnaireDto dto = questionnaireFacade.addQuestionnaire(questionnaireDto);
		return ResponseEntity.ok(new ApiResponse("success", "question added successfully", dto));
	}

	@PostMapping(value = "/list-questions")
	public ResponseEntity<?> getQuestions(@RequestBody final QuestionListFilterDto filter) throws Exception {
		return ResponseEntity.ok(questionnaireFacade.listAll(filter));
	}

	@GetMapping(value = "/list-questions-trash")
	public ResponseEntity<?> getQuestionsTrash() throws Exception {
		return ResponseEntity.ok(questionnaireFacade.listAllTrash());
	}

	@GetMapping(value = "/question")
	public ResponseEntity<?> getQuestion(@RequestParam final Long questionId) throws Exception {
		return ResponseEntity.ok(questionnaireFacade.getQuestion(questionId));
	}
	
	@GetMapping(value = "/delete-solution")
	public ResponseEntity<?> deleteSolution(@RequestParam final Long questionId) throws Exception {
		return ResponseEntity.ok(questionnaireFacade.deleteSolution(questionId));
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<?> deleteQuestion(@RequestBody final LongListDto list) throws Exception {
		questionnaireFacade.deleteQuestion(list.getList());
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping(value = "/restore")
	public ResponseEntity<?> restoreQuestion(@RequestBody final LongListDto list) throws Exception {
		questionnaireFacade.restoreQuestion(list.getList());
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping(value = "/update-approval-status")
	public ResponseEntity<?> updateApprovalStatus(@RequestBody final LongListDto list) throws Exception {
		questionnaireFacade.updateApprovalStatus(list);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/upload-solution") // //new annotation since 4.3 public
	ResponseEntity<?> singleFileUpload(@RequestParam("questionSolution") MultipartFile file,
			String fileName, Long questionId,
			RedirectAttributes redirectAttributes) throws Exception {
		ResponseDto responseDto = new ResponseDto();
		if (file.isEmpty()) {
			responseDto.setStatus("error");
			return ResponseEntity.ok(responseDto);
		}
		QuestionnaireDto questionnaireDto = questionnaireFacade.uploadSolutions(file, fileName, questionId);
		responseDto.setStatus("success");
		return ResponseEntity.ok(questionnaireDto);
	}

}
