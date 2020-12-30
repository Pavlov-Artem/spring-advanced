package com.epam.esm.service;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Tag;

import java.util.List;
import java.util.Map;

public interface GifCertificateDAOAdv extends CRUDOperations<GiftCertificate> {

    List<GiftCertificate> findAllWithCriteria(CertificateCriteriaParameters certificateCriteriaParameters);

    List<GiftCertificate> findCertificatesByTags(List<Tag> tags, Long page, Long pageSize);
}
