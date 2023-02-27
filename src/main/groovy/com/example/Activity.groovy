package com.example

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@CompileStatic
class Activity {
    String name
    Long distance
    Long moving_time
    String sport_type
    Date start_date
}
