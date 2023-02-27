### Micronaut app that interacts with Google Firestore

Fetch all activities:

`http://localhost:8080/api/activities`

Add an activity:

`curl --location 'http://localhost:8080/api/create' \
--header 'Content-Type: application/json' \
--data '{
"name": "Morning Weight Training",
"distance": 0,
"moving_time": 4685,
"sport_type": "WeightTraining",
"start_date": "2023-02-22T07:01:06Z"
}'`


