package com.epam.esm.service;

import com.epam.esm.data.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagDAO extends CRUDOperations<Tag> {

    Tag findByName(String name);

    List<Tag> findAllCertificateTags(Long certificateId);

    /**
     * the most widely used tag of a user with the highest cost of all orders.
     * @return
     */
    Tag findMostWidelyUsedTag();
}
