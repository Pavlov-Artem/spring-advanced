package com.epam.esm.service;

import com.epam.esm.data.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagDAO extends CRUDOperation<Tag> {

    Optional<Tag> findByName(String name);

    List<Tag> findAllCertificateTags(Long certificateId);


}
