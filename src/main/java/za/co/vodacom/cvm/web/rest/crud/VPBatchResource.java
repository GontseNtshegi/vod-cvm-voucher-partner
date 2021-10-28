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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.dto.VPBatchDTO;
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
     * @param vPBatchDTO the vPBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPBatchDTO, or with status {@code 400 (Bad Request)} if the vPBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-batches")
    public ResponseEntity<VPBatchDTO> createVPBatch(@Valid @RequestBody VPBatchDTO vPBatchDTO) throws URISyntaxException {
        log.debug("REST request to save VPBatch : {}", vPBatchDTO);
        if (vPBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new vPBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPBatchDTO result = vPBatchService.save(vPBatchDTO);
        return ResponseEntity
            .created(new URI("/api/vp-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-batches/:id} : Updates an existing vPBatch.
     *
     * @param id the id of the vPBatchDTO to save.
     * @param vPBatchDTO the vPBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPBatchDTO,
     * or with status {@code 400 (Bad Request)} if the vPBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-batches/{id}")
    public ResponseEntity<VPBatchDTO> updateVPBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPBatchDTO vPBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VPBatch : {}, {}", id, vPBatchDTO);
        if (vPBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VPBatchDTO result = vPBatchService.save(vPBatchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPBatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-batches/:id} : Partial updates given fields of an existing vPBatch, field will ignore if it is null
     *
     * @param id the id of the vPBatchDTO to save.
     * @param vPBatchDTO the vPBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPBatchDTO,
     * or with status {@code 400 (Bad Request)} if the vPBatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vPBatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-batches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VPBatchDTO> partialUpdateVPBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPBatchDTO vPBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPBatch partially : {}, {}", id, vPBatchDTO);
        if (vPBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VPBatchDTO> result = vPBatchService.partialUpdate(vPBatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPBatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-batches} : get all the vPBatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPBatches in body.
     */
    @GetMapping("/vp-batches")
    public List<VPBatchDTO> getAllVPBatches() {
        log.debug("REST request to get all VPBatches");
        return vPBatchService.findAll();
    }

    /**
     * {@code GET  /vp-batches/:id} : get the "id" vPBatch.
     *
     * @param id the id of the vPBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-batches/{id}")
    public ResponseEntity<VPBatchDTO> getVPBatch(@PathVariable Long id) {
        log.debug("REST request to get VPBatch : {}", id);
        Optional<VPBatchDTO> vPBatchDTO = vPBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPBatchDTO);
    }

    /**
     * {@code DELETE  /vp-batches/:id} : delete the "id" vPBatch.
     *
     * @param id the id of the vPBatchDTO to delete.
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
