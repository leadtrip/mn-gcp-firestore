package com.example

import com.google.api.core.ApiFuture
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Singleton
class FirestoreService {

    private static Logger logger = LoggerFactory.getLogger(FirestoreService.class)

    static final String ACTIVITY_COLLECTION = 'activity'

    Firestore firestore

    FirestoreService(GoogleCredentials googleCredentials) {
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build()

        firestore = FirestoreClient.getFirestore(FirebaseApp.initializeApp(options))
    }

    Mono<String> create( Activity activity ) {
        logger.info("Creating activity")
        DocumentReference docRef = firestore.collection(ACTIVITY_COLLECTION).document()
        Map<String, Object> data = new HashMap<>()
        data.put("name", activity.name)
        data.put("sport_type", activity.sport_type)
        data.put("start_date", activity.start_date)
        data.put("moving_time", activity.moving_time)
        data.put("distance", activity.distance)

        WriteResult result = docRef.set(data).get()
        println("Created activity " + docRef.getId() + ". Timestamp: " + result.getUpdateTime())

        Mono.just("Created")
    }

    Mono<String> createPojo( Activity activity ) {
        CollectionReference collectionReference = firestore.collection(ACTIVITY_COLLECTION);
        WriteResult result =
                collectionReference.document().set(activity).get();

        System.out.println("Created user by POJO. Timestamp: " + result.getUpdateTime());
    }

    Mono<String> activities() {
        logger.info("Getting activities")
        ApiFuture<QuerySnapshot> query = firestore.collection(ACTIVITY_COLLECTION).get()
        QuerySnapshot querySnapshot = query.get()
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments()
        printActivities(documents)
        Mono.just('Working')
    }

    void printActivities(Iterable<QueryDocumentSnapshot> documents) {
        for (QueryDocumentSnapshot document : documents) {
            System.out.printf(
                    "Document: %s | %s | %s | %s | %s | %ss\n",
                    document.getId(),
                    document.getString("name"),
                    document.getString("sport_type"),
                    document.getDate("start_date"),
                    document.getLong("moving_time"),
                    document.getLong("distance"));
        }
    }
}
