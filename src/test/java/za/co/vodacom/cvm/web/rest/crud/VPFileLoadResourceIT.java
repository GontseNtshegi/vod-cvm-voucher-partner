package za.co.vodacom.cvm.web.rest.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.vodacom.cvm.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;
import za.co.vodacom.cvm.web.rest.TestUtil;
import za.co.vodacom.cvm.web.rest.crud.VPFileLoadResource;

/**
 * Integration tests for the {@link VPFileLoadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPFileLoadResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BATCH_ID = 1;
    private static final Integer UPDATED_BATCH_ID = 2;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_COMPLETED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NUM_LOADED = 1;
    private static final Integer UPDATED_NUM_LOADED = 2;

    private static final Integer DEFAULT_NUM_FAILED = 1;
    private static final Integer UPDATED_NUM_FAILED = 2;

    private static final String ENTITY_API_URL = "/v2/api/vp-file-loads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPFileLoadRepository vPFileLoadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPFileLoadMockMvc;

    private VPFileLoad vPFileLoad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPFileLoad createEntity(EntityManager em) {
        VPFileLoad vPFileLoad = new VPFileLoad()
            .fileName(DEFAULT_FILE_NAME)
            .batchId(DEFAULT_BATCH_ID)
            .createDate(DEFAULT_CREATE_DATE)
            .completedDate(DEFAULT_COMPLETED_DATE)
            .numLoaded(DEFAULT_NUM_LOADED)
            .numFailed(DEFAULT_NUM_FAILED);
        return vPFileLoad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPFileLoad createUpdatedEntity(EntityManager em) {
        VPFileLoad vPFileLoad = new VPFileLoad()
            .fileName(UPDATED_FILE_NAME)
            .batchId(UPDATED_BATCH_ID)
            .createDate(UPDATED_CREATE_DATE)
            .completedDate(UPDATED_COMPLETED_DATE)
            .numLoaded(UPDATED_NUM_LOADED)
            .numFailed(UPDATED_NUM_FAILED);
        return vPFileLoad;
    }

    @BeforeEach
    public void initTest() {
        vPFileLoad = createEntity(em);
    }

    //@Test
    @Transactional
    void createVPFileLoad() throws Exception {
        int databaseSizeBeforeCreate = vPFileLoadRepository.findAll().size();
        // Create the VPFileLoad
        restVPFileLoadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isCreated());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeCreate + 1);
        VPFileLoad testVPFileLoad = vPFileLoadList.get(vPFileLoadList.size() - 1);
        assertThat(testVPFileLoad.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testVPFileLoad.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testVPFileLoad.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPFileLoad.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
        assertThat(testVPFileLoad.getNumLoaded()).isEqualTo(DEFAULT_NUM_LOADED);
        assertThat(testVPFileLoad.getNumFailed()).isEqualTo(DEFAULT_NUM_FAILED);
    }

    //@Test
    @Transactional
    void createVPFileLoadWithExistingId() throws Exception {
        // Create the VPFileLoad with an existing ID
        vPFileLoad.setId(1L);

        int databaseSizeBeforeCreate = vPFileLoadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPFileLoadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isBadRequest());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPFileLoadRepository.findAll().size();
        // set the field null
        vPFileLoad.setFileName(null);

        // Create the VPFileLoad, which fails.

        restVPFileLoadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isBadRequest());

        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkBatchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPFileLoadRepository.findAll().size();
        // set the field null
        vPFileLoad.setBatchId(null);

        // Create the VPFileLoad, which fails.

        restVPFileLoadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isBadRequest());

        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPFileLoadRepository.findAll().size();
        // set the field null
        vPFileLoad.setCreateDate(null);

        // Create the VPFileLoad, which fails.

        restVPFileLoadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isBadRequest());

        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPFileLoads() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        // Get all the vPFileLoadList
        restVPFileLoadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPFileLoad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(sameInstant(DEFAULT_COMPLETED_DATE))))
            .andExpect(jsonPath("$.[*].numLoaded").value(hasItem(DEFAULT_NUM_LOADED)))
            .andExpect(jsonPath("$.[*].numFailed").value(hasItem(DEFAULT_NUM_FAILED)));
    }

    //@Test
    @Transactional
    void getVPFileLoad() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        // Get the vPFileLoad
        restVPFileLoadMockMvc
            .perform(get(ENTITY_API_URL_ID, vPFileLoad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPFileLoad.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.completedDate").value(sameInstant(DEFAULT_COMPLETED_DATE)))
            .andExpect(jsonPath("$.numLoaded").value(DEFAULT_NUM_LOADED))
            .andExpect(jsonPath("$.numFailed").value(DEFAULT_NUM_FAILED));
    }

    //@Test
    @Transactional
    void getNonExistingVPFileLoad() throws Exception {
        // Get the vPFileLoad
        restVPFileLoadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPFileLoad() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();

        // Update the vPFileLoad
        VPFileLoad updatedVPFileLoad = vPFileLoadRepository.findById(vPFileLoad.getId()).get();
        // Disconnect from session so that the updates on updatedVPFileLoad are not directly saved in db
        em.detach(updatedVPFileLoad);
        updatedVPFileLoad
            .fileName(UPDATED_FILE_NAME)
            .batchId(UPDATED_BATCH_ID)
            .createDate(UPDATED_CREATE_DATE)
            .completedDate(UPDATED_COMPLETED_DATE)
            .numLoaded(UPDATED_NUM_LOADED)
            .numFailed(UPDATED_NUM_FAILED);

        restVPFileLoadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPFileLoad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPFileLoad))
            )
            .andExpect(status().isOk());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
        VPFileLoad testVPFileLoad = vPFileLoadList.get(vPFileLoadList.size() - 1);
        assertThat(testVPFileLoad.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testVPFileLoad.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPFileLoad.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPFileLoad.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
        assertThat(testVPFileLoad.getNumLoaded()).isEqualTo(UPDATED_NUM_LOADED);
        assertThat(testVPFileLoad.getNumFailed()).isEqualTo(UPDATED_NUM_FAILED);
    }

    //@Test
    @Transactional
    void putNonExistingVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPFileLoad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPFileLoad))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPFileLoad))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPFileLoad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPFileLoadWithPatch() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();

        // Update the vPFileLoad using partial update
        VPFileLoad partialUpdatedVPFileLoad = new VPFileLoad();
        partialUpdatedVPFileLoad.setId(vPFileLoad.getId());

        partialUpdatedVPFileLoad.batchId(UPDATED_BATCH_ID).createDate(UPDATED_CREATE_DATE).numLoaded(UPDATED_NUM_LOADED);

        restVPFileLoadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPFileLoad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPFileLoad))
            )
            .andExpect(status().isOk());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
        VPFileLoad testVPFileLoad = vPFileLoadList.get(vPFileLoadList.size() - 1);
        assertThat(testVPFileLoad.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testVPFileLoad.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPFileLoad.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPFileLoad.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
        assertThat(testVPFileLoad.getNumLoaded()).isEqualTo(UPDATED_NUM_LOADED);
        assertThat(testVPFileLoad.getNumFailed()).isEqualTo(DEFAULT_NUM_FAILED);
    }

    //@Test
    @Transactional
    void fullUpdateVPFileLoadWithPatch() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();

        // Update the vPFileLoad using partial update
        VPFileLoad partialUpdatedVPFileLoad = new VPFileLoad();
        partialUpdatedVPFileLoad.setId(vPFileLoad.getId());

        partialUpdatedVPFileLoad
            .fileName(UPDATED_FILE_NAME)
            .batchId(UPDATED_BATCH_ID)
            .createDate(UPDATED_CREATE_DATE)
            .completedDate(UPDATED_COMPLETED_DATE)
            .numLoaded(UPDATED_NUM_LOADED)
            .numFailed(UPDATED_NUM_FAILED);

        restVPFileLoadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPFileLoad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPFileLoad))
            )
            .andExpect(status().isOk());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
        VPFileLoad testVPFileLoad = vPFileLoadList.get(vPFileLoadList.size() - 1);
        assertThat(testVPFileLoad.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testVPFileLoad.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPFileLoad.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPFileLoad.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
        assertThat(testVPFileLoad.getNumLoaded()).isEqualTo(UPDATED_NUM_LOADED);
        assertThat(testVPFileLoad.getNumFailed()).isEqualTo(UPDATED_NUM_FAILED);
    }

    //@Test
    @Transactional
    void patchNonExistingVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPFileLoad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPFileLoad))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPFileLoad))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamVPFileLoad() throws Exception {
        int databaseSizeBeforeUpdate = vPFileLoadRepository.findAll().size();
        vPFileLoad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPFileLoadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPFileLoad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPFileLoad in the database
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteVPFileLoad() throws Exception {
        // Initialize the database
        vPFileLoadRepository.saveAndFlush(vPFileLoad);

        int databaseSizeBeforeDelete = vPFileLoadRepository.findAll().size();

        // Delete the vPFileLoad
        restVPFileLoadMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPFileLoad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPFileLoad> vPFileLoadList = vPFileLoadRepository.findAll();
        assertThat(vPFileLoadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
