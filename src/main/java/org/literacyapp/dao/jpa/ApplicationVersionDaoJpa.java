package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.dao.ApplicationVersionDao;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.admin.ApplicationVersion;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.enums.Locale;

public class ApplicationVersionDaoJpa extends GenericDaoJpa<ApplicationVersion> implements ApplicationVersionDao {
    
    @Override
    public List<ApplicationVersion> readAll(Application application) throws DataAccessException {
        return em.createQuery(
            "SELECT av " +
            "FROM ApplicationVersion av " +
            "WHERE av.application = :application " +
            "ORDER BY av.versionCode DESC")
            .setParameter("application", application)
            .getResultList();
    }
}
