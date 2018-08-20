package com.acoss.webae.web.rest;

import com.acoss.webae.WebaeApp;

import com.acoss.webae.domain.UserPreference;
import com.acoss.webae.repository.UserPreferenceRepository;
import com.acoss.webae.service.UserPreferenceService;
import com.acoss.webae.service.dto.UserPreferenceDTO;
import com.acoss.webae.service.mapper.UserPreferenceMapper;
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

import com.acoss.webae.domain.enumeration.PreferenceAvatar;
import com.acoss.webae.domain.enumeration.PreferenceAbonnement;
/**
 * Test class for the UserPreferenceResource REST controller.
 *
 * @see UserPreferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebaeApp.class)
public class UserPreferenceResourceIntTest {

    private static final String DEFAULT_NUM_COMPTE_EXTERNE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_COMPTE_EXTERNE = "BBBBBBBBBB";

    private static final PreferenceAvatar DEFAULT_AVATAR_PREF = PreferenceAvatar.PREFERENCEAVATAR;
    private static final PreferenceAvatar UPDATED_AVATAR_PREF = PreferenceAvatar.PREFERENCEAVATAR;

    private static final PreferenceAbonnement DEFAULT_ABONNE_PREF = PreferenceAbonnement.PREFERENCEABONNEMENT;
    private static final PreferenceAbonnement UPDATED_ABONNE_PREF = PreferenceAbonnement.PREFERENCEABONNEMENT;

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Autowired
    private UserPreferenceService userPreferenceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPreferenceMockMvc;

    private UserPreference userPreference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPreferenceResource userPreferenceResource = new UserPreferenceResource(userPreferenceService);
        this.restUserPreferenceMockMvc = MockMvcBuilders.standaloneSetup(userPreferenceResource)
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
    public static UserPreference createEntity(EntityManager em) {
        UserPreference userPreference = new UserPreference()
            .numCompteExterne(DEFAULT_NUM_COMPTE_EXTERNE)
            .avatarPref(DEFAULT_AVATAR_PREF)
            .abonnePref(DEFAULT_ABONNE_PREF);
        return userPreference;
    }

    @Before
    public void initTest() {
        userPreference = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPreference() throws Exception {
        int databaseSizeBeforeCreate = userPreferenceRepository.findAll().size();

        // Create the UserPreference
        UserPreferenceDTO userPreferenceDTO = userPreferenceMapper.toDto(userPreference);
        restUserPreferenceMockMvc.perform(post("/api/user-and-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPreferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPreference in the database
        List<UserPreference> userPreferenceList = userPreferenceRepository.findAll();
        assertThat(userPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        UserPreference testUserPreference = userPreferenceList.get(userPreferenceList.size() - 1);
        assertThat(testUserPreference.getNumCompteExterne()).isEqualTo(DEFAULT_NUM_COMPTE_EXTERNE);
        assertThat(testUserPreference.getAvatarPref()).isEqualTo(DEFAULT_AVATAR_PREF);
        assertThat(testUserPreference.getAbonnePref()).isEqualTo(DEFAULT_ABONNE_PREF);
    }

    @Test
    @Transactional
    public void createUserPreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPreferenceRepository.findAll().size();

        // Create the UserPreference with an existing ID
        userPreference.setId(1L);
        UserPreferenceDTO userPreferenceDTO = userPreferenceMapper.toDto(userPreference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPreferenceMockMvc.perform(post("/api/user-and-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPreferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPreference in the database
        List<UserPreference> userPreferenceList = userPreferenceRepository.findAll();
        assertThat(userPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserPreferences() throws Exception {
        // Initialize the database
        userPreferenceRepository.saveAndFlush(userPreference);

        // Get all the userPreferenceList
        restUserPreferenceMockMvc.perform(get("/api/user-and-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].numCompteExterne").value(hasItem(DEFAULT_NUM_COMPTE_EXTERNE.toString())))
            .andExpect(jsonPath("$.[*].avatarPref").value(hasItem(DEFAULT_AVATAR_PREF.toString())))
            .andExpect(jsonPath("$.[*].abonnePref").value(hasItem(DEFAULT_ABONNE_PREF.toString())));
    }

    @Test
    @Transactional
    public void getUserPreference() throws Exception {
        // Initialize the database
        userPreferenceRepository.saveAndFlush(userPreference);

        // Get the userPreference
        restUserPreferenceMockMvc.perform(get("/api/user-and-preferences/{id}", userPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPreference.getId().intValue()))
            .andExpect(jsonPath("$.numCompteExterne").value(DEFAULT_NUM_COMPTE_EXTERNE.toString()))
            .andExpect(jsonPath("$.avatarPref").value(DEFAULT_AVATAR_PREF.toString()))
            .andExpect(jsonPath("$.abonnePref").value(DEFAULT_ABONNE_PREF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPreference() throws Exception {
        // Get the userPreference
        restUserPreferenceMockMvc.perform(get("/api/user-and-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPreference() throws Exception {
        // Initialize the database
        userPreferenceRepository.saveAndFlush(userPreference);
        int databaseSizeBeforeUpdate = userPreferenceRepository.findAll().size();

        // Update the userPreference
        UserPreference updatedUserPreference = userPreferenceRepository.findOne(userPreference.getId());
        // Disconnect from session so that the updates on updatedUserPreference are not directly saved in db
        em.detach(updatedUserPreference);
        updatedUserPreference
            .numCompteExterne(UPDATED_NUM_COMPTE_EXTERNE)
            .avatarPref(UPDATED_AVATAR_PREF)
            .abonnePref(UPDATED_ABONNE_PREF);
        UserPreferenceDTO userPreferenceDTO = userPreferenceMapper.toDto(updatedUserPreference);

        restUserPreferenceMockMvc.perform(put("/api/user-and-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPreferenceDTO)))
            .andExpect(status().isOk());

        // Validate the UserPreference in the database
        List<UserPreference> userPreferenceList = userPreferenceRepository.findAll();
        assertThat(userPreferenceList).hasSize(databaseSizeBeforeUpdate);
        UserPreference testUserPreference = userPreferenceList.get(userPreferenceList.size() - 1);
        assertThat(testUserPreference.getNumCompteExterne()).isEqualTo(UPDATED_NUM_COMPTE_EXTERNE);
        assertThat(testUserPreference.getAvatarPref()).isEqualTo(UPDATED_AVATAR_PREF);
        assertThat(testUserPreference.getAbonnePref()).isEqualTo(UPDATED_ABONNE_PREF);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPreference() throws Exception {
        int databaseSizeBeforeUpdate = userPreferenceRepository.findAll().size();

        // Create the UserPreference
        UserPreferenceDTO userPreferenceDTO = userPreferenceMapper.toDto(userPreference);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPreferenceMockMvc.perform(put("/api/user-and-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPreferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPreference in the database
        List<UserPreference> userPreferenceList = userPreferenceRepository.findAll();
        assertThat(userPreferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPreference() throws Exception {
        // Initialize the database
        userPreferenceRepository.saveAndFlush(userPreference);
        int databaseSizeBeforeDelete = userPreferenceRepository.findAll().size();

        // Get the userPreference
        restUserPreferenceMockMvc.perform(delete("/api/user-and-preferences/{id}", userPreference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPreference> userPreferenceList = userPreferenceRepository.findAll();
        assertThat(userPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPreference.class);
        UserPreference userPreference1 = new UserPreference();
        userPreference1.setId(1L);
        UserPreference userPreference2 = new UserPreference();
        userPreference2.setId(userPreference1.getId());
        assertThat(userPreference1).isEqualTo(userPreference2);
        userPreference2.setId(2L);
        assertThat(userPreference1).isNotEqualTo(userPreference2);
        userPreference1.setId(null);
        assertThat(userPreference1).isNotEqualTo(userPreference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPreferenceDTO.class);
        UserPreferenceDTO userPreferenceDTO1 = new UserPreferenceDTO();
        userPreferenceDTO1.setId(1L);
        UserPreferenceDTO userPreferenceDTO2 = new UserPreferenceDTO();
        assertThat(userPreferenceDTO1).isNotEqualTo(userPreferenceDTO2);
        userPreferenceDTO2.setId(userPreferenceDTO1.getId());
        assertThat(userPreferenceDTO1).isEqualTo(userPreferenceDTO2);
        userPreferenceDTO2.setId(2L);
        assertThat(userPreferenceDTO1).isNotEqualTo(userPreferenceDTO2);
        userPreferenceDTO1.setId(null);
        assertThat(userPreferenceDTO1).isNotEqualTo(userPreferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userPreferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userPreferenceMapper.fromId(null)).isNull();
    }
}
