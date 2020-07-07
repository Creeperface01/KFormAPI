package com.creeperface.nukkit.kformapi.form.util

import com.fasterxml.jackson.annotation.JsonProperty

enum class FormType() {
    @JsonProperty("form")
    SIMPLE,
    @JsonProperty("modal")
    MODAL,
    @JsonProperty("custom_form")
    CUSTOM
}