package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

class ElementToggle(
        elementText: String,
        @JsonProperty("default") val defaultValue: Boolean = false
) : Element(ElementType.TOGGLE, elementText)