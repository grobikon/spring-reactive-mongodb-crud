package ru.grobikon.reactive.service

import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.grobikon.reactive.dto.ProductDto
import ru.grobikon.reactive.repository.ProductRepository
import ru.grobikon.reactive.utils.dtoToEntity
import ru.grobikon.reactive.utils.entityToDto

@Service
class ProductService(
    val productRepository: ProductRepository
) {

    /**
     * Получаем все продукты из БД
     */
    fun getProducts(): Flux<ProductDto> {
        return productRepository.findAll().map(::entityToDto)
    }

    /**
     * Получаем продукт по его Id
     * @param id - продукта, который будем искать в БД и возвращать
     */
    fun getProduct(id: String): Mono<ProductDto> {
        return productRepository.findById(id).map(::entityToDto)
    }

    /**
     * Возвращаем товар в зависимости от ценового диапазона:
     *  Range.closed() - Создает новый диапазон с включенными границами для обоих значений.
     *                   Параметры:
     *                          from - не должно быть нулевым.
     *                          to - не может быть нулевым.
     * @param min - минимальная цена
     * @param max - максимальная цена
     */
    fun getProductInRange(min: Double, max: Double): Flux<ProductDto> {
        return productRepository.findByPriceBetween(Range.closed(min, max))
    }

    /**
     * Сохраняем значение продукта в БД
     * @param productDtoMono - продукт, который будем сохранять в БД MongoDB
     */
    fun saveProduct(productDtoMono: Mono<ProductDto>): Mono<ProductDto> {
        println("server method called ...")
        return productDtoMono
            .map(::dtoToEntity)
            .flatMap(productRepository::insert)
            .map(::entityToDto)
    }

    /**
     * Сохраняем значение продукта в БД
     * @param productDtoMono - продукт, который будем обновлять в БД MongoDB
     * @param id - id продукта значения которого будем обновлять
     */
    fun updateProduct(productDtoMono: Mono<ProductDto>, id: String): Mono<ProductDto> {
        return productRepository.findById(id)                               //поиск продукта по ID
            .flatMap { productFind -> productDtoMono.map(::dtoToEntity) }   //нашли продукт, тогда productDtoMono преобразуем в Entity класс
            .doOnNext{ product -> product.id = id}                          //в следующем шаге преобразованному product устанавливаем id
            .flatMap(productRepository::save)                               //сохраняем изменения в БД
            .map(::entityToDto)
    }

    /**
     * Удаление продукта
     */
    fun deleteProduct(id: String): Mono<Void> {
        return productRepository.deleteById(id)
    }
}