package com.github.jntakpe.fmk.service.impl;


import com.github.jntakpe.fmk.domain.GenericDomain;
import com.github.jntakpe.fmk.service.GenericService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;


/**
 * Implémentation des services usuels
 *
 * @author jntakpe
 */
public abstract class GenericServiceImpl<T extends GenericDomain> implements GenericService<T> {

    /**
     * @return the repository to use.
     */
    public abstract CrudRepository<T, Long> getRepository();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return getRepository().count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return getRepository().exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        getRepository().delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(T t) {
        getRepository().delete(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T save(T t) {
        return getRepository().save(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isAvaillable(String fieldName, Long id, Object value) {
        Class<?> fieldClass = ReflectionUtils.findField(getDomainClass(), fieldName).getType();
        String upperName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = ReflectionUtils.findMethod(getRepository().getClass(), "findBy" + upperName, fieldClass);
        T entity = (T) ReflectionUtils.invokeMethod(method, getRepository(), value);
        return entity == null || entity.getId().equals(id);
    }

    /**
     * Méthode renvoyant l'entité de la couche domain/model
     *
     * @return ressource utilisée par le contrôlleur
     */
    private Class<T> getDomainClass() {
        ParameterizedType genericSuperclass = ((ParameterizedType) this.getClass().getGenericSuperclass());
        return (Class) genericSuperclass.getActualTypeArguments()[0];
    }

}
