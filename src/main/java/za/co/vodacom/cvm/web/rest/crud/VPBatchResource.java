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
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPBatch}.
 */
@RestController
@RequestMapping("/v2/api")
//@Profile("crud")
public class VPBatchResource {

    private final Logger log = LoggerFactory.getLogger(VPBatchResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPBatchService vPBatchService;

    private final VPBatchRepository vPBatchRepository;

    public VPBatchResource(VPBatchService vPBatchService, VPBatchRepository vPBatchRepository) {
        this.vPBatchService = vPBatchService;
        this.vPBatchRepository = vPBatchRepository;
    }

    /**
     * {@code POST  /vp-batches} : Create a new vPBatch.
     *
     * @param vPBatch the vPBatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPBatch, or with status {@code 400 (Bad Request)} if the vPBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-batches")
    public ResponseEntity<VPBatch> createVPBatch(@Valid @RequestBody VPBatch vPBatch) throws URISyntaxException {
        log.debug("REST request to save VPBatch : {}", vPBatch);
        if (vPBatch.getId() != null) {
            throw new BadRequestAlertException("A new vPBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPBatch result = vPBatchService.save(vPBatch);
        return ResponseEntity
            .created(new URI("/api/vp-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-batches/:id} : Updates an existing vPBatch.
     *
     * @param id the id of the vPBatch to save.
     * @param vPBatch the vPBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPBatch,
     * or with status {@code 400 (Bad Request)} if the vPBatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-batches/{id}")
    public ResponseEntity<VPBatch> updateVPBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPBatch vPBatch
    ) throws URISyntaxException {
        log.debug("REST request to update VPBatch : {}, {}", id, vPBatch);
        if (vPBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPBatch result = vPBatchService.save(vPBatch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPBatch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-batches/:id} : Partial updates given fields of an existing vPBatch, field will ignore if it is null
     *
     * @param id the id of the vPBatch to save.
     * @param vPBatch the vPBatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPBatch,
     * or with status {@code 400 (Bad Request)} if the vPBatch is not valid,
     * or with status {@code 404 (Not Found)} if the vPBatch is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPBatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-batches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPBatch> partialUpdateVPBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPBatch vPBatch
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPBatch partially : {}, {}", id, vPBatch);
        if (vPBatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPBatch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPBatch> result = vPBatchService.partialUpdate(vPBatch);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPBatch.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-batches} : get all the vPBatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPBatches in body.
     */
    @GetMapping("/vp-batches")
    public List<VPBatch> getAllVPBatches() {
        log.debug("REST request to get all VPBatches");
        return vPBatchService.findAll();
    }

    /**
     * {@code GET  /vp-batches/:id} : get the "id" vPBatch.
     *
     * @param id the id of the vPBatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPBatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-batches/{id}")
    public ResponseEntity<VPBatch> getVPBatch(@PathVariable Long id) {
        log.debug("REST request to get VPBatch : {}", id);
        Optional<VPBatch> vPBatch = vPBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPBatch);
    }

    /**
     * {@code DELETE  /vp-batches/:id} : delete the "id" vPBatch.
     *
     * @param id the id of the vPBatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-batches/{id}")
    public ResponseEntity<Void> deleteVPBatch(@PathVariable Long id) {
        log.debug("REST request to delete VPBatch : {}", id);
        vPBatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
