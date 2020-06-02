package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.Questions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionsDao {
    @PersistenceContext
    private EntityManager entityManager;
    public Questions questionById(final String question_id) {
        try {
            return entityManager.find(Questions.class, Integer.parseInt(question_id));
        } catch (NoResultException nre) {
            return null;
        }
    }
}
