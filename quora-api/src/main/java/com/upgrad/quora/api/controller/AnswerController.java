package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.AuthenticationService;
import com.upgrad.quora.service.entity.Answer;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;
import java.util.UUID;
@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, path =  "/question/{questionId}/answer/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> answerCreate(final AnswerRequest answerRequest, @PathVariable("questionId") final Integer questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException
    {
            final Answer answer = new Answer();
            answer.setUuid(UUID.randomUUID().toString());
            answer.setAns(answerRequest.getAnswer());
            answer.setDate(ZonedDateTime.now());
            answer.setQuestion_id(questionId);

        final Answer answerEntity = answerService.createAnswer(answer,authorization);
        AnswerResponse userResponse = new AnswerResponse().id(answerEntity.getUuid()).status("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(userResponse, HttpStatus.CREATED);
    }
}
