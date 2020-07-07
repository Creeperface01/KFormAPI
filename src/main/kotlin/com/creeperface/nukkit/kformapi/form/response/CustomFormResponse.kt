package com.creeperface.nukkit.kformapi.form.response

import com.creeperface.nukkit.kformapi.form.CustomForm
import com.creeperface.nukkit.kformapi.form.element.ElementDropdown
import com.creeperface.nukkit.kformapi.form.element.ElementStepSlider
import com.fasterxml.jackson.databind.JsonNode

class CustomFormResponse(private val form: CustomForm, private val responses: JsonNode) {

    private operator fun get(index: Int): JsonNode {
        return responses[index]
    }

    /**
     * @param index element index
     * @return dropdown response object
     */
    fun getDropdown(index: Int): ElementDropdown.Response {
        val node = get(index)
        if (!node.isInt) {
            wrongValue(index, "dropdown")
        }
        return ElementDropdown.Response(index, (form.getElement(index) as ElementDropdown).getDropdownOption(node.asInt()))
    }

    /**
     * @param index element index
     * @return step slider response object
     */
    fun getStepSlider(index: Int): ElementStepSlider.Response {
        val node = get(index)
        if (!node.isInt) {
            wrongValue(index, "step slider")
        }
        return ElementStepSlider.Response(index, (form.getElement(index) as ElementStepSlider).getStep(node.asInt()))
    }

    /**
     * @param index element index
     * @return input response value
     */
    fun getInput(index: Int): String {
        val node = get(index)
        if (!node.isTextual) {
            wrongValue(index, "input")
        }
        return node.asText()
    }

    /**
     * @param index element index
     * @return slider response value
     */
    fun getSlider(index: Int): Float {
        val node = get(index)
        if (!node.isDouble) {
            wrongValue(index, "slider")
        }
        return node.asDouble().toFloat()
    }

    /**
     * @param index element index
     * @return toggle response value
     */
    fun getToggle(index: Int): Boolean {
        val node = get(index)
        if (!node.isBoolean) {
            wrongValue(index, "toggle")
        }
        return node.asBoolean()
    }

    companion object {
        private fun wrongValue(index: Int, expected: String) {
            throw IllegalStateException(String.format("Wrong element at index %d expected '%s'", index, expected))
        }
    }
}