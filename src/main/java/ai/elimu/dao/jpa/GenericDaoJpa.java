package ai.elimu.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ai.elimu.dao.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
@Transactional
public class GenericDaoJpa<T> implements GenericDao<T> {
    
    @PersistenceContext
    protected EntityManager em;

    protected final Logger logger = LogManager.getLogger();

    public void create(T t) throws DataAccessException {
        em.persist(t);
    }

    public T read(Long id) throws DataAccessException {
        Class<?> entityClass = getEntityClass();
        return (T) em.find(entityClass, id);
    }

    public List<T> readAll() throws DataAccessException {
        Class<?> entityClass = getEntityClass();
        return (List<T>) em.createQuery("SELECT c " +
                "FROM " + entityClass.getSimpleName() + " c",
                entityClass)
                .getResultList();
    }
    
    @Override
    public Long readCount() throws DataAccessException {
        Class<?> entityClass = getEntityClass();
        return (Long) em.createQuery("SELECT COUNT(c) " +
                "FROM " + entityClass.getSimpleName() + " c")
                .getSingleResult();
                
    }

    public void update(T t) throws DataAccessException {
        em.merge(t);
    }

    public void delete(T t) throws DataAccessException {
        em.remove(em.merge(t));
    }

    private Class<?> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            return null;
        }
    }
}
