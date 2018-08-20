package com.acoss.webae.web.rest;

import com.acoss.webae.WebaeApp;

import com.acoss.webae.domain.PreferenceNotif;
import com.acoss.webae.repository.PreferenceNotifRepository;
import com.acoss.webae.service.PreferenceNotifService;
import com.acoss.webae.service.dto.PreferenceNotifDTO;
import com.acoss.webae.service.mapper.PreferenceNotifMapper;
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

import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
/**
 * Test class for the PreferenceNotifResource REST controller.
 *
 * @see PreferenceNotifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebaeApp.class)
public class PreferenceNotifResourceIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final NotifChannel DEFAULT_CHANNEL = NotifChannel.SMS;
    private static final NotifChannel UPDATED_CHANNEL = NotifChannel.EMAIL;

    private static final NotifMoment DEFAULT_MOMENT = NotifMoment.OUV_DECL;
    private static final NotifMoment UPDATED_MOMENT = NotifMoment.FER_DECL;

    @Autowired
    private PreferenceNotifRepository preferenceNotifRepository;

    @Autowired
    private PreferenceNotifMapper preferenceNotifMapper;

    @Autowired
    private PreferenceNotifService preferenceNotifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreferenceNotifMockMvc;

    private PreferenceNotif preferenceNotif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreferenceNotifResource preferenceNotifResource = new PreferenceNotifResource(preferenceNotifService);
        this.restPreferenceNotifMockMvc = MockMvcBuilders.standaloneSetup(preferenceNotifResource)
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
    public static PreferenceNotif createEntity(EntityManager em) {
        PreferenceNotif preferenceNotif = new PreferenceNotif()
            .active(DEFAULT_ACTIVE)
            .channel(DEFAULT_CHANNEL)
            .moment(DEFAULT_MOMENT);
        return preferenceNotif;
    }

    @Before
    public void initTest() {
        preferenceNotif = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreferenceNotif() throws Exception {
        int databaseSizeBeforeCreate = preferenceNotifRepository.findAll().size();

        // Create the PreferenceNotif
        PreferenceNotifDTO preferenceNotifDTO = preferenceNotifMapper.toDto(preferenceNotif);
        restPreferenceNotifMockMvc.perform(post("/api/preference-notifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferenceNotifDTO)))
            .andExpect(status().isCreated());

        // Validate the PreferenceNotif in the database
        List<PreferenceNotif> preferenceNotifList = preferenceNotifRepository.findAll();
        assertThat(preferenceNotifList).hasSize(databaseSizeBeforeCreate + 1);
        PreferenceNotif testPreferenceNotif = preferenceNotifList.get(preferenceNotifList.size() - 1);
        assertThat(testPreferenceNotif.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPreferenceNotif.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testPreferenceNotif.getMoment()).isEqualTo(DEFAULT_MOMENT);
    }

    @Test
    @Transactional
    public void createPreferenceNotifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preferenceNotifRepository.findAll().size();

        // Create the PreferenceNotif with an existing ID
        preferenceNotif.setId(1L);
        PreferenceNotifDTO preferenceNotifDTO = preferenceNotifMapper.toDto(preferenceNotif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferenceNotifMockMvc.perform(post("/api/preference-notifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferenceNotifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreferenceNotif in the database
        List<PreferenceNotif> preferenceNotifList = preferenceNotifRepository.findAll();
        assertThat(preferenceNotifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreferenceNotifs() throws Exception {
        // Initialize the database
        preferenceNotifRepository.saveAndFlush(preferenceNotif);

        // Get all the preferenceNotifList
        restPreferenceNotifMockMvc.perform(get("/api/preference-notifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferenceNotif.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].moment").value(hasItem(DEFAULT_MOMENT.toString())));
    }

    @Test
    @Transactional
    public void getPreferenceNotif() throws Exception {
        // Initialize the database
        preferenceNotifRepository.saveAndFlush(preferenceNotif);

        // Get the preferenceNotif
        restPreferenceNotifMockMvc.perform(get("/api/preference-notifs/{id}", preferenceNotif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preferenceNotif.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.moment").value(DEFAULT_MOMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreferenceNotif() throws Exception {
        // Get the preferenceNotif
        restPreferenceNotifMockMvc.perform(get("/api/preference-notifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreferenceNotif() throws Exception {
        // Initialize the database
        preferenceNotifRepository.saveAndFlush(preferenceNotif);
        int databaseSizeBeforeUpdate = preferenceNotifRepository.findAll().size();

        // Update the preferenceNotif
        PreferenceNotif updatedPreferenceNotif = preferenceNotifRepository.findOne(preferenceNotif.getId());
        // Disconnect from session so that the updates on updatedPreferenceNotif are not directly saved in db
        em.detach(updatedPreferenceNotif);
        updatedPreferenceNotif
            .active(UPDATED_ACTIVE)
            .channel(UPDATED_CHANNEL)
            .moment(UPDATED_MOMENT);
        PreferenceNotifDTO preferenceNotifDTO = preferenceNotifMapper.toDto(updatedPreferenceNotif);

        restPreferenceNotifMockMvc.perform(put("/api/preference-notifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferenceNotifDTO)))
            .andExpect(status().isOk());

        // Validate the PreferenceNotif in the database
        List<PreferenceNotif> preferenceNotifList = preferenceNotifRepository.findAll();
        assertThat(preferenceNotifList).hasSize(databaseSizeBeforeUpdate);
        PreferenceNotif testPreferenceNotif = preferenceNotifList.get(preferenceNotifList.size() - 1);
        assertThat(testPreferenceNotif.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPreferenceNotif.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testPreferenceNotif.getMoment()).isEqualTo(UPDATED_MOMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingPreferenceNotif() throws Exception {
        int databaseSizeBeforeUpdate = preferenceNotifRepository.findAll().size();

        // Create the PreferenceNotif
        PreferenceNotifDTO preferenceNotifDTO = preferenceNotifMapper.toDto(preferenceNotif);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreferenceNotifMockMvc.perform(put("/api/preference-notifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preferenceNotifDTO)))
            .andExpect(status().isCreated());

        // Validate the PreferenceNotif in the database
        List<PreferenceNotif> preferenceNotifList = preferenceNotifRepository.findAll();
        assertThat(preferenceNotifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreferenceNotif() throws Exception {
        // Initialize the database
        preferenceNotifRepository.saveAndFlush(preferenceNotif);
        int databaseSizeBeforeDelete = preferenceNotifRepository.findAll().size();

        // Get the preferenceNotif
        restPreferenceNotifMockMvc.perform(delete("/api/preference-notifs/{id}", preferenceNotif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PreferenceNotif> preferenceNotifList = preferenceNotifRepository.findAll();
        assertThat(preferenceNotifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferenceNotif.class);
        PreferenceNotif preferenceNotif1 = new PreferenceNotif();
        preferenceNotif1.setId(1L);
        PreferenceNotif preferenceNotif2 = new PreferenceNotif();
        preferenceNotif2.setId(preferenceNotif1.getId());
        assertThat(preferenceNotif1).isEqualTo(preferenceNotif2);
        preferenceNotif2.setId(2L);
        assertThat(preferenceNotif1).isNotEqualTo(preferenceNotif2);
        preferenceNotif1.setId(null);
        assertThat(preferenceNotif1).isNotEqualTo(preferenceNotif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferenceNotifDTO.class);
        PreferenceNotifDTO preferenceNotifDTO1 = new PreferenceNotifDTO();
        preferenceNotifDTO1.setId(1L);
        PreferenceNotifDTO preferenceNotifDTO2 = new PreferenceNotifDTO();
        assertThat(preferenceNotifDTO1).isNotEqualTo(preferenceNotifDTO2);
        preferenceNotifDTO2.setId(preferenceNotifDTO1.getId());
        assertThat(preferenceNotifDTO1).isEqualTo(preferenceNotifDTO2);
        preferenceNotifDTO2.setId(2L);
        assertThat(preferenceNotifDTO1).isNotEqualTo(preferenceNotifDTO2);
        preferenceNotifDTO1.setId(null);
        assertThat(preferenceNotifDTO1).isNotEqualTo(preferenceNotifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(preferenceNotifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(preferenceNotifMapper.fromId(null)).isNull();
    }
}
