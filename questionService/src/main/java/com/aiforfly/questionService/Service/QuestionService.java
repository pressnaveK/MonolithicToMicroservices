package com.aiforfly.questionService.Service;


import com.aiforfly.questionService.Dao.QuestionDao;
import com.aiforfly.questionService.Model.Question;
import com.aiforfly.questionService.Model.QuestionWrapper;
import com.aiforfly.questionService.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public ResponseEntity<String>addQuestion(Question question) {
         questionDao.save(question);
         return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionByCategory(category,numQ);
        return new ResponseEntity<>(questions, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> questionList = new ArrayList<>();
        for(Integer id : questionIds){
            Question q = questionDao.findById(id).get();
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getCategory(),q.getDifficulties(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3());
            questionList.add(qw);
        }
        return new ResponseEntity<>(questionList,HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer score = 0;
        for(Response response: responses){
            Question q = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(q.getCorrectAnswer()))
                score++;
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
