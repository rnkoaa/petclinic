package com.petclinic.common.adapter

import java.lang.RuntimeException

class DuplicateKeyException(override val message: String): RuntimeException(message) {
}