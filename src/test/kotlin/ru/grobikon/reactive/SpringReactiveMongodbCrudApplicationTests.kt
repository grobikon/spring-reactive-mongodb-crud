package ru.grobikon.reactive

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ru.grobikon.reactive.controller.ProductController
import ru.grobikon.reactive.dto.ProductDto
import ru.grobikon.reactive.service.ProductService


@RunWith(SpringRunner::class)
@WebFluxTest(ProductController::class)
class SpringReactiveMongodbCrudApplicationTests{
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var service: ProductService

    @Test
    fun addProductText() {
        val productDtoMono = Mono.just(ProductDto("1", "mobile", 1, 10000.0))
        `when`(service.saveProduct(productDtoMono)).thenReturn(productDtoMono)

        webTestClient.post().uri("/products")
            .body(Mono.just(productDtoMono), ProductDto::class.java)
            .exchange()
            .expectStatus().isOk    //200
    }

    @Test
    fun getProductsTest() {
        //значения, которые ожидаем вернуть
        val productDtoFlux = Flux.just(ProductDto("1", "mobile", 1, 10000.0),
                                        ProductDto("2", "TV", 5, 50000.0))

        `when`(service.getProducts()).thenReturn(productDtoFlux)

        val responseBody = webTestClient.get().uri("/products")
            .exchange()
            .expectStatus().isOk    //200
            .returnResult(ProductDto::class.java)
            .responseBody

        //пошагово проверяем объекты чтобы они совпадали
        StepVerifier.create(responseBody)
            .expectSubscription()
            .expectNext(ProductDto("1", "mobile", 1, 10000.0))
            .expectNext(ProductDto("2", "TV", 5, 50000.0))
            .verifyComplete()
    }

    @Test
    fun getProductTest() {
        val productDtoMono = Mono.just(ProductDto("1", "mobile", 1, 10000.0))

        `when`(service.getProduct(anyString())).thenReturn(productDtoMono)

        val responseBody = webTestClient.get().uri("/products/1")
            .exchange()
            .expectStatus().isOk    //200
            .returnResult(ProductDto::class.java)
            .responseBody

        //пошагово проверяем объекты чтобы они совпадали
        StepVerifier.create(responseBody)
            .expectSubscription()
            .expectNextMatches{ p -> p.name == "mobile"}
            .verifyComplete()
    }

    @Test
    fun updateProductTest() {
        val productDtoMono = Mono.just(ProductDto("1", "mobile", 1, 10000.0))

        `when`(service.updateProduct(productDtoMono, "1")).thenReturn(productDtoMono)

        webTestClient.put().uri("/products/update/1")
            .body(Mono.just(productDtoMono), ProductDto::class.java)
            .exchange()
            .expectStatus().isOk    //200
    }

    @Test
    fun deleteProductTest() {
        given(service.deleteProduct(anyString())).willReturn(Mono.empty())

        webTestClient.delete().uri("/products/delete/1")
            .exchange()
            .expectStatus().isOk    //200
    }
}
