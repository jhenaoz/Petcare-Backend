package com.petcare.web.rest;

import com.petcare.Application;
import com.petcare.domain.Sponsor;
import com.petcare.repository.SponsorRepository;

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
 * Test class for the SponsorResource REST controller.
 *
 * @see SponsorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Ignore
public class SponsorResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIES = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES = "UPDATED_TEXT";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final String DEFAULT_GENDER = "SAMPLE_TEXT";
    private static final String UPDATED_GENDER = "UPDATED_TEXT";
    private static final String DEFAULT_SIZE = "SAMPLE_TEXT";
    private static final String UPDATED_SIZE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_IMAGE = "SAMPLE_TEXT";
    private static final String UPDATED_IMAGE = "UPDATED_TEXT";

    @Inject
    private SponsorRepository sponsorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restSponsorMockMvc;

    private Sponsor sponsor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SponsorResource sponsorResource = new SponsorResource();
        ReflectionTestUtils.setField(sponsorResource, "sponsorRepository", sponsorRepository);
        this.restSponsorMockMvc = MockMvcBuilders.standaloneSetup(sponsorResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sponsor = new Sponsor();
        sponsor.setName(DEFAULT_NAME);
        sponsor.setSpecies(DEFAULT_SPECIES);
        sponsor.setAge(DEFAULT_AGE);
        sponsor.setGender(DEFAULT_GENDER);
        sponsor.setSize(DEFAULT_SIZE);
        sponsor.setDescription(DEFAULT_DESCRIPTION);
//        sponsor.setImage(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createSponsor() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();

        // Create the Sponsor

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isCreated());

        // Validate the Sponsor in the database
        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeCreate + 1);
        Sponsor testSponsor = sponsors.get(sponsors.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSponsor.getSpecies()).isEqualTo(DEFAULT_SPECIES);
        assertThat(testSponsor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSponsor.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSponsor.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testSponsor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSponsor.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setName(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setSpecies(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setAge(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setGender(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setSize(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setImage(null);

        // Create the Sponsor, which fails.

        restSponsorMockMvc.perform(post("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isBadRequest());

        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSponsors() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get all the sponsors
        restSponsorMockMvc.perform(get("/api/sponsors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sponsor.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", sponsor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sponsor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSponsor() throws Exception {
        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

		int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor
        sponsor.setName(UPDATED_NAME);
        sponsor.setSpecies(UPDATED_SPECIES);
        sponsor.setAge(UPDATED_AGE);
        sponsor.setGender(UPDATED_GENDER);
        sponsor.setSize(UPDATED_SIZE);
        sponsor.setDescription(UPDATED_DESCRIPTION);
//        sponsor.setImage(UPDATED_IMAGE);
        

        restSponsorMockMvc.perform(put("/api/sponsors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sponsor)))
                .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsors.get(sponsors.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSponsor.getSpecies()).isEqualTo(UPDATED_SPECIES);
        assertThat(testSponsor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSponsor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSponsor.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testSponsor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSponsor.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

		int databaseSizeBeforeDelete = sponsorRepository.findAll().size();

        // Get the sponsor
        restSponsorMockMvc.perform(delete("/api/sponsors/{id}", sponsor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sponsor> sponsors = sponsorRepository.findAll();
        assertThat(sponsors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
