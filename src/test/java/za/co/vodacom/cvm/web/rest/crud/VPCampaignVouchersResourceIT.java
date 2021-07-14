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
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.web.rest.TestUtil;
import za.co.vodacom.cvm.web.rest.crud.VPCampaignVouchersResource;

/**
 * Integration tests for the {@link VPCampaignVouchersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPCampaignVouchersResourceIT {

    private static final Long DEFAULT_CAMPAIGN_ID = 3L;
    private static final Long UPDATED_CAMPAIGN_ID = 4L;

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA1";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB2";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACTIVE_YN = "A";
    private static final String UPDATED_ACTIVE_YN = "B";

    private static final String ENTITY_API_URL = "/v2/api/vp-campaign-vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPCampaignVouchersRepository vPCampaignVouchersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPCampaignVouchersMockMvc;

    private VPCampaignVouchers vPCampaignVouchers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPCampaignVouchers createEntity(EntityManager em) {
        VPCampaignVouchers vPCampaignVouchers = new VPCampaignVouchers()
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .createDate(DEFAULT_CREATE_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .activeYN(DEFAULT_ACTIVE_YN);
        return vPCampaignVouchers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPCampaignVouchers createUpdatedEntity(EntityManager em) {
        VPCampaignVouchers vPCampaignVouchers = new VPCampaignVouchers()
            .campaignId(UPDATED_CAMPAIGN_ID)
            .productId(UPDATED_PRODUCT_ID)
            .createDate(UPDATED_CREATE_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .activeYN(UPDATED_ACTIVE_YN);
        return vPCampaignVouchers;
    }

    @BeforeEach
    public void initTest() {
        vPCampaignVouchers = createEntity(em);
    }

    //@Test
    @Transactional
    void createVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeCreate = vPCampaignVouchersRepository.findAll().size();
        // Create the VPCampaignVouchers
        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isCreated());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeCreate + 1);
        VPCampaignVouchers testVPCampaignVouchers = vPCampaignVouchersList.get(vPCampaignVouchersList.size() - 1);
        assertThat(testVPCampaignVouchers.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testVPCampaignVouchers.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testVPCampaignVouchers.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPCampaignVouchers.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testVPCampaignVouchers.getActiveYN()).isEqualTo(DEFAULT_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void createVPCampaignVouchersWithExistingId() throws Exception {
        // Create the VPCampaignVouchers with an existing ID
        vPCampaignVouchers.setId(1L);

        int databaseSizeBeforeCreate = vPCampaignVouchersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkCampaignIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignVouchersRepository.findAll().size();
        // set the field null
        vPCampaignVouchers.setCampaignId(null);

        // Create the VPCampaignVouchers, which fails.

        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignVouchersRepository.findAll().size();
        // set the field null
        vPCampaignVouchers.setProductId(null);

        // Create the VPCampaignVouchers, which fails.

        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignVouchersRepository.findAll().size();
        // set the field null
        vPCampaignVouchers.setCreateDate(null);

        // Create the VPCampaignVouchers, which fails.

        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignVouchersRepository.findAll().size();
        // set the field null
        vPCampaignVouchers.setModifiedDate(null);

        // Create the VPCampaignVouchers, which fails.

        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkActiveYNIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignVouchersRepository.findAll().size();
        // set the field null
        vPCampaignVouchers.setActiveYN(null);

        // Create the VPCampaignVouchers, which fails.

        restVPCampaignVouchersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPCampaignVouchers() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        // Get all the vPCampaignVouchersList
        restVPCampaignVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPCampaignVouchers.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].activeYN").value(hasItem(DEFAULT_ACTIVE_YN)));
    }

    //@Test
    @Transactional
    void getVPCampaignVouchers() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        // Get the vPCampaignVouchers
        restVPCampaignVouchersMockMvc
            .perform(get(ENTITY_API_URL_ID, vPCampaignVouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPCampaignVouchers.getId().intValue()))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.activeYN").value(DEFAULT_ACTIVE_YN));
    }

    //@Test
    @Transactional
    void getNonExistingVPCampaignVouchers() throws Exception {
        // Get the vPCampaignVouchers
        restVPCampaignVouchersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPCampaignVouchers() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();

        // Update the vPCampaignVouchers
        VPCampaignVouchers updatedVPCampaignVouchers = vPCampaignVouchersRepository.findById(vPCampaignVouchers.getId()).get();
        // Disconnect from session so that the updates on updatedVPCampaignVouchers are not directly saved in db
        em.detach(updatedVPCampaignVouchers);
        updatedVPCampaignVouchers
            .campaignId(UPDATED_CAMPAIGN_ID)
            .productId(UPDATED_PRODUCT_ID)
            .createDate(UPDATED_CREATE_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .activeYN(UPDATED_ACTIVE_YN);

        restVPCampaignVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPCampaignVouchers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPCampaignVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPCampaignVouchers testVPCampaignVouchers = vPCampaignVouchersList.get(vPCampaignVouchersList.size() - 1);
        assertThat(testVPCampaignVouchers.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testVPCampaignVouchers.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testVPCampaignVouchers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPCampaignVouchers.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testVPCampaignVouchers.getActiveYN()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void putNonExistingVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPCampaignVouchers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPCampaignVouchersWithPatch() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();

        // Update the vPCampaignVouchers using partial update
        VPCampaignVouchers partialUpdatedVPCampaignVouchers = new VPCampaignVouchers();
        partialUpdatedVPCampaignVouchers.setId(vPCampaignVouchers.getId());

        partialUpdatedVPCampaignVouchers.activeYN(UPDATED_ACTIVE_YN);

        restVPCampaignVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPCampaignVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPCampaignVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPCampaignVouchers testVPCampaignVouchers = vPCampaignVouchersList.get(vPCampaignVouchersList.size() - 1);
        assertThat(testVPCampaignVouchers.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testVPCampaignVouchers.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testVPCampaignVouchers.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPCampaignVouchers.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testVPCampaignVouchers.getActiveYN()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void fullUpdateVPCampaignVouchersWithPatch() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();

        // Update the vPCampaignVouchers using partial update
        VPCampaignVouchers partialUpdatedVPCampaignVouchers = new VPCampaignVouchers();
        partialUpdatedVPCampaignVouchers.setId(vPCampaignVouchers.getId());

        partialUpdatedVPCampaignVouchers
            .campaignId(UPDATED_CAMPAIGN_ID)
            .productId(UPDATED_PRODUCT_ID)
            .createDate(UPDATED_CREATE_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .activeYN(UPDATED_ACTIVE_YN);

        restVPCampaignVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPCampaignVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPCampaignVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPCampaignVouchers testVPCampaignVouchers = vPCampaignVouchersList.get(vPCampaignVouchersList.size() - 1);
        assertThat(testVPCampaignVouchers.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testVPCampaignVouchers.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testVPCampaignVouchers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPCampaignVouchers.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testVPCampaignVouchers.getActiveYN()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void patchNonExistingVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPCampaignVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamVPCampaignVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignVouchersRepository.findAll().size();
        vPCampaignVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaignVouchers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPCampaignVouchers in the database
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteVPCampaignVouchers() throws Exception {
        // Initialize the database
        vPCampaignVouchersRepository.saveAndFlush(vPCampaignVouchers);

        int databaseSizeBeforeDelete = vPCampaignVouchersRepository.findAll().size();

        // Delete the vPCampaignVouchers
        restVPCampaignVouchersMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPCampaignVouchers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPCampaignVouchers> vPCampaignVouchersList = vPCampaignVouchersRepository.findAll();
        assertThat(vPCampaignVouchersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
