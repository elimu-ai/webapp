package ai.elimu.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import org.apache.log4j.Logger;

import ai.elimu.dao.GenericDao;

@Repository
@Transactional
public class GenericDaoJpa<T> implements GenericDao<T> {
	
    @PersistenceContext
    protected EntityManager em;

    protected final Logger logger = Logger.getLogger(getClass());

    public void create(T t) throws DataAccessException {
        em.persist(t);
    }

    @SuppressWarnings("unchecked")
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
