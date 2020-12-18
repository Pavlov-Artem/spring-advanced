package com.epam.esm.service.impl;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Tag;
import com.epam.esm.service.*;
import com.epam.esm.service.data.GiftCertificateCreateDto;
import com.epam.esm.service.data.GiftCertificateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class GiftCertificateServiceImpl implements GiftCertificatesService {


    private GiftCertificateDAO giftCertificateDAO;
    private TagDAO tagDAO;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public List<GiftCertificateDto> getAllCertificates() {


        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAll();
        return giftCertificates.stream()
                .map(gc -> CertificateBuilder.buildCertificateDto(gc, tagDAO.findAllCertificateTags(gc.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findCertificatesByCriteria(Map<CertificateSearchCriteria, String> criteriaMap, List<CertificateSortCriteria> sortCriteria) {

        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAllWithCriteria(criteriaMap, sortCriteria);

        return giftCertificates.stream()
                .map(gc -> CertificateBuilder.buildCertificateDto(gc, tagDAO.findAllCertificateTags(gc.getId())))
                .collect(Collectors.toList());

    }

    @Override
    public GiftCertificateDto findById(Long id) throws DAOException {
        //Dao throws EntityNotFoundException
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id).get();
        return CertificateBuilder.buildCertificateDto(giftCertificate, tagDAO.findAllCertificateTags(giftCertificate.getId()));
    }


    @Override
    @Transactional
    public void createCertificate(GiftCertificateCreateDto giftCertificateCreateDto) throws DAOException {

        GiftCertificate gc = CertificateBuilder.buildGiftCertificateFromCreationDto(giftCertificateCreateDto);
        gc.setId(giftCertificateDAO.createEntity(gc));
        createTagsIfNotExist(giftCertificateCreateDto.getTags());
        giftCertificateDAO.addCertificateTags(giftCertificateCreateDto.getTags(), gc.getId());
    }

    @Override
    @Transactional
    public void removeCertificate(Long id) throws DAOException {

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.findById(id);
        if (optionalGiftCertificate.isPresent()) {
            giftCertificateDAO.deleteCertificate(optionalGiftCertificate.get());
        } else {
            throw new DAOException(String.format("certificate with id=%s not found", id));
        }

    }

    @Override
    @Transactional
    public void updateCertificate(GiftCertificateCreateDto giftCertificateCreateDto, Long id) throws DAOException {

        GiftCertificate newCertificate = giftCertificateDAO.findById(id).get();
        newCertificate.setName(giftCertificateCreateDto.getName());
        newCertificate.setDescription(giftCertificateCreateDto.getDescription());
        newCertificate.setPrice(giftCertificateCreateDto.getPrice());
        newCertificate.setDuration(giftCertificateCreateDto.getDuration());
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());
        newCertificate.setLastUpdateTime(currentDateTimestamp);

        giftCertificateDAO.updateCertificate(newCertificate);
        createTagsIfNotExist(giftCertificateCreateDto.getTags());
        giftCertificateDAO.updateCertificateTags(id, giftCertificateCreateDto.getTags());

    }

    private void createTagsIfNotExist(List<Tag> tags) throws DAOException {

        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagDAO.findByName(tag.getName());
            if (optionalTag.isPresent()) {
                tag.setId(optionalTag.get().getId());
            } else {
                tag.setId(tagDAO.createEntity(tag));
            }
        }
    }

}
