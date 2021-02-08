package com.epam.esm.api.rest;

import com.epam.esm.api.filter.JwtRequestFilter;
import com.epam.esm.service.data.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticateControllerTest {

    private final Logger LOG = LogManager.getLogger(AuthenticateControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void requestShouldFailWith401() throws Exception {
        mvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void requestShouldWorkWithUnauthorizedPerson() throws Exception {
        mvc.perform(get("/certificates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void requestForToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test1@gmail.com", "123");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(authenticationRequest );
        ResultActions resultActions = mvc.perform(post("/api/token")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentResult = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentResult);
        String token = jsonObject.getString("token");
        LOG.info("token");
    }

    @Test
    public void requestWithNotValidToken() throws Exception {
        mvc.perform(post("/orders")
                .header("token","asdgbhk343njbasdasf")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void requestWithValidToken() throws Exception {
        //request for token
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test1@gmail.com", "123");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(authenticationRequest );
        ResultActions resultActions = mvc.perform(post("/api/token")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentResult = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentResult);
        String token = jsonObject.getString("token");
        // request with valid token
        mvc.perform(get("/orders")
                .header(HttpHeaders.AUTHORIZATION,token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        LOG.info("token");
    }
}