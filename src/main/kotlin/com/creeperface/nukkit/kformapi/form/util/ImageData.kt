package com.creeperface.nukkit.kformapi.form.util

import com.creeperface.nukkit.kformapi.form.util.ImageData.ImageDataSerializer
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = ImageDataSerializer::class)
class ImageData(
        @JsonProperty("type") val imageType: ImageType?,
        @JsonProperty("data") val imageData: String?
) {

    internal class ImageDataSerializer : JsonSerializer<ImageData>() {

        override fun serialize(imageData: ImageData, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
            if (imageData.imageData.isNullOrEmpty() || imageData.imageType == null) {
                jsonGenerator.writeNull()
                return
            }
            jsonGenerator.writeStartObject()
            jsonGenerator.writeObjectField("type", imageData.imageType)
            jsonGenerator.writeStringField("data", imageData.imageData)
            jsonGenerator.writeEndObject()
        }
    }
}