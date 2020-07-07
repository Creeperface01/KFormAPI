package com.creeperface.nukkit.kformapi.form

import cn.nukkit.Player
import cn.nukkit.utils.MainLogger
import com.creeperface.nukkit.kformapi.form.element.*
import com.creeperface.nukkit.kformapi.form.response.CustomFormResponse
import com.creeperface.nukkit.kformapi.form.util.FormType
import com.creeperface.nukkit.kformapi.form.util.ImageData
import com.creeperface.nukkit.kformapi.form.util.ImageType
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.google.common.base.Preconditions
import java.util.*

class CustomForm(
        title: String,
        val icon: ImageData?,
        @JsonProperty("content") val elements: List<Element>,
        listeners: List<(Player, CustomFormResponse) -> Unit>,
        closeListeners: List<(Player) -> Unit>,
        errorListeners: List<(Player) -> Unit>
) : Form<CustomFormResponse>(FormType.CUSTOM, title, listeners, closeListeners, errorListeners) {
    /**
     * @param index element index
     * @return element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    fun getElement(index: Int): Element {
        return elements[index]
    }

    override fun handleResponse(p: Player, node: JsonNode) {
        if (!node.isArray) {
            error(p)
            MainLogger.getLogger().warning("Received invalid response for CustomForm $node")
            return
        }

        submit(p, CustomFormResponse(this, node))
    }

    class CustomFormBuilder : FormBuilder<CustomForm, CustomFormBuilder, CustomFormResponse>() {

        private val elements: MutableList<Element> = ArrayList()
        private var icon: ImageData? = null

        /**
         * Add a dropdown element
         *
         * @param text          dropdown title
         * @param defaultOption default dropdown option index
         * @param options       dropdown options
         */
        fun dropdown(
                text: String,
                options: List<String>,
                defaultOption: Int = 0,
        ) {
            Preconditions.checkPositionIndex(defaultOption, options.size, "Default option index out of bounds")

            element(ElementDropdown(text, options, defaultOption))
        }

        /**
         * Add an input element
         *
         * @param text        input title
         * @param placeholder placeholder text
         * @param defaultText default input text
         */
        fun input(
                text: String,
                placeholder: String? = null,
                defaultText: String = ""
        ) {
            element(ElementInput(text, placeholder, defaultText))
        }

        /**
         * Add a label element
         *
         * @param text label text
         */
        fun label(text: String) {
            element(ElementLabel(text))
        }

        /**
         * Add a slider element
         *
         * @param elementText  slider title
         * @param minimum      minimal slider value
         * @param maximum      maximal slider value
         * @param stepCount    amount of steps in a given range
         * @param defaultValue default slider value
         */
        fun slider(
                elementText: String,
                minimum: Float = 0f,
                maximum: Float = 100f,
                stepCount: Int = 1,
                defaultValue: Float = minimum
        ) {
            element(ElementSlider(elementText, minimum, maximum, stepCount, defaultValue))
        }

        /**
         * Add a step slider element
         *
         * @param elementText      step slider title
         * @param defaultStepIndex step slider default option index
         * @param stepOptions      list of all available steps
         */
        fun stepSlider(
                elementText: String,
                stepOptions: List<String>,
                defaultStepIndex: Int = 0,
        ) {
            element(ElementStepSlider(elementText, stepOptions, defaultStepIndex))
        }

        /**
         * Add a toggle element
         *
         * @param elementText  toggle title
         * @param defaultValue default toggle value
         */
        fun toggle(elementText: String, defaultValue: Boolean = false) {
            element(ElementToggle(elementText, defaultValue))
        }

        /**
         * Add an element
         *
         * @param element an element to be added
         */
        fun element(element: Element) {
            elements.add(element)
        }

        /**
         * Add one or more elements
         *
         * @param element  an element to be added
         * @param elements list of elements to be added
         */
        fun elements(element: Element, vararg elements: Element) {
            this.elements.add(element)
            this.elements.addAll(elements)
        }

        /**
         * Add list of elements
         *
         * @param elements list of elements to be added
         */
        fun elements(elements: Collection<Element>) {
            this.elements.addAll(elements)
        }

        /**
         * Set an icon of the form
         * The icon is visible only in case of server settings form
         *
         * @param imageType icon image type
         * @param imageData icon image data
         */
        fun icon(imageType: ImageType, imageData: String) {
            icon = ImageData(imageType, imageData)
        }

        /**
         * Builds a new CustomForm instance using builder values
         *
         * @return CustomForm instance
         */
        override fun build(): CustomForm {
            return CustomForm(title, icon, Collections.unmodifiableList(elements), listeners, closeListeners, errorListeners)
        }
    }
}