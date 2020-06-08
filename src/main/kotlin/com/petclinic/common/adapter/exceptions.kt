package com.petclinic.common.adapter

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class InvalidProductIdException: IllegalArgumentException("Invalid Product id")

data class ExceptionResponse(val message: String)

data class ProductNotFoundException(val m: String) : RuntimeException(m)

data class ProductNameNotFoundException(val m: String) : RuntimeException(m)

data class ProductPriceNotFoundException(val m: String): RuntimeException(m)