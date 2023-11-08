package za.co.vodacom.cvm.web.rest.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.vodacom.cvm.web.rest.TestUtil.sameInstant;

import java.math.BigDecimal;
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
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.web.rest.TestUtil;
import za.co.vodacom.cvm.web.rest.crud.VPVouchersResource;

/**
 * Integration tests for the {@link VPVouchersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class VPVouchersResourceIT {

    private static final Integer DEFAULT_BATCH_ID = 1;
    private static final Integer UPDATED_BATCH_ID = 2;

    private static final Integer DEFAULT_FILE_ID = 1;
    private static final Integer UPDATED_FILE_ID = 2;

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VOUCHER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime TEST_END_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(System.currentTimeMillis() + 240 * 60 * 1000),
        ZoneOffset.systemDefault()
    );

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COLLECTION_POINT = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION_POINT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ISSUED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ISSUED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REVERSED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REVERSED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SOURCE_TRXID = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_TRXID = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/v2/api/vp-vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPVouchersRepository vPVouchersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPVouchersMockMvc;

    private VPVouchers vPVouchers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPVouchers createEntity(EntityManager em) {
        VPVouchers vPVouchers = new VPVouchers()
            .batchId(DEFAULT_BATCH_ID)
            .fileId(DEFAULT_FILE_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .voucherCode(DEFAULT_VOUCHER_CODE)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .collectionPoint(DEFAULT_COLLECTION_POINT)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .reversedDate(DEFAULT_REVERSED_DATE)
            .sourceTrxid(DEFAULT_SOURCE_TRXID)
            .quantity(DEFAULT_QUANTITY);
        return vPVouchers;
    }

    public static VPVouchers createTestEntity(EntityManager em, Integer batchId) {
        VPVouchers vPVouchers = new VPVouchers()
            .batchId(batchId)
            .fileId(DEFAULT_FILE_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .voucherCode(DEFAULT_VOUCHER_CODE)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(TEST_END_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .collectionPoint(DEFAULT_COLLECTION_POINT)
            .reversedDate(DEFAULT_REVERSED_DATE)
            .sourceTrxid(DEFAULT_SOURCE_TRXID)
            .quantity(DEFAULT_QUANTITY);
        return vPVouchers;
    }

    public static VPVouchers createTestEntity(EntityManager em, String productId) {
        VPVouchers vPVouchers = new VPVouchers()
            .batchId(DEFAULT_BATCH_ID)
            .fileId(DEFAULT_FILE_ID)
            .productId(productId)
            .voucherCode(DEFAULT_VOUCHER_CODE)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(TEST_END_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .collectionPoint(DEFAULT_COLLECTION_POINT)
            .reversedDate(DEFAULT_REVERSED_DATE)
            .sourceTrxid(DEFAULT_SOURCE_TRXID)
            .quantity(DEFAULT_QUANTITY);
        return vPVouchers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPVouchers createUpdatedEntity(EntityManager em) {
        VPVouchers vPVouchers = new VPVouchers()
            .batchId(UPDATED_BATCH_ID)
            .fileId(UPDATED_FILE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .voucherCode(UPDATED_VOUCHER_CODE)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .collectionPoint(UPDATED_COLLECTION_POINT)
            .issuedDate(UPDATED_ISSUED_DATE)
            .reversedDate(UPDATED_REVERSED_DATE)
            .sourceTrxid(UPDATED_SOURCE_TRXID)
            .quantity(UPDATED_QUANTITY);
        return vPVouchers;
    }

    @BeforeEach
    public void initTest() {
        vPVouchers = createEntity(em);
    }

    @Test
    @Transactional
    void createVPVouchers() throws Exception {
        int databaseSizeBeforeCreate = vPVouchersRepository.findAll().size();
        // Create the VPVouchers
        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isCreated());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeCreate + 1);
        VPVouchers testVPVouchers = vPVouchersList.get(vPVouchersList.size() - 1);
        assertThat(testVPVouchers.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testVPVouchers.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        //assertThat(testVPVouchers.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testVPVouchers.getVoucherCode()).isEqualTo(DEFAULT_VOUCHER_CODE);
        assertThat(testVPVouchers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVPVouchers.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPVouchers.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVPVouchers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testVPVouchers.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testVPVouchers.getCollectionPoint()).isEqualTo(DEFAULT_COLLECTION_POINT);
        assertThat(testVPVouchers.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testVPVouchers.getReversedDate()).isEqualTo(DEFAULT_REVERSED_DATE);
        assertThat(testVPVouchers.getSourceTrxid()).isEqualTo(DEFAULT_SOURCE_TRXID);
        assertThat(testVPVouchers.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createVPVouchersWithExistingId() throws Exception {
        // Create the VPVouchers with an existing ID
        vPVouchers.setId(1L);

        int databaseSizeBeforeCreate = vPVouchersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBatchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setBatchId(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setProductId(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVoucherCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setVoucherCode(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setDescription(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setCreateDate(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isInternalServerError());
        //        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        //        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setStartDate(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isInternalServerError());
        //        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        //        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setEndDate(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isInternalServerError());
        //        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        //        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCollectionPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setCollectionPoint(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReversedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setReversedDate(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isInternalServerError());
        //        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        //        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVouchersRepository.findAll().size();
        // set the field null
        vPVouchers.setQuantity(null);

        // Create the VPVouchers, which fails.

        restVPVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isBadRequest());

        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVPVouchers() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        // Get all the vPVouchersList
        restVPVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPVouchers.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].voucherCode").value(hasItem(DEFAULT_VOUCHER_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].collectionPoint").value(hasItem(DEFAULT_COLLECTION_POINT)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(sameInstant(DEFAULT_ISSUED_DATE))))
            .andExpect(jsonPath("$.[*].reversedDate").value(hasItem(sameInstant(DEFAULT_REVERSED_DATE))))
            .andExpect(jsonPath("$.[*].sourceTrxid").value(hasItem(DEFAULT_SOURCE_TRXID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getVPVouchers() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        // Get the vPVouchers
        restVPVouchersMockMvc
            .perform(get(ENTITY_API_URL_ID, vPVouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPVouchers.getId().intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_VOUCHER_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.collectionPoint").value(DEFAULT_COLLECTION_POINT))
            .andExpect(jsonPath("$.issuedDate").value(sameInstant(DEFAULT_ISSUED_DATE)))
            .andExpect(jsonPath("$.reversedDate").value(sameInstant(DEFAULT_REVERSED_DATE)))
            .andExpect(jsonPath("$.sourceTrxid").value(DEFAULT_SOURCE_TRXID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingVPVouchers() throws Exception {
        // Get the vPVouchers
        restVPVouchersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVPVouchers() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();

        // Update the vPVouchers
        VPVouchers updatedVPVouchers = vPVouchersRepository.findById(vPVouchers.getId()).get();
        // Disconnect from session so that the updates on updatedVPVouchers are not directly saved in db
        em.detach(updatedVPVouchers);
        updatedVPVouchers
            .batchId(UPDATED_BATCH_ID)
            .fileId(UPDATED_FILE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .voucherCode(UPDATED_VOUCHER_CODE)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .collectionPoint(UPDATED_COLLECTION_POINT)
            .issuedDate(UPDATED_ISSUED_DATE)
            .reversedDate(UPDATED_REVERSED_DATE)
            .sourceTrxid(UPDATED_SOURCE_TRXID)
            .quantity(UPDATED_QUANTITY);

        restVPVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPVouchers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPVouchers testVPVouchers = vPVouchersList.get(vPVouchersList.size() - 1);
        assertThat(testVPVouchers.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPVouchers.getFileId()).isEqualTo(UPDATED_FILE_ID);
        // assertThat(testVPVouchers.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testVPVouchers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVPVouchers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPVouchers.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVPVouchers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testVPVouchers.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testVPVouchers.getCollectionPoint()).isEqualTo(UPDATED_COLLECTION_POINT);
        assertThat(testVPVouchers.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testVPVouchers.getReversedDate()).isEqualTo(UPDATED_REVERSED_DATE);
        assertThat(testVPVouchers.getSourceTrxid()).isEqualTo(UPDATED_SOURCE_TRXID);
        assertThat(testVPVouchers.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    //@Test
    @Transactional
    void putNonExistingVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPVouchers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPVouchers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVouchers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVPVouchersWithPatch() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();

        // Update the vPVouchers using partial update
        VPVouchers partialUpdatedVPVouchers = new VPVouchers();
        partialUpdatedVPVouchers.setId(vPVouchers.getId());

        partialUpdatedVPVouchers
            .batchId(UPDATED_BATCH_ID)
            .fileId(UPDATED_FILE_ID)
            .voucherCode(UPDATED_VOUCHER_CODE)
            .createDate(UPDATED_CREATE_DATE)
            .collectionPoint(UPDATED_COLLECTION_POINT)
            .issuedDate(UPDATED_ISSUED_DATE);

        restVPVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPVouchers testVPVouchers = vPVouchersList.get(vPVouchersList.size() - 1);
        assertThat(testVPVouchers.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPVouchers.getFileId()).isEqualTo(UPDATED_FILE_ID);
        // assertThat(testVPVouchers.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        // assertThat(testVPVouchers.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testVPVouchers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVPVouchers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPVouchers.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVPVouchers.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testVPVouchers.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testVPVouchers.getCollectionPoint()).isEqualTo(UPDATED_COLLECTION_POINT);
        assertThat(testVPVouchers.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testVPVouchers.getReversedDate()).isEqualTo(DEFAULT_REVERSED_DATE);
        assertThat(testVPVouchers.getSourceTrxid()).isEqualTo(DEFAULT_SOURCE_TRXID);
        assertThat(testVPVouchers.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateVPVouchersWithPatch() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();

        // Update the vPVouchers using partial update
        VPVouchers partialUpdatedVPVouchers = new VPVouchers();
        partialUpdatedVPVouchers.setId(vPVouchers.getId());

        partialUpdatedVPVouchers
            .batchId(UPDATED_BATCH_ID)
            .fileId(UPDATED_FILE_ID)
            .productId(UPDATED_PRODUCT_ID)
            .voucherCode(UPDATED_VOUCHER_CODE)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .collectionPoint(UPDATED_COLLECTION_POINT)
            .issuedDate(UPDATED_ISSUED_DATE)
            .reversedDate(UPDATED_REVERSED_DATE)
            .sourceTrxid(UPDATED_SOURCE_TRXID)
            .quantity(UPDATED_QUANTITY);

        restVPVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPVouchers))
            )
            .andExpect(status().isOk());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
        VPVouchers testVPVouchers = vPVouchersList.get(vPVouchersList.size() - 1);
        assertThat(testVPVouchers.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testVPVouchers.getFileId()).isEqualTo(UPDATED_FILE_ID);
        //  assertThat(testVPVouchers.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        //  assertThat(testVPVouchers.getVoucherCode()).isEqualTo(UPDATED_VOUCHER_CODE);
        assertThat(testVPVouchers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVPVouchers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPVouchers.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVPVouchers.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testVPVouchers.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testVPVouchers.getCollectionPoint()).isEqualTo(UPDATED_COLLECTION_POINT);
        assertThat(testVPVouchers.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testVPVouchers.getReversedDate()).isEqualTo(UPDATED_REVERSED_DATE);
        assertThat(testVPVouchers.getSourceTrxid()).isEqualTo(UPDATED_SOURCE_TRXID);
        assertThat(testVPVouchers.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPVouchers))
            )
            .andExpect(status().isNotFound());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPVouchers))
            )
            .andExpect(status().isNotFound());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVPVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vPVouchersRepository.findAll().size();
        vPVouchers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPVouchers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPVouchers in the database
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVPVouchers() throws Exception {
        // Initialize the database
        vPVouchersRepository.saveAndFlush(vPVouchers);

        int databaseSizeBeforeDelete = vPVouchersRepository.findAll().size();

        // Delete the vPVouchers
        restVPVouchersMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPVouchers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPVouchers> vPVouchersList = vPVouchersRepository.findAll();
        assertThat(vPVouchersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
