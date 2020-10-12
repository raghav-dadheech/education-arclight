package com.study.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.question.dto.ExamDto;
import com.study.question.dto.ExamPaperQuestionsDto;
import com.study.question.dto.LongListDto;
import com.study.question.dto.ResponseDto;
import com.study.question.entity.facade.ExamPaperFacade;

@RestController
@RequestMapping("/exam-paper")
public class ExamPaperController {

	@Autowired
	private ExamPaperFacade examPaperFacade;
	
	/*
	 * @PostMapping(value = "/add-exam-paper") public ResponseEntity<?>
	 * addExam(@RequestBody final ExamPaperDto examPaperDto) throws Exception {
	 * examPaperFacade.addExamPaper(examPaperDto); ResponseDto responseDto = new
	 * ResponseDto(); responseDto.setStatus("success"); return
	 * ResponseEntity.ok(responseDto); }
	 */
	
	@PostMapping(value = "/add-exam-paper-questions")
	public ResponseEntity<?> addExamPaperQuestions(@RequestBody final ExamPaperQuestionsDto examPaperQuestions) throws Exception {
		examPaperFacade.addExamPaperQuestions(examPaperQuestions);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		return ResponseEntity.ok(responseDto);
	}
	
	@PostMapping(value = "/list-exam-practice-questions")
	public ResponseEntity<?> getExamPracticeQuestions(@RequestBody final ExamDto examDto) throws Exception {
		return ResponseEntity.ok(examPaperFacade.listExamPracticeQuestions(examDto));
	}
	
	@GetMapping(value = "/list-exam-papers")
	public ResponseEntity<?> getExamPapers() throws Exception {
		return ResponseEntity.ok(examPaperFacade.listExamPapers());
	}
	
	@PostMapping(value = "/list-exam-paper-questions")
	public ResponseEntity<?> getExamPaperQuestions(@RequestBody final ExamDto examDto) throws Exception {
		return ResponseEntity.ok(examPaperFacade.listExamPaperQuestions(examDto));
	}
	
	@GetMapping(value = "/delete-exam-paper")
	public ResponseEntity<?> deletePaper(@RequestParam final Long paperId) throws Exception {
		return ResponseEntity.ok(examPaperFacade.deleteExamPaper(paperId));
	}
	
	@PostMapping(value = "/get-exam-paper")
	public ResponseEntity<?> getExamPaper(@RequestBody final ExamDto examDto) throws Exception {
		return ResponseEntity.ok(examPaperFacade.getExamPaper(examDto));
	}
	
	@PostMapping(value = "/delete-exam-paper-questions")
	public ResponseEntity<?> deleteExamPaperQuestions(@RequestBody final ExamDto examDto) throws Exception {
		examPaperFacade.deleteExamPaperQuestions(examDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("success");
		return ResponseEntity.ok(responseDto);
	}
}
