package com.epam.esm.service;

import com.epam.esm.service.data.GiftCertificateCreateDto;
import com.epam.esm.service.data.GiftCertificateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GiftCertificatesService {

    List<GiftCertificateDto> getAllCertificates();

    List<GiftCertificateDto> findCertificatesByCriteria(Map<CertificateSearchCriteria, String> criteriaMap, List<CertificateSortCriteria> sortCriteria);

    GiftCertificateDto findById(Long id) throws DAOException;

    void createCertificate(GiftCertificateCreateDto giftCertificateDto) throws DAOException;

    void removeCertificate(Long id) throws DAOException;

    void updateCertificate(GiftCertificateCreateDto giftCertificateCreateDto, Long id) throws DAOException;


}
