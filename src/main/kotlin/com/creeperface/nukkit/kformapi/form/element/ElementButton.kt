package com.creeperface.nukkit.kformapi.form.element

import com.creeperface.nukkit.kformapi.form.util.ImageData
import com.fasterxml.jackson.annotation.JsonProperty

class ElementButton(
        @JsonProperty("text") val buttonText: String,
        @JsonProperty("image") val imageData: ImageData? = null
)