package com.epam.esm.api.validation;

import com.epam.esm.service.data.GiftCertificateCreateDto;

import java.math.BigDecimal;

public class GiftCertificatesCreateDtoValidator implements Validator<GiftCertificateCreateDto> {


    @Override
    public ValidationResult validate(GiftCertificateCreateDto entity) {

        ValidationResult validationResult = new ValidationResult();

        validationResult.ifNotEmptyAddErrorMessage(checkName(entity.getName()));
        validationResult.ifNotEmptyAddErrorMessage(checkPrice(entity.getPrice()));
        validationResult.ifNotEmptyAddErrorMessage(checkDuration(entity.getDuration()));

        return validationResult;
    }


    private String checkName(String name) {

        return name.trim().isEmpty() ?  "Name can't be empty" : "";
    }

    private String checkPrice(BigDecimal price) {

        BigDecimal minPrice = new BigDecimal("0.1");

        return price.compareTo(minPrice) < 0 ? String.format("price can't be less than %s", minPrice.toString()) : "";

    }

    private String checkDuration(int duration) {

        return duration < 1 ? "duration can't be less than 1" : "";
    }

}
