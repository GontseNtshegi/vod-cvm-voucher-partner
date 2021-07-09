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
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPCampaign}.
 */
@RestController
@RequestMapping("/api")
@Profile("crud")
public class VPCampaignResource {

    private final Logger log = LoggerFactory.getLogger(VPCampaignResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpCampaign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPCampaignService vPCampaignService;

    private final VPCampaignRepository vPCampaignRepository;

    public VPCampaignResource(VPCampaignService vPCampaignService, VPCampaignRepository vPCampaignRepository) {
        this.vPCampaignService = vPCampaignService;
        this.vPCampaignRepository = vPCampaignRepository;
    }

    /**
     * {@code POST  /vp-campaigns} : Create a new vPCampaign.
     *
     * @param vPCampaign the vPCampaign to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPCampaign, or with status {@code 400 (Bad Request)} if the vPCampaign has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-campaigns")
    public ResponseEntity<VPCampaign> createVPCampaign(@Valid @RequestBody VPCampaign vPCampaign) throws URISyntaxException {
        log.debug("REST request to save VPCampaign : {}", vPCampaign);
        if (vPCampaign.getId() != null) {
            throw new BadRequestAlertException("A new vPCampaign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPCampaign result = vPCampaignService.save(vPCampaign);
        return ResponseEntity
            .created(new URI("/api/vp-campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-campaigns/:id} : Updates an existing vPCampaign.
     *
     * @param id the id of the vPCampaign to save.
     * @param vPCampaign the vPCampaign to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaign,
     * or with status {@code 400 (Bad Request)} if the vPCampaign is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaign couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-campaigns/{id}")
    public ResponseEntity<VPCampaign> updateVPCampaign(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPCampaign vPCampaign
    ) throws URISyntaxException {
        log.debug("REST request to update VPCampaign : {}, {}", id, vPCampaign);
        if (vPCampaign.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPCampaign.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VPCampaign result = vPCampaignService.save(vPCampaign);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaign.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-campaigns/:id} : Partial updates given fields of an existing vPCampaign, field will ignore if it is null
     *
     * @param id the id of the vPCampaign to save.
     * @param vPCampaign the vPCampaign to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaign,
     * or with status {@code 400 (Bad Request)} if the vPCampaign is not valid,
     * or with status {@code 404 (Not Found)} if the vPCampaign is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaign couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-campaigns/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPCampaign> partialUpdateVPCampaign(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPCampaign vPCampaign
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPCampaign partially : {}, {}", id, vPCampaign);
        if (vPCampaign.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPCampaign.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VPCampaign> result = vPCampaignService.partialUpdate(vPCampaign);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaign.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-campaigns} : get all the vPCampaigns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPCampaigns in body.
     */
    @GetMapping("/vp-campaigns")
    public List<VPCampaign> getAllVPCampaigns() {
        log.debug("REST request to get all VPCampaigns");
        return vPCampaignService.findAll();
    }

    /**
     * {@code GET  /vp-campaigns/:id} : get the "id" vPCampaign.
     *
     * @param id the id of the vPCampaign to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPCampaign, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-campaigns/{id}")
    public ResponseEntity<VPCampaign> getVPCampaign(@PathVariable Long id) {
        log.debug("REST request to get VPCampaign : {}", id);
        Optional<VPCampaign> vPCampaign = vPCampaignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPCampaign);
    }

    /**
     * {@code DELETE  /vp-campaigns/:id} : delete the "id" vPCampaign.
     *
     * @param id the id of the vPCampaign to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-campaigns/{id}")
    public ResponseEntity<Void> deleteVPCampaign(@PathVariable Long id) {
        log.debug("REST request to delete VPCampaign : {}", id);
        vPCampaignService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
