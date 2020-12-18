package com.epam.esm.api.rest;

import com.epam.esm.api.validation.GiftCertificatesCreateDtoValidator;
import com.epam.esm.api.validation.ValidationException;
import com.epam.esm.api.validation.ValidationResult;
import com.epam.esm.api.validation.Validator;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.GiftCertificatesService;
import com.epam.esm.service.data.GiftCertificateCreateDto;
import com.epam.esm.service.data.GiftCertificateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GiftCertificateRestController {

    private final GiftCertificatesService giftCertificatesService;
    private final Validator<GiftCertificateCreateDto> certificateCreationValidator;

    public GiftCertificateRestController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
        certificateCreationValidator = new GiftCertificatesCreateDtoValidator();
    }

    /**
     * path ...host/certificates?search_param=value
     * available search params:
     * can be used in lower case
     *
     * @BY_TAG search certificate by tag name,
     * @BY_NAME_PART search certificate by part of name,
     * @BY_DESCRIPTION_PART search certificate by part of description;
     * available sort params:
     * @NAME_ASC
     * @PRICE_ASC
     * @NAME_DESC
     * @NAME_DESC
     **/
    @GetMapping("/certificates")
    public List<GiftCertificateDto> all(@RequestParam(required = false) Map<String, String> params) {

        return params.isEmpty() ? giftCertificatesService.getAllCertificates()
                : giftCertificatesService.findCertificatesByCriteria(
                GiftCertificateQueryParametersMapper.mapSearchParams(params), GiftCertificateQueryParametersMapper.mapSortCriteria(params));

    }


    @GetMapping(value = "/certificates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDto> getById(@PathVariable Long id) throws DAOException {

        return ResponseEntity.status(HttpStatus.OK).body(giftCertificatesService.findById(id));

    }


    @PutMapping("/certificates/{id}")
    public ResponseEntity<String> updateCertificate(@RequestBody GiftCertificateCreateDto giftCertificateCreateDto, @PathVariable Long id) throws DAOException {
        giftCertificatesService.updateCertificate(giftCertificateCreateDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("updated successfully");
    }

    @PutMapping("/certificates")
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificateCreateDto giftCertificateCreateDto) throws DAOException {

        ValidationResult validationResult = certificateCreationValidator.validate(giftCertificateCreateDto);
        if (validationResult.isValid()) {
            giftCertificatesService.createCertificate(giftCertificateCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Certificate successfully created");
        } else {
            throw new ValidationException("cannot create certificate because of invalid input data", validationResult);
        }

    }

    @DeleteMapping(value = "/certificates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCertificate(@PathVariable Long id) {

        try {
            giftCertificatesService.removeCertificate(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("certificate was removed");
        } catch (DAOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("something went wrong");
        }


    }


}
