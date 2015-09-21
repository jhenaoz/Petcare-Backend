package com.petcare.web.rest;

import com.petcare.Application;
import com.petcare.domain.Adoption;
import com.petcare.repository.AdoptionRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AdoptionResource REST controller.
 *
 * @see AdoptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Ignore
public class AdoptionResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIES = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_GENDER = "SAMPLE_TEXT";
    private static final String UPDATED_GENDER = "UPDATED_TEXT";
    private static final String DEFAULT_SIZE = "SAMPLE_TEXT";
    private static final String UPDATED_SIZE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private AdoptionRepository adoptionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restAdoptionMockMvc;

    private Adoption adoption;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdoptionResource adoptionResource = new AdoptionResource();
        ReflectionTestUtils.setField(adoptionResource, "adoptionRepository", adoptionRepository);
        this.restAdoptionMockMvc = MockMvcBuilders.standaloneSetup(adoptionResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        adoption = new Adoption();
        adoption.setName(DEFAULT_NAME);
        adoption.setSpecies(DEFAULT_SPECIES);
        adoption.setAge(DEFAULT_AGE);
        adoption.setGender(DEFAULT_GENDER);
        adoption.setSize(DEFAULT_SIZE);
        adoption.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAdoption() throws Exception {
        int databaseSizeBeforeCreate = adoptionRepository.findAll().size();

        // Create the Adoption

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isCreated());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeCreate + 1);
        Adoption testAdoption = adoptions.get(adoptions.size() - 1);
        assertThat(testAdoption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdoption.getSpecies()).isEqualTo(DEFAULT_SPECIES);
        assertThat(testAdoption.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testAdoption.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAdoption.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testAdoption.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adoptionRepository.findAll().size();
        // set the field null
        adoption.setSize(null);

        // Create the Adoption, which fails.

        restAdoptionMockMvc.perform(post("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isBadRequest());

        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdoptions() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

        // Get all the adoptions
        restAdoptionMockMvc.perform(get("/api/adoptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adoption.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", adoption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adoption.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdoption() throws Exception {
        // Get the adoption
        restAdoptionMockMvc.perform(get("/api/adoptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

		int databaseSizeBeforeUpdate = adoptionRepository.findAll().size();

        // Update the adoption
        adoption.setName(UPDATED_NAME);
        adoption.setSpecies(UPDATED_SPECIES);
        adoption.setAge(UPDATED_AGE);
        adoption.setGender(UPDATED_GENDER);
        adoption.setSize(UPDATED_SIZE);
        adoption.setDescription(UPDATED_DESCRIPTION);
        

        restAdoptionMockMvc.perform(put("/api/adoptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adoption)))
                .andExpect(status().isOk());

        // Validate the Adoption in the database
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeUpdate);
        Adoption testAdoption = adoptions.get(adoptions.size() - 1);
        assertThat(testAdoption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdoption.getSpecies()).isEqualTo(UPDATED_SPECIES);
        assertThat(testAdoption.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAdoption.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAdoption.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testAdoption.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAdoption() throws Exception {
        // Initialize the database
        adoptionRepository.saveAndFlush(adoption);

		int databaseSizeBeforeDelete = adoptionRepository.findAll().size();

        // Get the adoption
        restAdoptionMockMvc.perform(delete("/api/adoptions/{id}", adoption.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertThat(adoptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
