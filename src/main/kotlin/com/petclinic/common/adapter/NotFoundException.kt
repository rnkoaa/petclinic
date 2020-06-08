package com.petclinic.common.adapter

class NotFoundException(override val message: String) : Exception(message) {
}