package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import org.literacyapp.dao.LetterDao;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.content.Letter;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Locale;

public class LetterDaoJpa extends GenericDaoJpa<Letter> implements LetterDao {

    @Override
    public Letter readByText(Locale locale, String text, Environment environment) throws DataAccessException {
        try {
            if (environment == Environment.DEV) {
                return (Letter) em.createQuery(
                    "SELECT l " +
                    "FROM Letter l " +
                    "WHERE l.locale = :locale " +
                    "AND l.text = :text")
                    .setParameter("locale", locale)
                    .setParameter("text", text)
                    .getSingleResult();
            } else {
                return (Letter) em.createQuery(
                    "SELECT l " +
                    "FROM Letter l " +
                    "WHERE l.locale = :locale " +
                    "AND l.text LIKE BINARY :text")
                    .setParameter("locale", locale)
                    .setParameter("text", text)
                    .getSingleResult();
            }
        } catch (NoResultException e) {
            logger.warn("Letter '" + text + "' was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Letter> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "WHERE l.locale = :locale " +
            "ORDER BY l.usageCount DESC, l.text")
            .setParameter("locale", locale)
            .getResultList();
    }
}
