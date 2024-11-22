package com.github.tarcalia.dmlabtestproject.repository;

import com.github.tarcalia.dmlabtestproject.entity.PersistedEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Generic repository for {@link PersistedEntity}.
 */
public abstract class GenericRepository<T extends PersistedEntity> {
    public static final String PARAM = "param";
    @PersistenceContext
    protected EntityManager em;

    /**
     * Save a {@link PersistedEntity} into the database.
     * @param entity the entity to be saved.
     * @return the saved entity.
     */
    @Transactional
    public T save(T entity) {
        return em.merge(entity);
    }

    /**
     * Find {@link PersistedEntity} by id.
     * @param id the ID of the {@link PersistedEntity}.
     * @return the entity if it exists.
     */
    @Transactional
    public Optional<T> findById(LocalDate id) {
        try {
            return Optional.of(em.createNamedQuery(T.FIND_BY_ID + className(), getEntityClass())
                    .setParameter(PARAM, id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Get the class of the entity.
     * @return the entity class.
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Return the name of the class as String.
     * @return the class name.
     */
    private String className() {
        return getEntityClass().getSimpleName();
    }
}
