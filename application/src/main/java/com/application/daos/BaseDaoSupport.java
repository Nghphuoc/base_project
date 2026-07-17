package com.application.daos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author PhuocNH.
 *
 * BaseDaoSupport abstract class.
 */

@Repository
public abstract class BaseDaoSupport {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * getResultListNative. <> get list data</>
     *
     * @param sql String
     * @param params Map<String, Object>
     * @param clazz Class<T>
     * @return T List<T>
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getResultListNative(String sql, Map<String, Object> params, Class<T> clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        setParameters(query, params);
        return query.getResultList();
    }

    /**
     * getSingleResultNative
     *
     * @param sql String
     * @param params Map<String, Object>
     * @param clazz Class<T>
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected <T> T getSingleResultNative(String sql, Map<String, Object> params, Class<T> clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        setParameters(query, params);

        List<T> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.getFirst();
        }
        return null;
    }

    /**
     * executeUpdateNative.
     *
     * @param sql String
     * @param params Map<String, Object>
     * @return int
     */
    protected int executeUpdateNative(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, params);
        return query.executeUpdate();
    }

    /**
     * getResultListObjects.
     *
     * @param sql String
     * @param params Map<String, Object>
     * @return List<Object[]>
     */
    @SuppressWarnings("unchecked")
    protected List<Object[]> getResultListObjects(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(query, params);
        return query.getResultList();
    }

    /**
     * setParameters.
     *
     * @param query Query
     * @param params Map<String, Object>
     */
    private void setParameters(Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
}
