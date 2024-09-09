package com.aiforfly.test.quiz.Controller;


import com.aiforfly.test.quiz.Model.Response;
import com.aiforfly.test.quiz.Model.QuestionWrapper;
import com.aiforfly.test.quiz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz (@RequestParam String category,@RequestParam int numQ , @RequestParam String title){
        return quizService.createQuiz(category, numQ , title);

    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id , @RequestBody List<Response> responses){
        return quizService.calculateResult(id , responses);
    }
}
