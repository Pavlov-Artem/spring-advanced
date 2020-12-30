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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class GiftCertificateServiceImpl implements GiftCertificatesService {

    private GifCertificateDAOAdv giftCertificateDAO;
    private TagDAO tagDAO;

    public GiftCertificateServiceImpl(GifCertificateDAOAdv giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public List<GiftCertificateDto> getAllCertificates() {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public List<GiftCertificateDto> findCertificatesByCriteria(CertificateCriteriaParameters certificateCriteriaParameters) {
        for (String tagName : certificateCriteriaParameters.getTags()) {
            certificateCriteriaParameters.getExistedTags().add(tagDAO.findByName(tagName));
        }
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAllWithCriteria(certificateCriteriaParameters);
        return giftCertificates.stream()
                .map(CertificateBuilder::buildCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findByTags(List<String> tagNames, Long page, Long pageSize) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(tagDAO.findByName(tagName));
        }
        List<GiftCertificateDto> giftCertificateDtos = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificateDAO.findCertificatesByTags(tags, page, pageSize)) {
            giftCertificateDtos.add(CertificateBuilder.buildCertificateDto(giftCertificate));
        }
        return giftCertificateDtos;
    }

    @Override
    public GiftCertificateDto findById(Long id) throws DAOException {
        //Dao throws EntityNotFoundException
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id).get();
        return CertificateBuilder.buildCertificateDto(giftCertificate);
    }

    @Override
    @Transactional
    public void createCertificate(GiftCertificateCreateDto giftCertificateDto) throws DAOException {
        GiftCertificate giftCertificate = CertificateBuilder.buildGiftCertificateFromCreationDto(giftCertificateDto);
        giftCertificateDAO.createEntity(giftCertificate);
    }

    @Override
    @Transactional
    public void removeCertificate(Long id) throws DAOException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.findById(id);
        giftCertificateDAO.deleteEntity(optionalGiftCertificate.get());
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

        giftCertificateDAO.updateEntity(newCertificate);
    }


}
