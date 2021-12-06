package ru.grobikon.reactive

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit4.SpringRunner
import ru.grobikon.reactive.controller.ProductController

@RunWith(SpringRunner::class)
@WebFluxTest(ProductController::class)
class SpringReactiveMongodbCrudApplicationTests {

}
