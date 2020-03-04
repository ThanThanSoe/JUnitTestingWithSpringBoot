package com.tts.exam.ExamJunit.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tts.exam.ExamJunit.exception.ResourceNotFoundException;
import com.tts.exam.ExamJunit.model.Answer;
import com.tts.exam.ExamJunit.repository.AnswerRepository;
import com.tts.exam.ExamJunit.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
public class AnswerController {

	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/answer")
	public List<Answer> getAllAnswer(){
		return answerRepository.findAll();
	}
	
	@PostMapping("/answer")
	public Answer createNote(@Valid @RequestBody Answer answer) {
		return answerRepository.save(answer);
	}
	
	@GetMapping("/answer/{id}")
	public Answer getAnswerById(@PathVariable(value = "id") Integer answerId) {
		return answerRepository.findById(answerId).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", answerId));
	}
	
	@PutMapping("/answer/{id}")
	public Answer updateNote(@PathVariable(value = "id") Integer answerId, @Valid @RequestBody Answer answerDetails) {
		Answer answer = answerRepository.findById(answerId).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", answerId));
		answer.setAnswername(answerDetails.getAnswername());
		answer.setPostedBy(answerDetails.getPostedBy());
		
		Answer updateAnswer = answerRepository.save(answer);
		return updateAnswer;
	}
	
	@DeleteMapping("/answer/{id}")
	public ResponseEntity<?> deleteAnswer(@PathVariable(value = "id") Integer answerId){
		Answer answer = answerRepository.findById(answerId).orElseThrow(()-> new ResourceNotFoundException("Answer", "id", answerId));
		answerRepository.delete(answer);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		return reportService.exportReport(format);
	}
}
