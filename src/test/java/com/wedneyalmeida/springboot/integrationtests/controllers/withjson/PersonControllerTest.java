package com.wedneyalmeida.springboot.integrationtests.controllers.withjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.wedneyalmeida.springboot.config.TestConfigs;
import com.wedneyalmeida.springboot.data.dto.v1.PersonDTO;
import com.wedneyalmeida.springboot.integrationtests.testscontainer.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private  static ObjectMapper objectMapper;

    private static PersonDTO person;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter("PersonFilter", SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData"));
        objectMapper.setFilterProvider(filters);

        person = new PersonDTO();
    }

    @Test
    void findByAll() {
    }

    @Test
    void findById() {
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(person))
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Ricar", createdPerson.getFirstName());
        assertEquals("Lutia", createdPerson.getLastName());
        assertEquals("falabala", createdPerson.getAddress());
        assertEquals("M", createdPerson.getGender());
    }

        private void mockPerson () {
            person.setFirstName("Ricar");
            person.setLastName("Lutia");
            person.setAddress("falabala");
            person.setGender("Male");
        }

    @Test
    void testCreate() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
