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
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.web.rest.TestUtil;
import za.co.vodacom.cvm.web.rest.crud.VPBatchResource;

/**
 * Integration tests for the {@link VPBatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPBatchResourceIT {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LOAD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LOAD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RESTRICTED_YN = "A";
    private static final String UPDATED_RESTRICTED_YN = "B";

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String ENTITY_API_URL = "/v2/api/vp-batches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPBatchRepository vPBatchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPBatchMockMvc;

    private VPBatch vPBatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPBatch createEntity(EntityManager em) {
        VPBatch vPBatch = new VPBatch()
            .createDate(DEFAULT_CREATE_DATE)
            .loadDate(DEFAULT_LOAD_DATE)
            .comment(DEFAULT_COMMENT)
            .restrictedYN(DEFAULT_RESTRICTED_YN);
        return vPBatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPBatch createUpdatedEntity(EntityManager em) {
        VPBatch vPBatch = new VPBatch()
            .createDate(UPDATED_CREATE_DATE)
            .loadDate(UPDATED_LOAD_DATE)
            .comment(UPDATED_COMMENT)
            .restrictedYN(UPDATED_RESTRICTED_YN);
        return vPBatch;
    }

    @BeforeEach
    public void initTest() {
        vPBatch = createEntity(em);
    }

    //////////@Test
    @Transactional
    void createVPBatch() throws Exception {
        int databaseSizeBeforeCreate = vPBatchRepository.findAll().size();
        // Create the VPBatch
        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isCreated());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeCreate + 1);
        VPBatch testVPBatch = vPBatchList.get(vPBatchList.size() - 1);
        assertThat(testVPBatch.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPBatch.getLoadDate()).isEqualTo(DEFAULT_LOAD_DATE);
        assertThat(testVPBatch.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testVPBatch.getRestrictedYN()).isEqualTo(DEFAULT_RESTRICTED_YN);
    }

    //@Test
    @Transactional
    void createVPBatchWithExistingId() throws Exception {
        // Create the VPBatch with an existing ID
        vPBatch.setId(1L);

        int databaseSizeBeforeCreate = vPBatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isBadRequest());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPBatchRepository.findAll().size();
        // set the field null
        vPBatch.setCreateDate(null);

        // Create the VPBatch, which fails.

        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isBadRequest());

        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPBatchRepository.findAll().size();
        // set the field null
        vPBatch.setComment(null);

        // Create the VPBatch, which fails.

        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isBadRequest());

        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkRestrictedYNIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPBatchRepository.findAll().size();
        // set the field null
        vPBatch.setRestrictedYN(null);

        // Create the VPBatch, which fails.

        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isBadRequest());

        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPBatchRepository.findAll().size();

        // Create the VPBatch, which fails.

        restVPBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isBadRequest());

        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPBatches() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        // Get all the vPBatchList
        restVPBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].loadDate").value(hasItem(sameInstant(DEFAULT_LOAD_DATE))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].restrictedYN").value(hasItem(DEFAULT_RESTRICTED_YN)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    //@Test
    @Transactional
    void getVPBatch() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        // Get the vPBatch
        restVPBatchMockMvc
            .perform(get(ENTITY_API_URL_ID, vPBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPBatch.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.loadDate").value(sameInstant(DEFAULT_LOAD_DATE)))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.restrictedYN").value(DEFAULT_RESTRICTED_YN))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    //@Test
    @Transactional
    void getNonExistingVPBatch() throws Exception {
        // Get the vPBatch
        restVPBatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPBatch() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();

        // Update the vPBatch
        VPBatch updatedVPBatch = vPBatchRepository.findById(vPBatch.getId()).get();
        // Disconnect from session so that the updates on updatedVPBatch are not directly saved in db
        em.detach(updatedVPBatch);
        updatedVPBatch
            .createDate(UPDATED_CREATE_DATE)
            .loadDate(UPDATED_LOAD_DATE)
            .comment(UPDATED_COMMENT)
            .restrictedYN(UPDATED_RESTRICTED_YN);

        restVPBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPBatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPBatch))
            )
            .andExpect(status().isOk());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
        VPBatch testVPBatch = vPBatchList.get(vPBatchList.size() - 1);
        assertThat(testVPBatch.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPBatch.getLoadDate()).isEqualTo(UPDATED_LOAD_DATE);
        assertThat(testVPBatch.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testVPBatch.getRestrictedYN()).isEqualTo(UPDATED_RESTRICTED_YN);
    }

    //@Test
    @Transactional
    void putNonExistingVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPBatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPBatchWithPatch() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();

        // Update the vPBatch using partial update
        VPBatch partialUpdatedVPBatch = new VPBatch();
        partialUpdatedVPBatch.setId(vPBatch.getId());

        partialUpdatedVPBatch.loadDate(UPDATED_LOAD_DATE);

        restVPBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPBatch))
            )
            .andExpect(status().isOk());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
        VPBatch testVPBatch = vPBatchList.get(vPBatchList.size() - 1);
        assertThat(testVPBatch.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPBatch.getLoadDate()).isEqualTo(UPDATED_LOAD_DATE);
        assertThat(testVPBatch.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testVPBatch.getRestrictedYN()).isEqualTo(DEFAULT_RESTRICTED_YN);
    }

    //@Test
    @Transactional
    void fullUpdateVPBatchWithPatch() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();

        // Update the vPBatch using partial update
        VPBatch partialUpdatedVPBatch = new VPBatch();
        partialUpdatedVPBatch.setId(vPBatch.getId());

        partialUpdatedVPBatch
            .createDate(UPDATED_CREATE_DATE)
            .loadDate(UPDATED_LOAD_DATE)
            .comment(UPDATED_COMMENT)
            .restrictedYN(UPDATED_RESTRICTED_YN);

        restVPBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPBatch))
            )
            .andExpect(status().isOk());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
        VPBatch testVPBatch = vPBatchList.get(vPBatchList.size() - 1);
        assertThat(testVPBatch.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPBatch.getLoadDate()).isEqualTo(UPDATED_LOAD_DATE);
        assertThat(testVPBatch.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testVPBatch.getRestrictedYN()).isEqualTo(UPDATED_RESTRICTED_YN);
    }

    //@Test
    @Transactional
    void patchNonExistingVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    ////@Test
    @Transactional
    void patchWithIdMismatchVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPBatch))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    ////@Test
    @Transactional
    void patchWithMissingIdPathParamVPBatch() throws Exception {
        int databaseSizeBeforeUpdate = vPBatchRepository.findAll().size();
        vPBatch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPBatchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPBatch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPBatch in the database
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    // //@Test
    @Transactional
    void deleteVPBatch() throws Exception {
        // Initialize the database
        vPBatchRepository.saveAndFlush(vPBatch);

        int databaseSizeBeforeDelete = vPBatchRepository.findAll().size();

        // Delete the vPBatch
        restVPBatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPBatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPBatch> vPBatchList = vPBatchRepository.findAll();
        assertThat(vPBatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
