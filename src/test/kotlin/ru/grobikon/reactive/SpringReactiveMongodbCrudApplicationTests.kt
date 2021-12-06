package ru.grobikon.reactive

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
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

}
