package com.creeperface.nukkit.kformapi.form.element

import com.fasterxml.jackson.annotation.JsonProperty

class ElementDropdown(
        elementText: String,
        options: List<String>? = null,
        @JsonProperty("default") val defaultOptionIndex: Int = 0
) : Element(ElementType.DROPDOWN, elementText) {

    val options = options?.toList() ?: emptyList()

    fun getDropdownOption(index: Int): String {
        return options[index]
    }

    data class Response(val index: Int, val option: String)
}