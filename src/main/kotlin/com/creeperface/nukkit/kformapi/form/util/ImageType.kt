package com.creeperface.nukkit.kformapi.form.util

import com.fasterxml.jackson.annotation.JsonProperty

enum class ImageType {
    /**
     * Remote URL
     */
    @JsonProperty("url")
    URL,

    /**
     * Path on the server file system
     */
    @JsonProperty("path")
    FILE
}