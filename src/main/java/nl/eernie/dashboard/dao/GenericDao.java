package nl.eernie.dashboard.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericDao(Class<T> type) {
        this.type = type;
    }


    public T create(final T t) {
        this.em.persist(t);
        //we want a database constraint check, therefor a flush.
        this.em.flush();
        return t;
    }

    public void delete(final Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    public T find(final Serializable id) {
        return this.em.find(type, id);
    }

    public T update(final T t) {
        return this.em.merge(t);
    }

    protected List<T> listQuery(String query, NamedParameter... namedParameters) {
        TypedQuery<T> listQuery = em.createQuery(query, type);
        List<NamedParameter> parameters = Arrays.asList(namedParameters);
        parameters.forEach(namedParameter ->
                listQuery.setParameter(namedParameter.getName(), namedParameter.getValue()));
        return listQuery.getResultList();
    }

    protected NamedParameter np(String name, Object parameter) {
        return new NamedParameter(name, parameter);
    }
}