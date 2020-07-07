package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

class ElementSlider(
        elementText: String,
        val min: Float = 0f,
        val max: Float = 100f,
        val step: Int = 1,
        @JsonProperty("default") val defaultValue: Float = min
) : Element(ElementType.SLIDER, elementText) {

    init {
        require(min < max) { "Maximal value can't be smaller or equal to the minimal value" }
    }
}