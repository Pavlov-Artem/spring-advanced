package com.epam.esm.impl;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Tag;
import com.epam.esm.service.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Primary
@Transactional
public class GiftCertificateJpaDAOImpl implements GifCertificateDAOAdv {

    @PersistenceContext
    private EntityManager entityManager;
    private CommonJpaOperations<GiftCertificate> commonJpaOperations = new CommonJpaOperations<>();

    @Override
    public List<GiftCertificate> findAll(Long pageSize, Long page) {
        return commonJpaOperations.findAllBasic(pageSize, page, GiftCertificate.class, entityManager);
    }

    @Override
    @Transactional
    public Long createEntity(GiftCertificate entity) throws DAOException {
        entityManager.persist(entity);
        Set<Tag> tags = entity.getCertificateTags();
        for (Tag tag : tags) {
            if (tag.getId() == null || findTagById(tag.getId()) == null) {
                entityManager.persist(tag);
            }
        }
        return entity.getId();
    }

    private Tag findTagById(Long tagId) {
        return entityManager.find(Tag.class, tagId);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws DAOException {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return giftCertificate == null ? Optional.empty() : Optional.of(giftCertificate);
    }

    @Override
    @Transactional
    public void updateEntity(GiftCertificate entity) throws DAOException {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public List<GiftCertificate> findAllWithCriteria(CertificateCriteriaParameters certificateCriteriaParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = cb.createQuery(GiftCertificate.class);
        if (!certificateCriteriaParameters.getExistedTags().isEmpty()) {
            query = findByTags(certificateCriteriaParameters.getExistedTags(), query, cb);
        } else {
            query = cb.createQuery(GiftCertificate.class);
        }
        List<Predicate> predicates = new ArrayList<>();
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        for (Map.Entry<CertificateSearchCriteria, String> entry : certificateCriteriaParameters.getSearchCriteriaStringMap().entrySet()) {
            switch (entry.getKey()) {
                case BY_NAME_PART:
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + entry.getValue() + "%"));
                    break;
                case BY_DESCRIPTION_PART:
                    predicates.add(cb.like(root.get("description").as(String.class), "%" + entry.getValue() + "%"));
                    break;
            }
        }
        if (!predicates.isEmpty()) {
            Predicate[] pr = new Predicate[predicates.size()];
            predicates.toArray(pr);
            query.where(pr);
        }
        CriteriaQuery<GiftCertificate> select = sortByParameter(certificateCriteriaParameters, query, cb, root);
        return getPaginatedResultList(certificateCriteriaParameters.getPage(), certificateCriteriaParameters.getPageSize(), select);
    }

    private CriteriaQuery<GiftCertificate> findByTags(List<Tag> tags, CriteriaQuery<GiftCertificate> query, CriteriaBuilder cb){
        Root<Tag> fromTag = query.from(Tag.class);
        Path<Object> path = fromTag.get("id");
        for (Tag tag : tags) {
            query.where(cb.equal(path, tag.getId()));
        }
        SetJoin<Tag, GiftCertificate> tagJoin = fromTag.joinSet("giftCertificates");
        CriteriaQuery<GiftCertificate> select = query.select(tagJoin);
        return select;
    }

    @Override
    public List<GiftCertificate> findCertificatesByTags(List<Tag> tags, Long page, Long pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<Tag> fromTag = cq.from(Tag.class);
        Path<Object> path = fromTag.get("id");
        for (Tag tag : tags) {
            cq.where(cb.equal(path, tag.getId()));
        }
        SetJoin<Tag, GiftCertificate> tagJoin = fromTag.joinSet("giftCertificates");
        CriteriaQuery<GiftCertificate> select = cq.select(tagJoin);
        return entityManager.createQuery(select).getResultList();
    }

    @Override
    public void deleteEntity(GiftCertificate entity) throws DAOException {
        try {
            GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, entity.getId());
            entityManager.remove(entity);
        } catch (Exception ex) {
            throw new DAOException(String.format("cannot remove order with id={0}", entity.getId()));
        }
    }

    private CriteriaQuery<GiftCertificate> sortByParameter(CertificateCriteriaParameters certificateCriteriaParameters, CriteriaQuery<GiftCertificate> select, CriteriaBuilder builder, Root<GiftCertificate> root) {
        if (certificateCriteriaParameters.getSortDirection().get() == SortDirection.DESC) {
            return select.orderBy(builder.desc(root.get(certificateCriteriaParameters.getCertificateSortCriteria().get().getName())));
        }
        return select.orderBy(builder.asc(root.get(certificateCriteriaParameters.getCertificateSortCriteria().get().getName())));
    }

    private List<GiftCertificate> getPaginatedResultList(Long page, Long pageSize, CriteriaQuery<GiftCertificate> select) {
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(select);
        int firstResult = (int) ((page - 1L) * pageSize);
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(Math.toIntExact(pageSize));
        return typedQuery.getResultList();
    }

}
