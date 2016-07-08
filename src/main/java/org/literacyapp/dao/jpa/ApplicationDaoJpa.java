package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.Application;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.enums.Locale;

public class ApplicationDaoJpa extends GenericDaoJpa<Application> implements ApplicationDao {

    @Override
    public List<Application> readAll(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.locale = :locale " +
            "ORDER BY a.packageName")
            .setParameter("locale", locale)
            .getResultList();
    }
}
