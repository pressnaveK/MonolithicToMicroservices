package com.aiforfly.test.quiz.Service;

import com.aiforfly.test.quiz.Dao.QuestionDao;
import com.aiforfly.test.quiz.Dao.QuizDao;
import com.aiforfly.test.quiz.Model.Question;
import com.aiforfly.test.quiz.Model.QuestionWrapper;
import com.aiforfly.test.quiz.Model.Quiz;
import com.aiforfly.test.quiz.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ , String  title) {
        List<Question> questions = questionDao.findRandomQuestionByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getCategory(),q.getDifficulties(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int i = 0;
        int NumCorrects = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getCorrectAnswer())) NumCorrects++;
            i++;
        }
        return new ResponseEntity<>(NumCorrects,HttpStatus.OK);
    }
}
