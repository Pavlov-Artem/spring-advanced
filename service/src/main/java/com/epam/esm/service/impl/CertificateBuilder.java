package com.epam.esm.service.impl;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Tag;
import com.epam.esm.service.data.GiftCertificateCreateDto;
import com.epam.esm.service.data.GiftCertificateDto;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class CertificateBuilder {

    static GiftCertificateDto buildCertificateDto(GiftCertificate gc) {
        if (gc == null) {
            return null;
        }
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String createDateFormatted = df.format(gc.getCreateDate());
        String lastUpdateDateFormatted = df.format(gc.getLastUpdateTime());
        List<Tag> tagList = new ArrayList<>(gc.getCertificateTags());
        return new GiftCertificateDto(
                gc.getId(),
                gc.getName(),
                gc.getDescription(),
                gc.getPrice(),
                createDateFormatted,
                lastUpdateDateFormatted,
                gc.getDuration(),
                tagList);
    }

    static GiftCertificate buildGiftCertificateFromCreationDto(GiftCertificateCreateDto giftCertificateCreateDto) {
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());

        GiftCertificate gc = new GiftCertificate();
        gc.setName(giftCertificateCreateDto.getName());
        gc.setDescription(giftCertificateCreateDto.getDescription());
        gc.setPrice(giftCertificateCreateDto.getPrice());
        gc.setCreateDate(currentDateTimestamp);
        gc.setLastUpdateTime(currentDateTimestamp);
        gc.setDuration(giftCertificateCreateDto.getDuration());
        gc.setCertificateTags(new HashSet<>(giftCertificateCreateDto.getTags()));
        return gc;
    }
}
