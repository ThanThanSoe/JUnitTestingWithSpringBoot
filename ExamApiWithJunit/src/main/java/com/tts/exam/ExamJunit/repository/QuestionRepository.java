package com.tts.exam.ExamJunit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tts.exam.ExamJunit.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
