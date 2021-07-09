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
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.web.rest.crud.VPCampaignResource;

/**
 * Integration tests for the {@link VPCampaignResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPCampaignResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/v2/api/vp-campaigns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPCampaignRepository vPCampaignRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPCampaignMockMvc;

    private VPCampaign vPCampaign;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPCampaign createEntity(EntityManager em) {
        VPCampaign vPCampaign = new VPCampaign().name(DEFAULT_NAME).startDate(DEFAULT_START_DATE).endDate(DEFAULT_END_DATE);
        return vPCampaign;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPCampaign createUpdatedEntity(EntityManager em) {
        VPCampaign vPCampaign = new VPCampaign().name(UPDATED_NAME).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        return vPCampaign;
    }

    @BeforeEach
    public void initTest() {
        vPCampaign = createEntity(em);
    }

    //@Test
    @Transactional
    void createVPCampaign() throws Exception {
        int databaseSizeBeforeCreate = vPCampaignRepository.findAll().size();
        // Create the VPCampaign
        restVPCampaignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isCreated());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeCreate + 1);
        VPCampaign testVPCampaign = vPCampaignList.get(vPCampaignList.size() - 1);
        assertThat(testVPCampaign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVPCampaign.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVPCampaign.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    //@Test
    @Transactional
    void createVPCampaignWithExistingId() throws Exception {
        // Create the VPCampaign with an existing ID
        vPCampaign.setId(1L);

        int databaseSizeBeforeCreate = vPCampaignRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPCampaignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isBadRequest());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignRepository.findAll().size();
        // set the field null
        vPCampaign.setName(null);

        // Create the VPCampaign, which fails.

        restVPCampaignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isBadRequest());

        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignRepository.findAll().size();
        // set the field null
        vPCampaign.setStartDate(null);

        // Create the VPCampaign, which fails.

        restVPCampaignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isBadRequest());

        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPCampaignRepository.findAll().size();
        // set the field null
        vPCampaign.setEndDate(null);

        // Create the VPCampaign, which fails.

        restVPCampaignMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isBadRequest());

        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPCampaigns() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        // Get all the vPCampaignList
        restVPCampaignMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPCampaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    //@Test
    @Transactional
    void getVPCampaign() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        // Get the vPCampaign
        restVPCampaignMockMvc
            .perform(get(ENTITY_API_URL_ID, vPCampaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPCampaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    //@Test
    @Transactional
    void getNonExistingVPCampaign() throws Exception {
        // Get the vPCampaign
        restVPCampaignMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPCampaign() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();

        // Update the vPCampaign
        VPCampaign updatedVPCampaign = vPCampaignRepository.findById(vPCampaign.getId()).get();
        // Disconnect from session so that the updates on updatedVPCampaign are not directly saved in db
        em.detach(updatedVPCampaign);
        updatedVPCampaign.name(UPDATED_NAME).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restVPCampaignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPCampaign.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPCampaign))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
        VPCampaign testVPCampaign = vPCampaignList.get(vPCampaignList.size() - 1);
        assertThat(testVPCampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVPCampaign.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVPCampaign.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    //@Test
    @Transactional
    void putNonExistingVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPCampaign.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaign))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaign))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPCampaign)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPCampaignWithPatch() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();

        // Update the vPCampaign using partial update
        VPCampaign partialUpdatedVPCampaign = new VPCampaign();
        partialUpdatedVPCampaign.setId(vPCampaign.getId());

        partialUpdatedVPCampaign.endDate(UPDATED_END_DATE);

        restVPCampaignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPCampaign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPCampaign))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
        VPCampaign testVPCampaign = vPCampaignList.get(vPCampaignList.size() - 1);
        assertThat(testVPCampaign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVPCampaign.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVPCampaign.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    //@Test
    @Transactional
    void fullUpdateVPCampaignWithPatch() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();

        // Update the vPCampaign using partial update
        VPCampaign partialUpdatedVPCampaign = new VPCampaign();
        partialUpdatedVPCampaign.setId(vPCampaign.getId());

        partialUpdatedVPCampaign.name(UPDATED_NAME).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restVPCampaignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPCampaign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPCampaign))
            )
            .andExpect(status().isOk());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
        VPCampaign testVPCampaign = vPCampaignList.get(vPCampaignList.size() - 1);
        assertThat(testVPCampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVPCampaign.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVPCampaign.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    //@Test
    @Transactional
    void patchNonExistingVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPCampaign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaign))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPCampaign))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamVPCampaign() throws Exception {
        int databaseSizeBeforeUpdate = vPCampaignRepository.findAll().size();
        vPCampaign.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPCampaignMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPCampaign))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPCampaign in the database
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteVPCampaign() throws Exception {
        // Initialize the database
        vPCampaignRepository.saveAndFlush(vPCampaign);

        int databaseSizeBeforeDelete = vPCampaignRepository.findAll().size();

        // Delete the vPCampaign
        restVPCampaignMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPCampaign.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPCampaign> vPCampaignList = vPCampaignRepository.findAll();
        assertThat(vPCampaignList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
