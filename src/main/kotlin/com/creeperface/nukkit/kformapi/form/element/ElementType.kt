package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

enum class ElementType {
    @JsonProperty("button")
    BUTTON,
    @JsonProperty("dropdown")
    DROPDOWN,
    @JsonProperty("input")
    INPUT,
    @JsonProperty("label")
    LABEL,
    @JsonProperty("slider")
    SLIDER,
    @JsonProperty("step_slider")
    STEP_SLIDER,
    @JsonProperty("toggle")
    TOGGLE
}