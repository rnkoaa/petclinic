package com.petclinic.common.dates

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAccessor


class InstantDateDeserializer() : JsonDeserializer<Instant>() {
    private val FMT = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
            .toFormatter()
            .withZone(ZoneId.of("Europe/Paris"))

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Instant {
        return FMT.parse(p?.text) { temporal: TemporalAccessor? -> Instant.from(temporal) }
    }
}
