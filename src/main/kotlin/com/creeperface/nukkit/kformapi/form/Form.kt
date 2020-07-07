package com.creeperface.nukkit.kformapi.form

import cn.nukkit.Player
import com.creeperface.nukkit.kformapi.form.util.FormType
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.JsonNode
import java.util.*

abstract class Form<R>(
        val type: FormType,
        val title: String,
        @JsonIgnore private val listeners: List<(Player, R) -> Unit>,
        @JsonIgnore private val closeListeners: List<(Player) -> Unit>,
        @JsonIgnore private val errorListeners: List<(Player) -> Unit>
) {

    internal abstract fun handleResponse(p: Player, node: JsonNode)

    fun close(p: Player) {
        for (closeListener in closeListeners) {
            closeListener(p)
        }
    }

    fun submit(p: Player, response: R?) {
        println(4)
        if (response == null) {
            println(5)
            close(p)
            return
        }

        println(6)
        for (listener in listeners) {
            listener(p, response)
        }
    }

    fun error(p: Player) {
        for (errorListener in errorListeners) {
            errorListener(p)
        }
    }

    abstract class FormBuilder<F : Form<R>, T : FormBuilder<F, T, R>, R> {

        protected var title = ""
        protected val listeners: MutableList<(Player, R) -> Unit> = LinkedList()
        protected val closeListeners: MutableList<(Player) -> Unit> = LinkedList()
        protected val errorListeners: MutableList<(Player) -> Unit> = LinkedList()

        /**
         * Set a title of the form
         *
         * @param title form title
         */
        fun title(title: String) {
            this.title = title
        }

        /**
         * Called when the form is successfully submitted
         *
         * @param listener callback function
         */
        fun onSubmit(listener: (Player, R) -> Unit) {
            listeners.add(listener)
        }

        /**
         * Called when the form is closed
         *
         * @param listener callback function
         */
        fun onClose(listener: (Player) -> Unit) {
            closeListeners.add(listener)
        }

        /**
         * Called when an error occurs during the response processing
         * That could be caused either by a plugin or wrong response (which shouldn't occur in case of vanilla client)
         *
         * @param listener callback function
         */
        fun onError(listener: (Player) -> Unit) {
            errorListeners.add(listener)
        }

        /**
         * @return a new Form instance of given generic type
         */
        internal abstract fun build(): F
    }
}