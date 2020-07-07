package com.creeperface.nukkit.kformapi.form

import cn.nukkit.Player
import cn.nukkit.utils.MainLogger
import com.creeperface.nukkit.kformapi.form.util.FormType
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

class ModalForm(
        title: String,
        val content: String,
        @field:JsonProperty("button1") private val trueValue: String,
        @field:JsonProperty("button2") private val falseValue: String,
        listeners: List<(Player, Boolean) -> Unit>,
        closeListeners: List<(Player) -> Unit>,
        errorListeners: List<(Player) -> Unit>
) : Form<Boolean>(FormType.MODAL, title, listeners, closeListeners, errorListeners) {

    override fun handleResponse(p: Player, node: JsonNode) {
        if (!node.isBoolean) {
            error(p)
            MainLogger.getLogger().warning("Received invalid response for ModalForm $node")
            return
        }

        submit(p, node.booleanValue())
    }

    class ModalFormBuilder : FormBuilder<ModalForm, ModalFormBuilder, Boolean>() {

        private var content = ""
        private var trueValue = "true"
        private var falseValue = "false"

        /**
         * Set the form text content
         *
         * @param content form text content
         */
        fun content(content: String) {
            this.content = content
        }

        /**
         * Set a displayed value for true boolean value
         *
         * @param value string value for [Boolean.TRUE]
         */
        fun trueValue(value: String) {
            trueValue = value
        }

        /**
         * Set a displayed value for false boolean value
         *
         * @param value string value for [Boolean.FALSE]
         */
        fun falseValue(value: String) {
            falseValue = value
        }

        /**
         * Builds a new ModalForm instance using builder values
         *
         * @return ModalForm instance
         */
        override fun build(): ModalForm {
            return ModalForm(title, content, trueValue, falseValue, listeners, closeListeners, errorListeners)
        }
    }
}