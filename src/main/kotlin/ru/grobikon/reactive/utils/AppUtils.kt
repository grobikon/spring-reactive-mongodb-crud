package ru.grobikon.reactive.utils

import org.springframework.beans.BeanUtils
import ru.grobikon.reactive.dto.ProductDto
import ru.grobikon.reactive.entity.Product

fun entityToDto(product: Product): ProductDto {
    val productDto = ProductDto()
    BeanUtils.copyProperties(product, productDto)
    return productDto
}

fun dtoToEntity(productDto: ProductDto): Product {
    val product = Product()
    BeanUtils.copyProperties(productDto, product)
    return product
}