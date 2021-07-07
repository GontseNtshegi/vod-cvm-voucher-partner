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
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPCampaignVouchers}.
 */
@RestController
@RequestMapping("/api")
public class VPCampaignVouchersResource {

    private final Logger log = LoggerFactory.getLogger(VPCampaignVouchersResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpCampaignVouchers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPCampaignVouchersService vPCampaignVouchersService;

    private final VPCampaignVouchersRepository vPCampaignVouchersRepository;

    public VPCampaignVouchersResource(
        VPCampaignVouchersService vPCampaignVouchersService,
        VPCampaignVouchersRepository vPCampaignVouchersRepository
    ) {
        this.vPCampaignVouchersService = vPCampaignVouchersService;
        this.vPCampaignVouchersRepository = vPCampaignVouchersRepository;
    }

    /**
     * {@code POST  /vp-campaign-vouchers} : Create a new vPCampaignVouchers.
     *
     * @param vPCampaignVouchers the vPCampaignVouchers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPCampaignVouchers, or with status {@code 400 (Bad Request)} if the vPCampaignVouchers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-campaign-vouchers")
    public ResponseEntity<VPCampaignVouchers> createVPCampaignVouchers(@Valid @RequestBody VPCampaignVouchers vPCampaignVouchers)
        throws URISyntaxException {
        log.debug("REST request to save VPCampaignVouchers : {}", vPCampaignVouchers);
        if (vPCampaignVouchers.getId() != null) {
            throw new BadRequestAlertException("A new vPCampaignVouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPCampaignVouchers result = vPCampaignVouchersService.save(vPCampaignVouchers);
        return ResponseEntity
            .created(new URI("/api/vp-campaign-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-campaign-vouchers/:id} : Updates an existing vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchers to save.
     * @param vPCampaignVouchers the vPCampaignVouchers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignVouchers,
     * or with status {@code 400 (Bad Request)} if the vPCampaignVouchers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignVouchers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<VPCampaignVouchers> updateVPCampaignVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPCampaignVouchers vPCampaignVouchers
    ) throws URISyntaxException {
        log.debug("REST request to update VPCampaignVouchers : {}, {}", id, vPCampaignVouchers);
        if (vPCampaignVouchers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPCampaignVouchers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VPCampaignVouchers result = vPCampaignVouchersService.save(vPCampaignVouchers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignVouchers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-campaign-vouchers/:id} : Partial updates given fields of an existing vPCampaignVouchers, field will ignore if it is null
     *
     * @param id the id of the vPCampaignVouchers to save.
     * @param vPCampaignVouchers the vPCampaignVouchers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignVouchers,
     * or with status {@code 400 (Bad Request)} if the vPCampaignVouchers is not valid,
     * or with status {@code 404 (Not Found)} if the vPCampaignVouchers is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignVouchers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-campaign-vouchers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPCampaignVouchers> partialUpdateVPCampaignVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPCampaignVouchers vPCampaignVouchers
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPCampaignVouchers partially : {}, {}", id, vPCampaignVouchers);
        if (vPCampaignVouchers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vPCampaignVouchers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VPCampaignVouchers> result = vPCampaignVouchersService.partialUpdate(vPCampaignVouchers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignVouchers.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-campaign-vouchers} : get all the vPCampaignVouchers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPCampaignVouchers in body.
     */
    @GetMapping("/vp-campaign-vouchers")
    public List<VPCampaignVouchers> getAllVPCampaignVouchers() {
        log.debug("REST request to get all VPCampaignVouchers");
        return vPCampaignVouchersService.findAll();
    }

    /**
     * {@code GET  /vp-campaign-vouchers/:id} : get the "id" vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPCampaignVouchers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<VPCampaignVouchers> getVPCampaignVouchers(@PathVariable Long id) {
        log.debug("REST request to get VPCampaignVouchers : {}", id);
        Optional<VPCampaignVouchers> vPCampaignVouchers = vPCampaignVouchersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPCampaignVouchers);
    }

    /**
     * {@code DELETE  /vp-campaign-vouchers/:id} : delete the "id" vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<Void> deleteVPCampaignVouchers(@PathVariable Long id) {
        log.debug("REST request to delete VPCampaignVouchers : {}", id);
        vPCampaignVouchersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
