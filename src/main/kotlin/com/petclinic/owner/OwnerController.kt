package com.petclinic.owner

import com.petclinic.common.adapter.InvalidProductIdException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/petclinic/v1/owner")
class OwnerController {
    /**
     * This is a private endpoint by which we can seed data for testing purposes.
     *
     * @param request a valid {@link ProductRequest} object containing a valid productId and {@link ProductPrice}
     *                       that can be used to persist a product price in our system. In order to call this
     *                       endpoint, the principal should have a role of admin.
     * @return http 201 response code if the request was successful.
     * Additionally, the request body is returned to the client
     */
//    @Operation(hidden = true, summary = "Ignore this method from being publicly documented")
    @PostMapping(value = ["", "/"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ADMIN')")
    fun create(/*@Valid*/ @RequestBody request: OwnerRequest): Mono<OwnerResponse> {
//        logger.info("create product {}", request.toString())
        val productId = request.id ?: 0L
        if (productId <= 0L) {
            return Mono.error(InvalidProductIdException())
        }
//
//        val product = Product(
//                id = request.id,
//                name = request.name ?: "",
//                currentPrice = request.currentPrice)
//
//        return productService.exists(productId)
//                .flatMap { res ->
//                    if (!res) {
//                        val toBeSavedProduct = Product(productId, request.name, request.currentPrice)
//                        productService.save(toBeSavedProduct)
//                    } else {
//                        Mono.error<Throwable>(DuplicateKeyException("product exists"))
//                    }
//                }
//                .flatMap { Mono.just(ProductResponse(product.id, product.name, product.currentPrice)) }

    }
}