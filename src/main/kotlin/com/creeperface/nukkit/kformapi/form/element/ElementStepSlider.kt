package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

class ElementStepSlider(
        elementText: String,
        steps: List<String>? = null,
        @JsonProperty("default") val defaultStepIndex: Int = 0
) : Element(ElementType.STEP_SLIDER, elementText) {

    val steps = mutableListOf<String>()

    init {
        if (steps != null) {
            this.steps.addAll(steps)
        }
    }

    fun getStep(index: Int): String {
        return steps[index]
    }

    class Response(val index: Int, val option: String)
}