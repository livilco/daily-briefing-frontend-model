# Daily Briefing data model


## Model
The Work-API data model is located at co.livil.workapi.model

These are direct transformations of the JSONAPI data coming in from the the API, hence the annotations.

## Serializers
Moshi generates adapters for the models which we have wrapped with helper classes at co.livil.workapi.serializers

For examples of JSON payloads and model instances, see the serializer tests.

## Gradle Dependencies
```
implementation 'com.squareup.moshi:moshi-kotlin:1.9.3'
implementation 'com.squareup.moshi:moshi-kotlin-codegen:1.9.3'
implementation 'moe.banana:moshi-jsonapi:3.5.0'
```

## Changes
At this early stage, everything is subject to change. Suggestions & requests welcome.
