package com.creeperface.nukkit.kformapi.form

import cn.nukkit.Player
import cn.nukkit.utils.MainLogger
import com.creeperface.nukkit.kformapi.form.element.ElementButton
import com.creeperface.nukkit.kformapi.form.response.SimpleFormResponse
import com.creeperface.nukkit.kformapi.form.util.FormType
import com.creeperface.nukkit.kformapi.form.util.ImageData
import com.creeperface.nukkit.kformapi.form.util.ImageType
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.JsonNode
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.util.*

class SimpleForm(
        title: String,
        val content: String,
        val buttons: List<ElementButton>,
        @JsonIgnore private val buttonListeners: Int2ObjectMap<(Player) -> Unit>,
        listeners: List<(Player, SimpleFormResponse) -> Unit>,
        closeListeners: List<(Player) -> Unit>,
        errorListeners: List<(Player) -> Unit>
) : Form<SimpleFormResponse>(FormType.SIMPLE, title, listeners, closeListeners, errorListeners) {

    fun getButton(index: Int) = buttons.getOrNull(index)

    override fun handleResponse(p: Player, node: JsonNode) {
        if (!node.isInt) {
            error(p)
            MainLogger.getLogger().warning("Received invalid response for SimpleForm $node")
            return
        }

        val clicked = node.intValue()
        buttonListeners[clicked]?.invoke(p)
        submit(p, SimpleFormResponse(clicked, getButton(clicked)))
    }

    class SimpleFormBuilder : FormBuilder<SimpleForm, SimpleFormBuilder, SimpleFormResponse>() {

        private var content = ""
        private val buttons: MutableList<ElementButton> = ArrayList()
        private val buttonListeners: Int2ObjectMap<(Player) -> Unit> = Int2ObjectOpenHashMap()

        /**
         * Set the form text content
         *
         * @param content form text content
         */
        fun content(content: String) {
            this.content = content
        }

        fun button(text: String, onClick: ((Player) -> Unit)? = null) {
            buttons.add(ElementButton(text))

            onClick?.let {
                buttonListeners[buttons.size - 1] = it
            }
        }

        fun button(text: String, imageType: ImageType, imageData: String, onClick: ((Player) -> Unit)? = null) {
            button(text, ImageData(imageType, imageData), onClick)
        }

        /**
         * Add a button with image and on click callback
         *
         * @param text      button title
         */
        fun button(text: String, imageData: ImageData?, onClick: ((Player) -> Unit)? = null) {
            buttons.add(ElementButton(text, imageData))

            onClick?.let {
                buttonListeners[buttons.size - 1] = it
            }
        }

        /**
         * Add one or more buttons
         *
         * @param button  button element
         * @param buttons list of button elements
         */
        fun buttons(button: ElementButton, vararg buttons: ElementButton) {
            this.buttons.add(button)
            this.buttons.addAll(buttons)
        }

        /**
         * Add list of buttons
         *
         * @param buttons list of button elements
         */
        fun buttons(buttons: Collection<ElementButton>) {
            this.buttons.addAll(buttons)
        }

        /**
         * Builds a new SimpleForm instance using builder values
         *
         * @return SimpleForm instance
         */
        override fun build(): SimpleForm {
            return SimpleForm(title, content, buttons, buttonListeners, listeners, closeListeners, errorListeners)
        }
    }
}