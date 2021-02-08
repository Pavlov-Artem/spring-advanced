package com.epam.esm.api.rest;

import com.epam.esm.api.validation.GiftCertificatesCreateDtoValidator;
import com.epam.esm.api.validation.Validator;
import com.epam.esm.service.GiftCertificatesService;
import com.epam.esm.service.data.GiftCertificateCreateDto;
import com.epam.esm.service.data.GiftCertificateDto;
import com.epam.esm.service.exceptions.EntityNotFoundDaoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@ActiveProfiles(profiles = "dev")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration
@ExtendWith(MockitoExtension.class)
class GiftCertificateRestControllerTest {



    @InjectMocks
    public GiftCertificateRestController giftCertificateRestController;
    @Mock
    private GiftCertificatesService giftCertificatesService;
    @Mock
    private final Validator<GiftCertificateCreateDto> certificateCreationValidator = new GiftCertificatesCreateDtoValidator();

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(giftCertificateRestController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void all() throws Exception {

    }

    @Test
    void getById() throws Exception {
        // given
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(1L);
        giftCertificateDto.setName("Yoda");
        giftCertificateDto.setDescription("Description ");
        giftCertificateDto.setPrice(new BigDecimal(100));
        given(giftCertificatesService.findById(1L))
                .willReturn(giftCertificateDto);
        given(giftCertificatesService.findById(2L))
                .willThrow(new EntityNotFoundDaoException("entity not found", 2L));
        mockMvc.perform(get("/certificates/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(get("/certificates/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void updateCertificate() throws Exception {
        GiftCertificateCreateDto giftCertificateCreateDto = new GiftCertificateCreateDto("updated name", "updated description", new BigDecimal(-200L), 2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(giftCertificateCreateDto);
        mockMvc.perform(put("/certificates/1")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createCertificate() throws Exception {
        GiftCertificateCreateDto giftCertificateCreateDto = new GiftCertificateCreateDto("updated name", "updated description", new BigDecimal(200L), 2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(giftCertificateCreateDto);
        mockMvc.perform(post("/certificates")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void removeCertificate() {
    }


}