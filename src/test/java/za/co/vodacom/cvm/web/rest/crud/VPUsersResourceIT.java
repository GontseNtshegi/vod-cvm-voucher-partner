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
import za.co.vodacom.cvm.domain.VPUsers;
import za.co.vodacom.cvm.repository.VPUsersRepository;
import za.co.vodacom.cvm.web.rest.TestUtil;
import za.co.vodacom.cvm.web.rest.crud.VPUsersResource;

/**
 * Integration tests for the {@link VPUsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VPUsersResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACTIVE_YN = "A";
    private static final String UPDATED_ACTIVE_YN = "B";

    private static final String ENTITY_API_URL = "/v2/api/vp-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VPUsersRepository vPUsersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVPUsersMockMvc;

    private VPUsers vPUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPUsers createEntity(EntityManager em) {
        VPUsers vPUsers = new VPUsers()
            .userId(DEFAULT_USER_ID)
            .createDate(DEFAULT_CREATE_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .activeYN(DEFAULT_ACTIVE_YN);
        return vPUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VPUsers createUpdatedEntity(EntityManager em) {
        VPUsers vPUsers = new VPUsers()
            .userId(UPDATED_USER_ID)
            .createDate(UPDATED_CREATE_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .activeYN(UPDATED_ACTIVE_YN);
        return vPUsers;
    }

    @BeforeEach
    public void initTest() {
        vPUsers = createEntity(em);
    }

    //@Test
    @Transactional
    void createVPUsers() throws Exception {
        int databaseSizeBeforeCreate = vPUsersRepository.findAll().size();
        // Create the VPUsers
        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isCreated());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeCreate + 1);
        VPUsers testVPUsers = vPUsersList.get(vPUsersList.size() - 1);
        assertThat(testVPUsers.getId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVPUsers.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPUsers.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testVPUsers.getActiveYN()).isEqualTo(DEFAULT_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void createVPUsersWithExistingId() throws Exception {
        // Create the VPUsers with an existing ID
        vPUsers.setId(1L);

        int databaseSizeBeforeCreate = vPUsersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isBadRequest());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPUsersRepository.findAll().size();
        // set the field null
        vPUsers.setId(null);

        // Create the VPUsers, which fails.

        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isBadRequest());

        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPUsersRepository.findAll().size();
        // set the field null
        vPUsers.setCreateDate(null);

        // Create the VPUsers, which fails.

        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isBadRequest());

        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPUsersRepository.findAll().size();
        // set the field null
        vPUsers.setModifiedDate(null);

        // Create the VPUsers, which fails.

        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isBadRequest());

        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkActiveYNIsRequired() throws Exception {
        int databaseSizeBeforeTest = vPUsersRepository.findAll().size();
        // set the field null
        vPUsers.setActiveYN(null);

        // Create the VPUsers, which fails.

        restVPUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isBadRequest());

        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllVPUsers() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        // Get all the vPUsersList
        restVPUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vPUsers.getId())))
            //.andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].activeYN").value(hasItem(DEFAULT_ACTIVE_YN)));
    }

    //@Test
    @Transactional
    void getVPUsers() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        // Get the vPUsers
        restVPUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, vPUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vPUsers.getId()))
            // .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.activeYN").value(DEFAULT_ACTIVE_YN));
    }

    //@Test
    @Transactional
    void getNonExistingVPUsers() throws Exception {
        // Get the vPUsers
        restVPUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putNewVPUsers() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();

        // Update the vPUsers
        VPUsers updatedVPUsers = vPUsersRepository.findById(vPUsers.getId()).get();
        // Disconnect from session so that the updates on updatedVPUsers are not directly saved in db
        em.detach(updatedVPUsers);
        updatedVPUsers.userId(UPDATED_USER_ID).createDate(UPDATED_CREATE_DATE).modifiedDate(UPDATED_MODIFIED_DATE).activeYN(UPDATED_ACTIVE_YN);

        restVPUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVPUsers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVPUsers))
            )
            .andExpect(status().isOk());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
        VPUsers testVPUsers = vPUsersList.get(vPUsersList.size() - 1);
        assertThat(testVPUsers.getId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testVPUsers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPUsers.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testVPUsers.getActiveYN()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void putNonExistingVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vPUsers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPUsers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vPUsers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateVPUsersWithPatch() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();

        // Update the vPUsers using partial update
        VPUsers partialUpdatedVPUsers = new VPUsers();
        partialUpdatedVPUsers.setId(vPUsers.getId());

        restVPUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPUsers))
            )
            .andExpect(status().isOk());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
        VPUsers testVPUsers = vPUsersList.get(vPUsersList.size() - 1);
        assertThat(testVPUsers.getId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testVPUsers.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testVPUsers.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testVPUsers.getActiveYN()).isEqualTo(DEFAULT_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void fullUpdateVPUsersWithPatch() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();

        // Update the vPUsers using partial update
        VPUsers partialUpdatedVPUsers = new VPUsers();
        partialUpdatedVPUsers.setId(vPUsers.getId());

        partialUpdatedVPUsers
            .userId(UPDATED_USER_ID)
            .createDate(UPDATED_CREATE_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .activeYN(UPDATED_ACTIVE_YN);

        restVPUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVPUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVPUsers))
            )
            .andExpect(status().isOk());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
        VPUsers testVPUsers = vPUsersList.get(vPUsersList.size() - 1);
        assertThat(testVPUsers.getId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testVPUsers.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testVPUsers.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testVPUsers.getActiveYN()).isEqualTo(UPDATED_ACTIVE_YN);
    }

    //@Test
    @Transactional
    void patchNonExistingVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vPUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPUsers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vPUsers))
            )
            .andExpect(status().isBadRequest());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamVPUsers() throws Exception {
        int databaseSizeBeforeUpdate = vPUsersRepository.findAll().size();
        vPUsers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVPUsersMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vPUsers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VPUsers in the database
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteVPUsers() throws Exception {
        // Initialize the database
        vPUsersRepository.saveAndFlush(vPUsers);

        int databaseSizeBeforeDelete = vPUsersRepository.findAll().size();

        // Delete the vPUsers
        restVPUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, vPUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VPUsers> vPUsersList = vPUsersRepository.findAll();
        assertThat(vPUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
