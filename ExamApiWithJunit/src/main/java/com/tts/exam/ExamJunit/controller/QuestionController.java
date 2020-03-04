package com.tts.exam.ExamJunit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tts.exam.ExamJunit.exception.ResourceNotFoundException;
import com.tts.exam.ExamJunit.model.Question;
import com.tts.exam.ExamJunit.repository.QuestionRepository;

@RestController
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;
	
	@GetMapping("/question")
	public List<Question> getAllQuestion(){
		return questionRepository.findAll();
	}
	
	@PostMapping("/question")
	public Question createQuestion(@Valid @RequestBody Question question) {
		return questionRepository.save(question);
	}
	
	@PostMapping("/question/{id}")
	public Question getQuestionById(@PathVariable(value="id") Integer questionId) {
		return questionRepository.findById(questionId).orElseThrow(()->new ResourceNotFoundException("Question", "id", questionId));
	}
	
	@PutMapping("/question/{id}")
	public Question updateQuestion(@PathVariable(value = "id") Integer questionId, @Valid @RequestBody Question questionDetail) {
		Question question = questionRepository.findById(questionId).orElseThrow(()->new ResourceNotFoundException("Question", "id", questionId));
		question.setQname(questionDetail.getQname());
		Question updateQuestion = questionRepository.save(question);
		return updateQuestion;
	}
	
	@DeleteMapping("/question/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable(value="id") Integer questionId){
		Question question = questionRepository.findById(questionId).orElseThrow(()->new ResourceNotFoundException("Question", "id", questionId));
		questionRepository.delete(question);
		return ResponseEntity.ok().build();
	}
}
