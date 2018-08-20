package com.acoss.webae.web.rest;

import com.acoss.webae.WebaeApp;

import com.acoss.webae.domain.UserDevice;
import com.acoss.webae.repository.UserDeviceRepository;
import com.acoss.webae.service.UserDeviceService;
import com.acoss.webae.service.dto.UserDeviceDTO;
import com.acoss.webae.service.mapper.UserDeviceMapper;
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

import com.acoss.webae.domain.enumeration.TypeDevice;
/**
 * Test class for the UserDeviceResource REST controller.
 *
 * @see UserDeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebaeApp.class)
public class UserDeviceResourceIntTest {

    private static final String DEFAULT_DEVICE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_TOKEN = "BBBBBBBBBB";

    private static final TypeDevice DEFAULT_TYPE_DEVICE = TypeDevice.IOS;
    private static final TypeDevice UPDATED_TYPE_DEVICE = TypeDevice.ANDROID;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private UserDeviceMapper userDeviceMapper;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDeviceMockMvc;

    private UserDevice userDevice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDeviceResource userDeviceResource = new UserDeviceResource(userDeviceService);
        this.restUserDeviceMockMvc = MockMvcBuilders.standaloneSetup(userDeviceResource)
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
    public static UserDevice createEntity(EntityManager em) {
        UserDevice userDevice = new UserDevice()
            .deviceToken(DEFAULT_DEVICE_TOKEN)
            .typeDevice(DEFAULT_TYPE_DEVICE);
        return userDevice;
    }

    @Before
    public void initTest() {
        userDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDevice() throws Exception {
        int databaseSizeBeforeCreate = userDeviceRepository.findAll().size();

        // Create the UserDevice
        UserDeviceDTO userDeviceDTO = userDeviceMapper.toDto(userDevice);
        restUserDeviceMockMvc.perform(post("/api/user-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(DEFAULT_DEVICE_TOKEN);
        assertThat(testUserDevice.getTypeDevice()).isEqualTo(DEFAULT_TYPE_DEVICE);
    }

    @Test
    @Transactional
    public void createUserDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDeviceRepository.findAll().size();

        // Create the UserDevice with an existing ID
        userDevice.setId(1L);
        UserDeviceDTO userDeviceDTO = userDeviceMapper.toDto(userDevice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDeviceMockMvc.perform(post("/api/user-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserDevices() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        // Get all the userDeviceList
        restUserDeviceMockMvc.perform(get("/api/user-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceToken").value(hasItem(DEFAULT_DEVICE_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].typeDevice").value(hasItem(DEFAULT_TYPE_DEVICE.toString())));
    }

    @Test
    @Transactional
    public void getUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);

        // Get the userDevice
        restUserDeviceMockMvc.perform(get("/api/user-devices/{id}", userDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceToken").value(DEFAULT_DEVICE_TOKEN.toString()))
            .andExpect(jsonPath("$.typeDevice").value(DEFAULT_TYPE_DEVICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDevice() throws Exception {
        // Get the userDevice
        restUserDeviceMockMvc.perform(get("/api/user-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();

        // Update the userDevice
        UserDevice updatedUserDevice = userDeviceRepository.findOne(userDevice.getId());
        // Disconnect from session so that the updates on updatedUserDevice are not directly saved in db
        em.detach(updatedUserDevice);
        updatedUserDevice
            .deviceToken(UPDATED_DEVICE_TOKEN)
            .typeDevice(UPDATED_TYPE_DEVICE);
        UserDeviceDTO userDeviceDTO = userDeviceMapper.toDto(updatedUserDevice);

        restUserDeviceMockMvc.perform(put("/api/user-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceDTO)))
            .andExpect(status().isOk());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate);
        UserDevice testUserDevice = userDeviceList.get(userDeviceList.size() - 1);
        assertThat(testUserDevice.getDeviceToken()).isEqualTo(UPDATED_DEVICE_TOKEN);
        assertThat(testUserDevice.getTypeDevice()).isEqualTo(UPDATED_TYPE_DEVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDevice() throws Exception {
        int databaseSizeBeforeUpdate = userDeviceRepository.findAll().size();

        // Create the UserDevice
        UserDeviceDTO userDeviceDTO = userDeviceMapper.toDto(userDevice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDeviceMockMvc.perform(put("/api/user-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDeviceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDevice in the database
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDevice() throws Exception {
        // Initialize the database
        userDeviceRepository.saveAndFlush(userDevice);
        int databaseSizeBeforeDelete = userDeviceRepository.findAll().size();

        // Get the userDevice
        restUserDeviceMockMvc.perform(delete("/api/user-devices/{id}", userDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDevice> userDeviceList = userDeviceRepository.findAll();
        assertThat(userDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDevice.class);
        UserDevice userDevice1 = new UserDevice();
        userDevice1.setId(1L);
        UserDevice userDevice2 = new UserDevice();
        userDevice2.setId(userDevice1.getId());
        assertThat(userDevice1).isEqualTo(userDevice2);
        userDevice2.setId(2L);
        assertThat(userDevice1).isNotEqualTo(userDevice2);
        userDevice1.setId(null);
        assertThat(userDevice1).isNotEqualTo(userDevice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDeviceDTO.class);
        UserDeviceDTO userDeviceDTO1 = new UserDeviceDTO();
        userDeviceDTO1.setId(1L);
        UserDeviceDTO userDeviceDTO2 = new UserDeviceDTO();
        assertThat(userDeviceDTO1).isNotEqualTo(userDeviceDTO2);
        userDeviceDTO2.setId(userDeviceDTO1.getId());
        assertThat(userDeviceDTO1).isEqualTo(userDeviceDTO2);
        userDeviceDTO2.setId(2L);
        assertThat(userDeviceDTO1).isNotEqualTo(userDeviceDTO2);
        userDeviceDTO1.setId(null);
        assertThat(userDeviceDTO1).isNotEqualTo(userDeviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userDeviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userDeviceMapper.fromId(null)).isNull();
    }
}
