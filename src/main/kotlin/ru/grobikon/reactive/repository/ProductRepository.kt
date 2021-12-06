package ru.grobikon.reactive.repository

import org.springframework.data.domain.Range
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.grobikon.reactive.dto.ProductDto
import ru.grobikon.reactive.entity.Product

@Repository
interface ProductRepository: ReactiveMongoRepository<Product, String> {
    fun findByPriceBetween(priceRange: Range<Double>): Flux<ProductDto>
}