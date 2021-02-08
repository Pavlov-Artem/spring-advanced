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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
     * @NAME
     * @PRICE
     * @ASC
     * @DESC
     **/
    @GetMapping("/certificates")
    public List<GiftCertificateDto> all(@RequestParam(required = false) Map<String, String> params) throws DAOException {

        List<GiftCertificateDto> giftCertificateDtos = giftCertificatesService.findCertificatesByCriteria(GiftCertificateQueryParametersMapper.mapCriteriaParameters(params));
        for (GiftCertificateDto certificateDto: giftCertificateDtos) {
            addLinks(certificateDto);
        }
        return giftCertificateDtos;
    }

    @GetMapping(value = "/certificates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDto> getById(@PathVariable Long id) throws DAOException {
        GiftCertificateDto giftCertificateDto = giftCertificatesService.findById(id);
        addLinks(giftCertificateDto);
        return ResponseEntity.status(HttpStatus.OK).body(giftCertificateDto);
    }


    @PutMapping("/certificates/{id}")
    public ResponseEntity<String> updateCertificate(@RequestBody GiftCertificateCreateDto giftCertificateCreateDto, @PathVariable Long id) throws DAOException {

        giftCertificatesService.updateCertificate(giftCertificateCreateDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("updated successfully");
    }

    @PostMapping("/certificates")
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

    private void addLinks(GiftCertificateDto giftCertificateDto) throws DAOException {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).getById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).createCertificate(new GiftCertificateCreateDto())).withRel("create"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).updateCertificate(new GiftCertificateCreateDto(), 1L)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateRestController.class).removeCertificate(giftCertificateDto.getId())).withRel("remove"));
    }
}
