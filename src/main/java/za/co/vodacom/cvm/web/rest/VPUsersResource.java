package za.co.vodacom.cvm.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import za.co.vodacom.cvm.domain.VPUsers;
import za.co.vodacom.cvm.repository.VPUsersRepository;
import za.co.vodacom.cvm.service.VPUsersService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPUsers}.
 */
@RestController
@RequestMapping("/api")
@Profile("crud")
public class VPUsersResource {

    private final Logger log = LoggerFactory.getLogger(VPUsersResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPUsersService vPUsersService;

    private final VPUsersRepository vPUsersRepository;

    public VPUsersResource(VPUsersService vPUsersService, VPUsersRepository vPUsersRepository) {
        this.vPUsersService = vPUsersService;
        this.vPUsersRepository = vPUsersRepository;
    }

    /**
     * {@code POST  /vp-users} : Create a new vPUsers.
     *
     * @param vPUsers the vPUsers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPUsers, or with status {@code 400 (Bad Request)} if the vPUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-users")
    public ResponseEntity<VPUsers> createVPUsers(@Valid @RequestBody VPUsers vPUsers) throws URISyntaxException {
        log.debug("REST request to save VPUsers : {}", vPUsers);
        if (vPUsers.getId() != null) {
            throw new BadRequestAlertException("A new vPUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPUsers result = vPUsersService.save(vPUsers);
        return ResponseEntity
            .created(new URI("/api/vp-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-users/:id} : Updates an existing vPUsers.
     *
     * @param id the id of the vPUsers to save.
     * @param vPUsers the vPUsers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPUsers,
     * or with status {@code 400 (Bad Request)} if the vPUsers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPUsers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-users/{id}")
    public ResponseEntity<VPUsers> updateVPUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPUsers vPUsers
    ) throws URISyntaxException {
        log.debug("REST request to update VPUsers : {}, {}", id, vPUsers);
        if (vPUsers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPUsers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VPUsers result = vPUsersService.save(vPUsers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPUsers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-users/:id} : Partial updates given fields of an existing vPUsers, field will ignore if it is null
     *
     * @param id the id of the vPUsers to save.
     * @param vPUsers the vPUsers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPUsers,
     * or with status {@code 400 (Bad Request)} if the vPUsers is not valid,
     * or with status {@code 404 (Not Found)} if the vPUsers is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPUsers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPUsers> partialUpdateVPUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPUsers vPUsers
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPUsers partially : {}, {}", id, vPUsers);
        if (vPUsers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPUsers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VPUsers> result = vPUsersService.partialUpdate(vPUsers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPUsers.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-users} : get all the vPUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPUsers in body.
     */
    @GetMapping("/vp-users")
    public List<VPUsers> getAllVPUsers() {
        log.debug("REST request to get all VPUsers");
        return vPUsersService.findAll();
    }

    /**
     * {@code GET  /vp-users/:id} : get the "id" vPUsers.
     *
     * @param id the id of the vPUsers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPUsers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-users/{id}")
    public ResponseEntity<VPUsers> getVPUsers(@PathVariable Long id) {
        log.debug("REST request to get VPUsers : {}", id);
        Optional<VPUsers> vPUsers = vPUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPUsers);
    }

    /**
     * {@code DELETE  /vp-users/:id} : delete the "id" vPUsers.
     *
     * @param id the id of the vPUsers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-users/{id}")
    public ResponseEntity<Void> deleteVPUsers(@PathVariable Long id) {
        log.debug("REST request to delete VPUsers : {}", id);
        vPUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
