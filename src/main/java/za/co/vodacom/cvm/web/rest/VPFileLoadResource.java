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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;
import za.co.vodacom.cvm.service.VPFileLoadService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPFileLoad}.
 */
@RestController
@RequestMapping("/api")
public class VPFileLoadResource {

    private final Logger log = LoggerFactory.getLogger(VPFileLoadResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpFileLoad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPFileLoadService vPFileLoadService;

    private final VPFileLoadRepository vPFileLoadRepository;

    public VPFileLoadResource(VPFileLoadService vPFileLoadService, VPFileLoadRepository vPFileLoadRepository) {
        this.vPFileLoadService = vPFileLoadService;
        this.vPFileLoadRepository = vPFileLoadRepository;
    }

    /**
     * {@code POST  /vp-file-loads} : Create a new vPFileLoad.
     *
     * @param vPFileLoad the vPFileLoad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPFileLoad, or with status {@code 400 (Bad Request)} if the vPFileLoad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-file-loads")
    public ResponseEntity<VPFileLoad> createVPFileLoad(@Valid @RequestBody VPFileLoad vPFileLoad) throws URISyntaxException {
        log.debug("REST request to save VPFileLoad : {}", vPFileLoad);
        if (vPFileLoad.getId() != null) {
            throw new BadRequestAlertException("A new vPFileLoad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPFileLoad result = vPFileLoadService.save(vPFileLoad);
        return ResponseEntity
            .created(new URI("/api/vp-file-loads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-file-loads/:id} : Updates an existing vPFileLoad.
     *
     * @param id the id of the vPFileLoad to save.
     * @param vPFileLoad the vPFileLoad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPFileLoad,
     * or with status {@code 400 (Bad Request)} if the vPFileLoad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPFileLoad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-file-loads/{id}")
    public ResponseEntity<VPFileLoad> updateVPFileLoad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPFileLoad vPFileLoad
    ) throws URISyntaxException {
        log.debug("REST request to update VPFileLoad : {}, {}", id, vPFileLoad);
        if (vPFileLoad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPFileLoad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPFileLoadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VPFileLoad result = vPFileLoadService.save(vPFileLoad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPFileLoad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-file-loads/:id} : Partial updates given fields of an existing vPFileLoad, field will ignore if it is null
     *
     * @param id the id of the vPFileLoad to save.
     * @param vPFileLoad the vPFileLoad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPFileLoad,
     * or with status {@code 400 (Bad Request)} if the vPFileLoad is not valid,
     * or with status {@code 404 (Not Found)} if the vPFileLoad is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPFileLoad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-file-loads/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPFileLoad> partialUpdateVPFileLoad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPFileLoad vPFileLoad
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPFileLoad partially : {}, {}", id, vPFileLoad);
        if (vPFileLoad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPFileLoad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPFileLoadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VPFileLoad> result = vPFileLoadService.partialUpdate(vPFileLoad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPFileLoad.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-file-loads} : get all the vPFileLoads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPFileLoads in body.
     */
    @GetMapping("/vp-file-loads")
    public List<VPFileLoad> getAllVPFileLoads() {
        log.debug("REST request to get all VPFileLoads");
        return vPFileLoadService.findAll();
    }

    /**
     * {@code GET  /vp-file-loads/:id} : get the "id" vPFileLoad.
     *
     * @param id the id of the vPFileLoad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPFileLoad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-file-loads/{id}")
    public ResponseEntity<VPFileLoad> getVPFileLoad(@PathVariable Long id) {
        log.debug("REST request to get VPFileLoad : {}", id);
        Optional<VPFileLoad> vPFileLoad = vPFileLoadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPFileLoad);
    }

    /**
     * {@code DELETE  /vp-file-loads/:id} : delete the "id" vPFileLoad.
     *
     * @param id the id of the vPFileLoad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-file-loads/{id}")
    public ResponseEntity<Void> deleteVPFileLoad(@PathVariable Long id) {
        log.debug("REST request to delete VPFileLoad : {}", id);
        vPFileLoadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
