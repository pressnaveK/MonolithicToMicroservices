package com.aiforfly.test.quiz.Dao;

import com.aiforfly.test.quiz.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
