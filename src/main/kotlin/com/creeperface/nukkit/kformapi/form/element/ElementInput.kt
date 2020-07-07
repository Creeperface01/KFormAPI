package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

class ElementInput(
        elementText: String,
        val placeholder: String? = null,
        @JsonProperty("default") val defaultText: String = ""
) : Element(ElementType.INPUT, elementText)