package ru.grobikon.reactive.controller

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.grobikon.reactive.dto.ProductDto
import ru.grobikon.reactive.service.ProductService

@RestController
@RequestMapping("/products")
class ProductController(
    val productService: ProductService
) {

    @GetMapping()
    fun getProducts(): Flux<ProductDto> {
        return productService.getProducts()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: String): Mono<ProductDto> {
        return productService.getProduct(id)
    }

    @GetMapping("/product-range")
    fun getProductBetweenRange(@RequestParam("min") min: Double,
                               @RequestParam("max") max: Double): Flux<ProductDto> {
        return productService.getProductInRange(min, max)
    }

    @PostMapping
    fun saveProduct(@RequestBody productDtoMono: Mono<ProductDto>): Mono<ProductDto> {
        println("controller method called ...")
        return productService.saveProduct(productDtoMono)
    }

    @PutMapping("/update/{id}")
    fun updateProduct(@RequestBody productDtoMono: Mono<ProductDto>, @PathVariable id: String): Mono<ProductDto> {
        return productService.updateProduct(productDtoMono, id)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: String): Mono<Void> {
        return productService.deleteProduct(id)
    }
}