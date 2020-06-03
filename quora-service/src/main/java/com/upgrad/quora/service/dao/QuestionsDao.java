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
    public Questions questionById(final String questionUuid) {
        try {
            return entityManager.createNamedQuery("questionById", Questions.class)
                    .setParameter("uuid", questionUuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
