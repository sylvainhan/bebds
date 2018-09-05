package com.acoss.webae.web.rest;

import com.acoss.webae.WebaeApp;

import com.acoss.webae.domain.LastConnection;
import com.acoss.webae.repository.LastConnectionRepository;
import com.acoss.webae.service.LastConnectionService;
import com.acoss.webae.service.dto.LastConnectionDTO;
import com.acoss.webae.service.mapper.LastConnectionMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.acoss.webae.web.rest.TestUtil.sameInstant;
import static com.acoss.webae.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.acoss.webae.domain.enumeration.TypeDevice;
/**
 * Test class for the LastConnectionResource REST controller.
 *
 * @see LastConnectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebaeApp.class)
public class LastConnectionResourceIntTest {

    private static final ZonedDateTime DEFAULT_ACTUEL_CONNECTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUEL_CONNECTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_CONNECTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_CONNECTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final TypeDevice DEFAULT_TYPE_DEVEICE = TypeDevice.IOS;
    private static final TypeDevice UPDATED_TYPE_DEVEICE = TypeDevice.ANDROID;

    @Autowired
    private LastConnectionRepository lastConnectionRepository;

    @Autowired
    private LastConnectionMapper lastConnectionMapper;

    @Autowired
    private LastConnectionService lastConnectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLastConnectionMockMvc;

    private LastConnection lastConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LastConnectionResource lastConnectionResource = new LastConnectionResource(lastConnectionService);
        this.restLastConnectionMockMvc = MockMvcBuilders.standaloneSetup(lastConnectionResource)
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
    public static LastConnection createEntity(EntityManager em) {
        LastConnection lastConnection = new LastConnection()
            .actuelConnection(DEFAULT_ACTUEL_CONNECTION)
            .lastConnection(DEFAULT_LAST_CONNECTION)
            .typeDeveice(DEFAULT_TYPE_DEVEICE);
        return lastConnection;
    }

    @Before
    public void initTest() {
        lastConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createLastConnection() throws Exception {
        int databaseSizeBeforeCreate = lastConnectionRepository.findAll().size();

        // Create the LastConnection
        LastConnectionDTO lastConnectionDTO = lastConnectionMapper.toDto(lastConnection);
        restLastConnectionMockMvc.perform(post("/api/last-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the LastConnection in the database
        List<LastConnection> lastConnectionList = lastConnectionRepository.findAll();
        assertThat(lastConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        LastConnection testLastConnection = lastConnectionList.get(lastConnectionList.size() - 1);
        assertThat(testLastConnection.getActuelConnection()).isEqualTo(DEFAULT_ACTUEL_CONNECTION);
        assertThat(testLastConnection.getLastConnection()).isEqualTo(DEFAULT_LAST_CONNECTION);
        assertThat(testLastConnection.getTypeDevice()).isEqualTo(DEFAULT_TYPE_DEVEICE);
    }

    @Test
    @Transactional
    public void createLastConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lastConnectionRepository.findAll().size();

        // Create the LastConnection with an existing ID
        lastConnection.setId(1L);
        LastConnectionDTO lastConnectionDTO = lastConnectionMapper.toDto(lastConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLastConnectionMockMvc.perform(post("/api/last-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LastConnection in the database
        List<LastConnection> lastConnectionList = lastConnectionRepository.findAll();
        assertThat(lastConnectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLastConnections() throws Exception {
        // Initialize the database
        lastConnectionRepository.saveAndFlush(lastConnection);

        // Get all the lastConnectionList
        restLastConnectionMockMvc.perform(get("/api/last-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lastConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].actuelConnection").value(hasItem(sameInstant(DEFAULT_ACTUEL_CONNECTION))))
            .andExpect(jsonPath("$.[*].lastConnection").value(hasItem(sameInstant(DEFAULT_LAST_CONNECTION))))
            .andExpect(jsonPath("$.[*].typeDeveice").value(hasItem(DEFAULT_TYPE_DEVEICE.toString())));
    }

    @Test
    @Transactional
    public void getLastConnection() throws Exception {
        // Initialize the database
        lastConnectionRepository.saveAndFlush(lastConnection);

        // Get the lastConnection
        restLastConnectionMockMvc.perform(get("/api/last-connections/{id}", lastConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lastConnection.getId().intValue()))
            .andExpect(jsonPath("$.actuelConnection").value(sameInstant(DEFAULT_ACTUEL_CONNECTION)))
            .andExpect(jsonPath("$.lastConnection").value(sameInstant(DEFAULT_LAST_CONNECTION)))
            .andExpect(jsonPath("$.typeDeveice").value(DEFAULT_TYPE_DEVEICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLastConnection() throws Exception {
        // Get the lastConnection
        restLastConnectionMockMvc.perform(get("/api/last-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLastConnection() throws Exception {
        // Initialize the database
        lastConnectionRepository.saveAndFlush(lastConnection);
        int databaseSizeBeforeUpdate = lastConnectionRepository.findAll().size();

        // Update the lastConnection
        LastConnection updatedLastConnection = lastConnectionRepository.findOne(lastConnection.getId());
        // Disconnect from session so that the updates on updatedLastConnection are not directly saved in db
        em.detach(updatedLastConnection);
        updatedLastConnection
            .actuelConnection(UPDATED_ACTUEL_CONNECTION)
            .lastConnection(UPDATED_LAST_CONNECTION)
            .typeDeveice(UPDATED_TYPE_DEVEICE);
        LastConnectionDTO lastConnectionDTO = lastConnectionMapper.toDto(updatedLastConnection);

        restLastConnectionMockMvc.perform(put("/api/last-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the LastConnection in the database
        List<LastConnection> lastConnectionList = lastConnectionRepository.findAll();
        assertThat(lastConnectionList).hasSize(databaseSizeBeforeUpdate);
        LastConnection testLastConnection = lastConnectionList.get(lastConnectionList.size() - 1);
        assertThat(testLastConnection.getActuelConnection()).isEqualTo(UPDATED_ACTUEL_CONNECTION);
        assertThat(testLastConnection.getLastConnection()).isEqualTo(UPDATED_LAST_CONNECTION);
        assertThat(testLastConnection.getTypeDevice()).isEqualTo(UPDATED_TYPE_DEVEICE);
    }

    @Test
    @Transactional
    public void updateNonExistingLastConnection() throws Exception {
        int databaseSizeBeforeUpdate = lastConnectionRepository.findAll().size();

        // Create the LastConnection
        LastConnectionDTO lastConnectionDTO = lastConnectionMapper.toDto(lastConnection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLastConnectionMockMvc.perform(put("/api/last-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lastConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the LastConnection in the database
        List<LastConnection> lastConnectionList = lastConnectionRepository.findAll();
        assertThat(lastConnectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLastConnection() throws Exception {
        // Initialize the database
        lastConnectionRepository.saveAndFlush(lastConnection);
        int databaseSizeBeforeDelete = lastConnectionRepository.findAll().size();

        // Get the lastConnection
        restLastConnectionMockMvc.perform(delete("/api/last-connections/{id}", lastConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LastConnection> lastConnectionList = lastConnectionRepository.findAll();
        assertThat(lastConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LastConnection.class);
        LastConnection lastConnection1 = new LastConnection();
        lastConnection1.setId(1L);
        LastConnection lastConnection2 = new LastConnection();
        lastConnection2.setId(lastConnection1.getId());
        assertThat(lastConnection1).isEqualTo(lastConnection2);
        lastConnection2.setId(2L);
        assertThat(lastConnection1).isNotEqualTo(lastConnection2);
        lastConnection1.setId(null);
        assertThat(lastConnection1).isNotEqualTo(lastConnection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LastConnectionDTO.class);
        LastConnectionDTO lastConnectionDTO1 = new LastConnectionDTO();
        lastConnectionDTO1.setId(1L);
        LastConnectionDTO lastConnectionDTO2 = new LastConnectionDTO();
        assertThat(lastConnectionDTO1).isNotEqualTo(lastConnectionDTO2);
        lastConnectionDTO2.setId(lastConnectionDTO1.getId());
        assertThat(lastConnectionDTO1).isEqualTo(lastConnectionDTO2);
        lastConnectionDTO2.setId(2L);
        assertThat(lastConnectionDTO1).isNotEqualTo(lastConnectionDTO2);
        lastConnectionDTO1.setId(null);
        assertThat(lastConnectionDTO1).isNotEqualTo(lastConnectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lastConnectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lastConnectionMapper.fromId(null)).isNull();
    }
}
