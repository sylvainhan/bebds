package com.acoss.webae.web.rest;

import com.acoss.webae.WebaeApp;

import com.acoss.webae.domain.Taux;
import com.acoss.webae.repository.TauxRepository;
import com.acoss.webae.web.rest.errors.ExceptionTranslator;

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

import static com.acoss.webae.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TauxResource REST controller.
 *
 * @see TauxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebaeApp.class)
public class TauxResourceIntTest {

    @Autowired
    private TauxRepository tauxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTauxMockMvc;

    private Taux taux;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TauxResource tauxResource = new TauxResource(tauxRepository);
        this.restTauxMockMvc = MockMvcBuilders.standaloneSetup(tauxResource)
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
    public static Taux createEntity(EntityManager em) {
        Taux taux = new Taux();
        return taux;
    }

    @Before
    public void initTest() {
        taux = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaux() throws Exception {
        int databaseSizeBeforeCreate = tauxRepository.findAll().size();

        // Create the Taux
        restTauxMockMvc.perform(post("/api/tauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taux)))
            .andExpect(status().isCreated());

        // Validate the Taux in the database
        List<Taux> tauxList = tauxRepository.findAll();
        assertThat(tauxList).hasSize(databaseSizeBeforeCreate + 1);
        Taux testTaux = tauxList.get(tauxList.size() - 1);
    }

    @Test
    @Transactional
    public void createTauxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tauxRepository.findAll().size();

        // Create the Taux with an existing ID
        taux.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTauxMockMvc.perform(post("/api/tauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taux)))
            .andExpect(status().isBadRequest());

        // Validate the Taux in the database
        List<Taux> tauxList = tauxRepository.findAll();
        assertThat(tauxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTauxes() throws Exception {
        // Initialize the database
        tauxRepository.saveAndFlush(taux);

        // Get all the tauxList
        restTauxMockMvc.perform(get("/api/tauxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taux.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTaux() throws Exception {
        // Initialize the database
        tauxRepository.saveAndFlush(taux);

        // Get the taux
        restTauxMockMvc.perform(get("/api/tauxes/{id}", taux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taux.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaux() throws Exception {
        // Get the taux
        restTauxMockMvc.perform(get("/api/tauxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaux() throws Exception {
        // Initialize the database
        tauxRepository.saveAndFlush(taux);
        int databaseSizeBeforeUpdate = tauxRepository.findAll().size();

        // Update the taux
        Taux updatedTaux = tauxRepository.findOne(taux.getId());
        // Disconnect from session so that the updates on updatedTaux are not directly saved in db
        em.detach(updatedTaux);

        restTauxMockMvc.perform(put("/api/tauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaux)))
            .andExpect(status().isOk());

        // Validate the Taux in the database
        List<Taux> tauxList = tauxRepository.findAll();
        assertThat(tauxList).hasSize(databaseSizeBeforeUpdate);
        Taux testTaux = tauxList.get(tauxList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTaux() throws Exception {
        int databaseSizeBeforeUpdate = tauxRepository.findAll().size();

        // Create the Taux

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTauxMockMvc.perform(put("/api/tauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taux)))
            .andExpect(status().isCreated());

        // Validate the Taux in the database
        List<Taux> tauxList = tauxRepository.findAll();
        assertThat(tauxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaux() throws Exception {
        // Initialize the database
        tauxRepository.saveAndFlush(taux);
        int databaseSizeBeforeDelete = tauxRepository.findAll().size();

        // Get the taux
        restTauxMockMvc.perform(delete("/api/tauxes/{id}", taux.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Taux> tauxList = tauxRepository.findAll();
        assertThat(tauxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taux.class);
        Taux taux1 = new Taux();
        taux1.setId(1L);
        Taux taux2 = new Taux();
        taux2.setId(taux1.getId());
        assertThat(taux1).isEqualTo(taux2);
        taux2.setId(2L);
        assertThat(taux1).isNotEqualTo(taux2);
        taux1.setId(null);
        assertThat(taux1).isNotEqualTo(taux2);
    }
}
