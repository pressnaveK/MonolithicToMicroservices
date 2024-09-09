package com.aiforfly.quizService.Dao;

import com.aiforfly.quizService.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
