package com.blockbrain.propmgmnt.web.rest;

import com.blockbrain.propmgmnt.PropMgmntApp;

import com.blockbrain.propmgmnt.domain.Apartment;
import com.blockbrain.propmgmnt.repository.ApartmentRepository;
import com.blockbrain.propmgmnt.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.blockbrain.propmgmnt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.blockbrain.propmgmnt.domain.enumeration.UnitType;
import com.blockbrain.propmgmnt.domain.enumeration.ApartmentStatus;
/**
 * Test class for the ApartmentResource REST controller.
 *
 * @see ApartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropMgmntApp.class)
public class ApartmentResourceIntTest {

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final UnitType DEFAULT_TYPE = UnitType.OnePlusOne;
    private static final UnitType UPDATED_TYPE = UnitType.TwoPlusTwo;

    private static final ApartmentStatus DEFAULT_UNIT_STATUS = ApartmentStatus.Available;
    private static final ApartmentStatus UPDATED_UNIT_STATUS = ApartmentStatus.Booked;

    @Autowired
    private ApartmentRepository apartmentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApartmentMockMvc;

    private Apartment apartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApartmentResource apartmentResource = new ApartmentResource(apartmentRepository);
        this.restApartmentMockMvc = MockMvcBuilders.standaloneSetup(apartmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apartment createEntity(EntityManager em) {
        Apartment apartment = new Apartment()
            .unit(DEFAULT_UNIT)
            .type(DEFAULT_TYPE)
            .unitStatus(DEFAULT_UNIT_STATUS);
        return apartment;
    }

    @Before
    public void initTest() {
        apartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createApartment() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // Create the Apartment
        restApartmentMockMvc.perform(post("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isCreated());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate + 1);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testApartment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApartment.getUnitStatus()).isEqualTo(DEFAULT_UNIT_STATUS);
    }

    @Test
    @Transactional
    public void createApartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // Create the Apartment with an existing ID
        apartment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApartmentMockMvc.perform(post("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = apartmentRepository.findAll().size();
        // set the field null
        apartment.setUnit(null);

        // Create the Apartment, which fails.

        restApartmentMockMvc.perform(post("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isBadRequest());

        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApartments() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList
        restApartmentMockMvc.perform(get("/api/apartments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].unitStatus").value(hasItem(DEFAULT_UNIT_STATUS.toString())));
    }
    

    @Test
    @Transactional
    public void getApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", apartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apartment.getId().intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.unitStatus").value(DEFAULT_UNIT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApartment() throws Exception {
        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment
        Apartment updatedApartment = apartmentRepository.findById(apartment.getId()).get();
        // Disconnect from session so that the updates on updatedApartment are not directly saved in db
        em.detach(updatedApartment);
        updatedApartment
            .unit(UPDATED_UNIT)
            .type(UPDATED_TYPE)
            .unitStatus(UPDATED_UNIT_STATUS);

        restApartmentMockMvc.perform(put("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApartment)))
            .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testApartment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApartment.getUnitStatus()).isEqualTo(UPDATED_UNIT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Create the Apartment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApartmentMockMvc.perform(put("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        int databaseSizeBeforeDelete = apartmentRepository.findAll().size();

        // Get the apartment
        restApartmentMockMvc.perform(delete("/api/apartments/{id}", apartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apartment.class);
        Apartment apartment1 = new Apartment();
        apartment1.setId(1L);
        Apartment apartment2 = new Apartment();
        apartment2.setId(apartment1.getId());
        assertThat(apartment1).isEqualTo(apartment2);
        apartment2.setId(2L);
        assertThat(apartment1).isNotEqualTo(apartment2);
        apartment1.setId(null);
        assertThat(apartment1).isNotEqualTo(apartment2);
    }
}
