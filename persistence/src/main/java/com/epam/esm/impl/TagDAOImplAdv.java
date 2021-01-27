package com.epam.esm.impl;

import com.epam.esm.data.Tag;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Transactional
public class TagDAOImplAdv implements TagDAO {

    private static final Logger LOGGER = LogManager.getLogger(TagDAOImplAdv.class);
    private final CommonJpaOperations<Tag> commonJpaOperations = new CommonJpaOperations<>();
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> root = cq.from(Tag.class);
        Predicate predicate = cb.equal(root.get("name"), name);
        try {
            Tag tag = entityManager.createQuery(cq.where(predicate)).getSingleResult();
            return tag;
        } catch (Exception ex) {
            throw new EntityNotFoundException(String.format("Tag with name = %s not found", name));
        }
    }

    @Override
    public List<Tag> findAllCertificateTags(Long certificateId) {
        return null;
    }

    @Override
    public List<Tag> findAll(Long pageSize, Long page) {
        return commonJpaOperations.findAllBasic(pageSize, page, Tag.class, entityManager);
    }

    @Override
    @Transactional
    public Long createEntity(Tag entity) throws DAOException {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            LOGGER.error("tag wasn't create", e);
            throw new DAOException("tag wasn't created");
        }
        return entity.getId();
    }

    @Override
    public Optional<Tag> findById(Long id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public Tag findMostWidelyUsedTag() {
        Tag tag = (Tag) entityManager.createNativeQuery("        SELECT tag.*, COUNT(tag.name) AS qty from orders\n" +
                "        inner join user u on u.id = orders.user_id\n" +
                "        inner join gift_certificate_has_tag on (orders.gift_certificate_id=gift_certificate_has_tag.gift_certificate_id)\n" +
                "        inner join tag on (tag.id=gift_certificate_has_tag.tag_id) where orders.user_id =\n" +
                "                (select orders.user_id from orders\n" +
                "        group by orders.user_id\n" +
                "        order by sum(orders.cost) desc\n" +
                "        limit 1)\n" +
                "        group by tag.name\n" +
                "        order by qty desc\n" +
                "        limit 1;\n", Tag.class).getSingleResult();
        return tag;
    }

    @Override
    public void updateEntity(Tag entity) throws DAOException {

    }

    @Override
    @Transactional
    public void deleteEntity(Tag entity) throws DAOException {
        try {
            Tag tag = entityManager.find(Tag.class, entity.getId());
            entityManager.remove(tag);
        } catch (Exception ex) {
            throw new DAOException("tag wasn't remove");
        }

    }
}
