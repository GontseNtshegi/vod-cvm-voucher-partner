package za.co.vodacom.cvm.web.rest;

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
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.web.rest.crud.VPVoucherDefResource;

/**
 * Integration tests for the {@link VPVoucherDefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPVoucherDefResourceIT {

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR = "BBBBBBBBBB";

    private static final String DEFAULT_EXT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_EXT_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALIDITY_PERIOD = 1;
    private static final Integer UPDATED_VALIDITY_PERIOD = 2;

    private static final Integer DEFAULT_CACHE_QUANTITY = 1;
    private static final Integer UPDATED_CACHE_QUANTITY = 2;

    private static final String DEFAULT_ENCRYPTED_YN = "A";
    private static final String UPDATED_ENCRYPTED_YN = "B";

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/v2/api/vp-voucher-defs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPVoucherDefRepository vPVoucherDefRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPVoucherDefMockMvc;

    private VPVoucherDef vPVoucherDef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPVoucherDef createEntity(EntityManager em) {
        VPVoucherDef vPVoucherDef = new VPVoucherDef()
            .id(DEFAULT_PRODUCT_ID)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .category(DEFAULT_CATEGORY)
            .vendor(DEFAULT_VENDOR)
            .extId(DEFAULT_EXT_ID)
            .extSystem(DEFAULT_EXT_SYSTEM)
            .templateId(DEFAULT_TEMPLATE_ID)
            .validityPeriod(DEFAULT_VALIDITY_PERIOD)
            .cacheQuantity(DEFAULT_CACHE_QUANTITY)
            .encryptedYN(DEFAULT_ENCRYPTED_YN)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return vPVoucherDef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPVoucherDef createUpdatedEntity(EntityManager em) {
        VPVoucherDef vPVoucherDef = new VPVoucherDef()
            //.productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .vendor(UPDATED_VENDOR)
            .extId(UPDATED_EXT_ID)
            .extSystem(UPDATED_EXT_SYSTEM)
            .templateId(UPDATED_TEMPLATE_ID)
            .validityPeriod(UPDATED_VALIDITY_PERIOD)
            .cacheQuantity(UPDATED_CACHE_QUANTITY)
            .encryptedYN(UPDATED_ENCRYPTED_YN)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return vPVoucherDef;
    }

    @BeforeEach
    public void initTest() {
        vPVoucherDef = createEntity(em);
    }

    //@Test
    @Transactional
    void createVPVoucherDef() throws Exception {
        int databaseSizeBeforeCreate = vPVoucherDefRepository.findAll().size();
        // Create the VPVoucherDef
        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isCreated());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeCreate + 1);
        VPVoucherDef testVPVoucherDef = vPVoucherDefList.get(vPVoucherDefList.size() - 1);
        //assertThat(testVPVoucherDef.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testVPVoucherDef.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVPVoucherDef.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVPVoucherDef.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testVPVoucherDef.getVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testVPVoucherDef.getExtId()).isEqualTo(DEFAULT_EXT_ID);
        assertThat(testVPVoucherDef.getExtSystem()).isEqualTo(DEFAULT_EXT_SYSTEM);
        assertThat(testVPVoucherDef.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testVPVoucherDef.getValidityPeriod()).isEqualTo(DEFAULT_VALIDITY_PERIOD);
        assertThat(testVPVoucherDef.getCacheQuantity()).isEqualTo(DEFAULT_CACHE_QUANTITY);
        assertThat(testVPVoucherDef.getEncryptedYN()).isEqualTo(DEFAULT_ENCRYPTED_YN);
        assertThat(testVPVoucherDef.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    //@Test
    @Transactional
    void createVPVoucherDefWithExistingId() throws Exception {
        // Create the VPVoucherDef with an existing ID
        vPVoucherDef.setId("1");

        int databaseSizeBeforeCreate = vPVoucherDefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
       // vPVoucherDef.setProductId(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setDescription(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setType(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setCategory(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkVendorIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setVendor(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkValidityPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setValidityPeriod(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCacheQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setCacheQuantity(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkEncryptedYNIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPVoucherDefRepository.findAll().size();
        // set the field null
        vPVoucherDef.setEncryptedYN(null);

        // Create the VPVoucherDef, which fails.

        restVPVoucherDefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isBadRequest());

        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPVoucherDefs() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        // Get all the vPVoucherDefList
        restVPVoucherDefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPVoucherDef.getId())))
            //.andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].vendor").value(hasItem(DEFAULT_VENDOR)))
            .andExpect(jsonPath("$.[*].extId").value(hasItem(DEFAULT_EXT_ID)))
            .andExpect(jsonPath("$.[*].extSystem").value(hasItem(DEFAULT_EXT_SYSTEM)))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID)))
            .andExpect(jsonPath("$.[*].validityPeriod").value(hasItem(DEFAULT_VALIDITY_PERIOD)))
            .andExpect(jsonPath("$.[*].cacheQuantity").value(hasItem(DEFAULT_CACHE_QUANTITY)))
            .andExpect(jsonPath("$.[*].encryptedYN").value(hasItem(DEFAULT_ENCRYPTED_YN)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    //@Test
    @Transactional
    void getVPVoucherDef() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        // Get the vPVoucherDef
        restVPVoucherDefMockMvc
            .perform(get(ENTITY_API_URL_ID, vPVoucherDef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            //.andExpect(jsonPath("$.id").value(vPVoucherDef.getId().intValue()))
            .andExpect(jsonPath("$.id").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.vendor").value(DEFAULT_VENDOR))
            .andExpect(jsonPath("$.extId").value(DEFAULT_EXT_ID))
            .andExpect(jsonPath("$.extSystem").value(DEFAULT_EXT_SYSTEM))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID))
            .andExpect(jsonPath("$.validityPeriod").value(DEFAULT_VALIDITY_PERIOD))
            .andExpect(jsonPath("$.cacheQuantity").value(DEFAULT_CACHE_QUANTITY))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    //@Test
    @Transactional
    void getNonExistingVPVoucherDef() throws Exception {
        // Get the vPVoucherDef
        restVPVoucherDefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPVoucherDef() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();

        // Update the vPVoucherDef
        VPVoucherDef updatedVPVoucherDef = vPVoucherDefRepository.findById(vPVoucherDef.getId()).get();
        // Disconnect from session so that the updates on updatedVPVoucherDef are not directly saved in db
        em.detach(updatedVPVoucherDef);
        updatedVPVoucherDef
            //.productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .vendor(UPDATED_VENDOR)
            .extId(UPDATED_EXT_ID)
            .extSystem(UPDATED_EXT_SYSTEM)
            .templateId(UPDATED_TEMPLATE_ID)
            .validityPeriod(UPDATED_VALIDITY_PERIOD)
            .cacheQuantity(UPDATED_CACHE_QUANTITY)
            .encryptedYN(UPDATED_ENCRYPTED_YN)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restVPVoucherDefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPVoucherDef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPVoucherDef))
            )
            .andExpect(status().isOk());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
        VPVoucherDef testVPVoucherDef = vPVoucherDefList.get(vPVoucherDefList.size() - 1);
        //assertThat(testVPVoucherDef.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testVPVoucherDef.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVPVoucherDef.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVPVoucherDef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testVPVoucherDef.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testVPVoucherDef.getExtId()).isEqualTo(UPDATED_EXT_ID);
        assertThat(testVPVoucherDef.getExtSystem()).isEqualTo(UPDATED_EXT_SYSTEM);
        assertThat(testVPVoucherDef.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testVPVoucherDef.getValidityPeriod()).isEqualTo(UPDATED_VALIDITY_PERIOD);
        assertThat(testVPVoucherDef.getCacheQuantity()).isEqualTo(UPDATED_CACHE_QUANTITY);
        assertThat(testVPVoucherDef.getEncryptedYN()).isEqualTo(UPDATED_ENCRYPTED_YN);
        assertThat(testVPVoucherDef.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    //@Test
    @Transactional
    void putNonExistingVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPVoucherDef.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPVoucherDef))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPVoucherDef))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPVoucherDef)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPVoucherDefWithPatch() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();

        // Update the vPVoucherDef using partial update
        VPVoucherDef partialUpdatedVPVoucherDef = new VPVoucherDef();
        partialUpdatedVPVoucherDef.setId(vPVoucherDef.getId());

        partialUpdatedVPVoucherDef
            //.productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .extId(UPDATED_EXT_ID)
            .cacheQuantity(UPDATED_CACHE_QUANTITY)
            .encryptedYN(UPDATED_ENCRYPTED_YN);

        restVPVoucherDefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPVoucherDef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPVoucherDef))
            )
            .andExpect(status().isOk());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
        VPVoucherDef testVPVoucherDef = vPVoucherDefList.get(vPVoucherDefList.size() - 1);
        //assertThat(testVPVoucherDef.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testVPVoucherDef.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVPVoucherDef.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVPVoucherDef.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testVPVoucherDef.getVendor()).isEqualTo(DEFAULT_VENDOR);
        assertThat(testVPVoucherDef.getExtId()).isEqualTo(UPDATED_EXT_ID);
        assertThat(testVPVoucherDef.getExtSystem()).isEqualTo(DEFAULT_EXT_SYSTEM);
        assertThat(testVPVoucherDef.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testVPVoucherDef.getValidityPeriod()).isEqualTo(DEFAULT_VALIDITY_PERIOD);
        assertThat(testVPVoucherDef.getCacheQuantity()).isEqualTo(UPDATED_CACHE_QUANTITY);
        assertThat(testVPVoucherDef.getEncryptedYN()).isEqualTo(UPDATED_ENCRYPTED_YN);
        assertThat(testVPVoucherDef.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    //@Test
    @Transactional
    void fullUpdateVPVoucherDefWithPatch() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();

        // Update the vPVoucherDef using partial update
        VPVoucherDef partialUpdatedVPVoucherDef = new VPVoucherDef();
        partialUpdatedVPVoucherDef.setId(vPVoucherDef.getId());

        partialUpdatedVPVoucherDef
            //.productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .vendor(UPDATED_VENDOR)
            .extId(UPDATED_EXT_ID)
            .extSystem(UPDATED_EXT_SYSTEM)
            .templateId(UPDATED_TEMPLATE_ID)
            .validityPeriod(UPDATED_VALIDITY_PERIOD)
            .cacheQuantity(UPDATED_CACHE_QUANTITY)
            .encryptedYN(UPDATED_ENCRYPTED_YN)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restVPVoucherDefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPVoucherDef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPVoucherDef))
            )
            .andExpect(status().isOk());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
        VPVoucherDef testVPVoucherDef = vPVoucherDefList.get(vPVoucherDefList.size() - 1);
        //assertThat(testVPVoucherDef.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testVPVoucherDef.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVPVoucherDef.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVPVoucherDef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testVPVoucherDef.getVendor()).isEqualTo(UPDATED_VENDOR);
        assertThat(testVPVoucherDef.getExtId()).isEqualTo(UPDATED_EXT_ID);
        assertThat(testVPVoucherDef.getExtSystem()).isEqualTo(UPDATED_EXT_SYSTEM);
        assertThat(testVPVoucherDef.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testVPVoucherDef.getValidityPeriod()).isEqualTo(UPDATED_VALIDITY_PERIOD);
        assertThat(testVPVoucherDef.getCacheQuantity()).isEqualTo(UPDATED_CACHE_QUANTITY);
        assertThat(testVPVoucherDef.getEncryptedYN()).isEqualTo(UPDATED_ENCRYPTED_YN);
        assertThat(testVPVoucherDef.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    //@Test
    @Transactional
    void patchNonExistingVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPVoucherDef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPVoucherDef))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPVoucherDef))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamVPVoucherDef() throws Exception {
        int databaseSizeBeforeUpdate = vPVoucherDefRepository.findAll().size();
        vPVoucherDef.setId("2");

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPVoucherDefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPVoucherDef))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPVoucherDef in the database
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteVPVoucherDef() throws Exception {
        // Initialize the database
        vPVoucherDefRepository.saveAndFlush(vPVoucherDef);

        int databaseSizeBeforeDelete = vPVoucherDefRepository.findAll().size();

        // Delete the vPVoucherDef
        restVPVoucherDefMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPVoucherDef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPVoucherDef> vPVoucherDefList = vPVoucherDefRepository.findAll();
        assertThat(vPVoucherDefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
