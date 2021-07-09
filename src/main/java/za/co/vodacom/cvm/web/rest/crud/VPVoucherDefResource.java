package za.co.vodacom.cvm.web.rest.crud;

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
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPVoucherDef}.
 */
@RestController
@RequestMapping("/v2/api")
//@Profile("crud")
public class VPVoucherDefResource {

    private final Logger log = LoggerFactory.getLogger(VPVoucherDefResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpVoucherDef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPVoucherDefService vPVoucherDefService;

    private final VPVoucherDefRepository vPVoucherDefRepository;

    public VPVoucherDefResource(VPVoucherDefService vPVoucherDefService, VPVoucherDefRepository vPVoucherDefRepository) {
        this.vPVoucherDefService = vPVoucherDefService;
        this.vPVoucherDefRepository = vPVoucherDefRepository;
    }

    /**
     * {@code POST  /vp-voucher-defs} : Create a new vPVoucherDef.
     *
     * @param vPVoucherDef the vPVoucherDef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPVoucherDef, or with status {@code 400 (Bad Request)} if the vPVoucherDef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-voucher-defs")
    public ResponseEntity<VPVoucherDef> createVPVoucherDef(@Valid @RequestBody VPVoucherDef vPVoucherDef) throws URISyntaxException {
        log.debug("REST request to save VPVoucherDef : {}", vPVoucherDef);
        if (vPVoucherDef.getId() != null) {
            throw new BadRequestAlertException("A new vPVoucherDef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPVoucherDef result = vPVoucherDefService.save(vPVoucherDef);
        return ResponseEntity
            .created(new URI("/api/vp-voucher-defs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-voucher-defs/:id} : Updates an existing vPVoucherDef.
     *
     * @param id the id of the vPVoucherDef to save.
     * @param vPVoucherDef the vPVoucherDef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVoucherDef,
     * or with status {@code 400 (Bad Request)} if the vPVoucherDef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPVoucherDef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-voucher-defs/{id}")
    public ResponseEntity<VPVoucherDef> updateVPVoucherDef(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody VPVoucherDef vPVoucherDef
    ) throws URISyntaxException {
        log.debug("REST request to update VPVoucherDef : {}, {}", id, vPVoucherDef);
        if (vPVoucherDef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPVoucherDef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVoucherDefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPVoucherDef result = vPVoucherDefService.save(vPVoucherDef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVoucherDef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-voucher-defs/:id} : Partial updates given fields of an existing vPVoucherDef, field will ignore if it is null
     *
     * @param id the id of the vPVoucherDef to save.
     * @param vPVoucherDef the vPVoucherDef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVoucherDef,
     * or with status {@code 400 (Bad Request)} if the vPVoucherDef is not valid,
     * or with status {@code 404 (Not Found)} if the vPVoucherDef is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPVoucherDef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-voucher-defs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPVoucherDef> partialUpdateVPVoucherDef(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody VPVoucherDef vPVoucherDef
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPVoucherDef partially : {}, {}", id, vPVoucherDef);
        if (vPVoucherDef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPVoucherDef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVoucherDefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPVoucherDef> result = vPVoucherDefService.partialUpdate(vPVoucherDef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVoucherDef.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-voucher-defs} : get all the vPVoucherDefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPVoucherDefs in body.
     */
    @GetMapping("/vp-voucher-defs")
    public List<VPVoucherDef> getAllVPVoucherDefs() {
        log.debug("REST request to get all VPVoucherDefs");
        return vPVoucherDefService.findAll();
    }

    /**
     * {@code GET  /vp-voucher-defs/:id} : get the "id" vPVoucherDef.
     *
     * @param id the id of the vPVoucherDef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPVoucherDef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-voucher-defs/{id}")
    public ResponseEntity<VPVoucherDef> getVPVoucherDef(@PathVariable String id) {
        log.debug("REST request to get VPVoucherDef : {}", id);
        Optional<VPVoucherDef> vPVoucherDef = vPVoucherDefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPVoucherDef);
    }

    /**
     * {@code DELETE  /vp-voucher-defs/:id} : delete the "id" vPVoucherDef.
     *
     * @param id the id of the vPVoucherDef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-voucher-defs/{id}")
    public ResponseEntity<Void> deleteVPVoucherDef(@PathVariable String id) {
        log.debug("REST request to delete VPVoucherDef : {}", id);
        vPVoucherDefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
