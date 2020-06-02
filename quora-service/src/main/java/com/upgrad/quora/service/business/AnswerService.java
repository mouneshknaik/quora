package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionsDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.Answer;
import com.upgrad.quora.service.entity.Questions;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionsDao questionsDao;

    @Transactional
    public Answer createAnswer(final Answer answer,final String questionId, final String authorization) throws AuthorizationFailedException, UserNotFoundException, InvalidQuestionException {
        UserAuth userAuth = userDao.getUserAuthByToken(authorization);
        Questions questions=  questionsDao.questionById(questionId);
        if(questions == null){
            throw new InvalidQuestionException("QUES-001","The question entered is invalid");
        }
        if(userAuth == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }
        if(userAuth.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out");
        }

        UserEntity userEntity =userDao.getUserByUserName(userAuth.getUser().getUserName());
        answer.setUser(userEntity);
        return answerDao.createAnswer(answer);
    }

}
