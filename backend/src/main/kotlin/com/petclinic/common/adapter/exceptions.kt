package com.petclinic.common.adapter

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class InvalidProductIdException: IllegalArgumentException("Invalid Product id")

data class ExceptionResponse(val message: String)
