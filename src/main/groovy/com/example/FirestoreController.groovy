package com.example

import io.micronaut.core.annotation.NonNull
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Controller('/api')
class FirestoreController {

    private static Logger logger = LoggerFactory.getLogger(FirestoreController.class)

    private final FirestoreService firestoreService

    FirestoreController(FirestoreService fs) {
        this.firestoreService = fs
    }

    @Get('/activities')
    Mono<String> activities() {
        firestoreService.activities()
    }

    @Post('/create')
    Mono<String> create(@Body @NonNull Activity activity) {
        firestoreService.create(activity)
    }

    @Post('/createpojo')
    Mono<String> createPojo(@Body @NonNull Activity activity) {
        firestoreService.createPojo(activity)
    }
}
