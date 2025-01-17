/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package za.co.vodacom.cvm.client.wigroup.api;

import io.swagger.annotations.*;
import javax.net.ssl.SSLHandshakeException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.vodacom.cvm.client.wigroup.model.*;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2022-03-07T11:18:06.608682700+02:00[Africa/Harare]"
)
@Validated
@Api(value = "Giftcards", description = "the Giftcards API")
public interface GiftcardsApi {
    /**
     * POST /giftcards/{id}/wicode : Customer voucher redemption
     * issue a wicode against giftcard for length of giftcard expiry
     *
     * @param id The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher (required)
     * @return Sucessful operation (status code 200)
     *         or No content (status code 204)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     *         or Method Not Allowed (status code 405)
     *         or Internal Server Error (status code 500)
     */
    @Retryable(value = SSLHandshakeException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @ApiOperation(
        value = "Customer voucher redemption",
        nickname = "redeemGiftcard",
        notes = "issue a wicode against giftcard for length of giftcard expiry",
        response = GiftCardsRedeemResponse.class,
        tags = { "giftcards" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Sucessful operation", response = GiftCardsRedeemResponse.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
        }
    )
    @PostMapping(value = "/giftcards/{id}/wicode", produces = "application/json")
    ResponseEntity<GiftCardsRedeemResponse> redeemGiftcard(
        @NotNull @ApiParam(
            value = "The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher",
            required = true
        ) @Valid @RequestParam(value = "id", required = true) Long id
    );

    /**
     * POST /giftcards : Customer voucher reservation
     * Reserve a voucher for a customer.
     *
     * @param issueWiCode The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher (required)
     * @param giftCardsRequest  (optional)
     * @return Sucessful operation (status code 200)
     *         or No content (status code 204)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     *         or Method Not Allowed (status code 405)
     *         or Internal Server Error (status code 500)
     */
    @Retryable(value = SSLHandshakeException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @ApiOperation(
        value = "Customer voucher reservation",
        nickname = "updateVoucherToReserved",
        notes = "Reserve a voucher for a customer.",
        response = GiftCardsResponse.class,
        tags = { "giftcards" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Sucessful operation", response = GiftCardsResponse.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
        }
    )
    @PostMapping(value = "/giftcards", produces = "application/json", consumes = "application/json")
    ResponseEntity<GiftCardsResponse> updateVoucherToReserved(
        @NotNull @ApiParam(
            value = "The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher",
            required = true,
            defaultValue = "true"
        ) @Valid @RequestParam(value = "issueWiCode", required = true, defaultValue = "true") Boolean issueWiCode,
        @ApiParam(value = "") @Valid @RequestBody(required = false) GiftCardsRequest giftCardsRequest
    );

    /**
     * GET /giftcards/{id} : Customer voucher balance view
     * This call can be used to retrieve (get) a wiGroup Gift Card
     *
     * @param id The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher (required)
     * @return Sucessful operation (status code 200)
     *         or No content (status code 204)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     *         or Method Not Allowed (status code 405)
     *         or Internal Server Error (status code 500)
     */
    @Retryable(value = SSLHandshakeException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @ApiOperation(
        value = "Customer voucher balance view",
        nickname = "viewGiftcard",
        notes = "This call can be used to retrieve (get) a wiGroup Gift Card",
        response = GiftCardsBalanceResponse.class,
        tags = { "giftcards" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Sucessful operation", response = GiftCardsBalanceResponse.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
        }
    )
    @GetMapping(value = "/giftcards/{id}", produces = "application/json")
    ResponseEntity<GiftCardsBalanceResponse> viewGiftcard(
        @NotNull @ApiParam(
            value = "The original wigroup voucher id or VP_VOUCHER.ID used to issued the voucher",
            required = true
        ) @Valid @RequestParam(value = "id", required = true) Long id
    );

    /**
     * DELETE /giftcards/{idd} : Check whether the given voucher based product has stock.
     * Check whether the given voucher based product has stock
     *
     * @param idd The original wigroup voucher id (required)
     * @return Successful Operation (status code 200)
     *         or No content (status code 204)
     *         or Bad Request (status code 400)
     *         or Not Found (status code 404)
     *         or Method Not Allowed (status code 405)
     *         or Internal Server Error (status code 500)
     */
    @Retryable(value = SSLHandshakeException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @ApiOperation(
        value = "This call can be used to expire (delete) a wiGroup Gift Card",
        nickname = "expireGiftCards",
        notes = "This call can be used to expire (delete) a wiGroup Gift Card",
        response = GiftCardsDelResponse.class,
        tags = { "giftcards" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful Operation", response = GiftCardsDelResponse.class),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
        }
    )
    @DeleteMapping(value = "/giftcards/{idd}", produces = "application/json")
    ResponseEntity<GiftCardsDelResponse> expireGiftCards(
        @ApiParam(value = "The original wigroup voucher id", required = true) @PathVariable("idd") Long idd
    );
}
