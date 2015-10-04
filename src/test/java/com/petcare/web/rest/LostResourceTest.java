package com.petcare.web.rest;

import com.petcare.Application;
import com.petcare.domain.Lost;
import com.petcare.repository.LostRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LostResource REST controller.
 *
 * @see LostResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Ignore
public class LostResourceTest {

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

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final LocalDate DEFAULT_LOST_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_LOST_DATE = new LocalDate();

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");

    @Inject
    private LostRepository lostRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restLostMockMvc;

    private Lost lost;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LostResource lostResource = new LostResource();
        ReflectionTestUtils.setField(lostResource, "lostRepository", lostRepository);
        this.restLostMockMvc = MockMvcBuilders.standaloneSetup(lostResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lost = new Lost();
        lost.setName(DEFAULT_NAME);
        lost.setSpecies(DEFAULT_SPECIES);
        lost.setAge(DEFAULT_AGE);
        lost.setGender(DEFAULT_GENDER);
        lost.setSize(DEFAULT_SIZE);
        lost.setDescription(DEFAULT_DESCRIPTION);
        lost.setPhone(DEFAULT_PHONE);
        lost.setLostDate(DEFAULT_LOST_DATE);
        lost.setImage(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createLost() throws Exception {
        int databaseSizeBeforeCreate = lostRepository.findAll().size();

        // Create the Lost

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isCreated());

        // Validate the Lost in the database
        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeCreate + 1);
        Lost testLost = losts.get(losts.size() - 1);
        assertThat(testLost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLost.getSpecies()).isEqualTo(DEFAULT_SPECIES);
        assertThat(testLost.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testLost.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testLost.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testLost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLost.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testLost.getLostDate()).isEqualTo(DEFAULT_LOST_DATE);
        assertThat(testLost.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setName(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setSpecies(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setAge(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setGender(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setSize(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setPhone(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLostDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setLostDate(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = lostRepository.findAll().size();
        // set the field null
        lost.setImage(null);

        // Create the Lost, which fails.

        restLostMockMvc.perform(post("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isBadRequest());

        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLosts() throws Exception {
        // Initialize the database
        lostRepository.saveAndFlush(lost);

        // Get all the losts
        restLostMockMvc.perform(get("/api/losts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lost.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
                .andExpect(jsonPath("$.[*].lostDate").value(hasItem(DEFAULT_LOST_DATE.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getLost() throws Exception {
        // Initialize the database
        lostRepository.saveAndFlush(lost);

        // Get the lost
        restLostMockMvc.perform(get("/api/losts/{id}", lost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lost.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.lostDate").value(DEFAULT_LOST_DATE.toString()))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingLost() throws Exception {
        // Get the lost
        restLostMockMvc.perform(get("/api/losts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLost() throws Exception {
        // Initialize the database
        lostRepository.saveAndFlush(lost);

		int databaseSizeBeforeUpdate = lostRepository.findAll().size();

        // Update the lost
        lost.setName(UPDATED_NAME);
        lost.setSpecies(UPDATED_SPECIES);
        lost.setAge(UPDATED_AGE);
        lost.setGender(UPDATED_GENDER);
        lost.setSize(UPDATED_SIZE);
        lost.setDescription(UPDATED_DESCRIPTION);
        lost.setPhone(UPDATED_PHONE);
        lost.setLostDate(UPDATED_LOST_DATE);
        lost.setImage(UPDATED_IMAGE);
        

        restLostMockMvc.perform(put("/api/losts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lost)))
                .andExpect(status().isOk());

        // Validate the Lost in the database
        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeUpdate);
        Lost testLost = losts.get(losts.size() - 1);
        assertThat(testLost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLost.getSpecies()).isEqualTo(UPDATED_SPECIES);
        assertThat(testLost.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testLost.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testLost.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testLost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLost.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testLost.getLostDate()).isEqualTo(UPDATED_LOST_DATE);
        assertThat(testLost.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteLost() throws Exception {
        // Initialize the database
        lostRepository.saveAndFlush(lost);

		int databaseSizeBeforeDelete = lostRepository.findAll().size();

        // Get the lost
        restLostMockMvc.perform(delete("/api/losts/{id}", lost.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lost> losts = lostRepository.findAll();
        assertThat(losts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
