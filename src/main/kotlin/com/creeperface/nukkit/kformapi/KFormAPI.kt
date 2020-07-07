package com.creeperface.nukkit.kformapi

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerQuitEvent
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.network.protocol.ModalFormRequestPacket
import cn.nukkit.network.protocol.ModalFormResponsePacket
import cn.nukkit.network.protocol.ProtocolInfo
import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.MainLogger
import com.creeperface.nukkit.kformapi.form.CustomForm
import com.creeperface.nukkit.kformapi.form.Form
import com.creeperface.nukkit.kformapi.form.ModalForm
import com.creeperface.nukkit.kformapi.form.SimpleForm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.*

object KFormAPI : Listener {

    private var initialized = false

    val mapper = ObjectMapper().registerKotlinModule()

    private var allocator = 486000 //some initial value
    private val forms = mutableMapOf<UUID, MutableMap<Int, Form<*>>>()

    fun init(plugin: Plugin) {
        if (initialized) {
            return
        }

        plugin.server.pluginManager.registerEvents(this, plugin)
        initialized = true
    }

    fun simpleForm(action: SimpleForm.SimpleFormBuilder.() -> Unit): SimpleForm {
        val builder = SimpleForm.SimpleFormBuilder()

        action(builder)
        return builder.build()
    }

    fun customForm(action: CustomForm.CustomFormBuilder.() -> Unit): CustomForm {
        val builder = CustomForm.CustomFormBuilder()

        action(builder)
        return builder.build()
    }

    fun modalForm(action: ModalForm.ModalFormBuilder.() -> Unit): ModalForm {
        val builder = ModalForm.ModalFormBuilder()

        action(builder)
        return builder.build()
    }

    internal fun showForm(p: Player, form: Form<*>, id: Int? = null) {
        val pk = ModalFormRequestPacket()
        pk.formId = id ?: allocator++
        pk.data = mapper.writeValueAsString(form)

        forms.getOrPut(p.uniqueId) {
            mutableMapOf()
        }[pk.formId] = form

        p.dataPacket(pk)
    }

    @EventHandler
    private fun onQuit(e: PlayerQuitEvent) {
        forms.remove(e.player.uniqueId)
    }

    @EventHandler
    private fun onPacketReceive(e: DataPacketReceiveEvent) {
        if (e.packet.pid() == ProtocolInfo.MODAL_FORM_RESPONSE_PACKET) {
            handleResponse(e.player, e.packet as ModalFormResponsePacket)
        }
    }

    private fun handleResponse(p: Player, pk: ModalFormResponsePacket) {
        val form = forms[p.uniqueId]?.remove(pk.formId) ?: return

        try {
            val response = mapper.readTree(pk.data)

            if ("null" == response.asText()) {
                form.close(p)
            } else {
                try {
                    form.handleResponse(p, response)
                } catch (e: Exception) {
                    MainLogger.getLogger().error("Error while handling form response", e)
                    form.error(p)
                }
            }
        } catch (e: Exception) {
            MainLogger.getLogger().debug("Received corrupted json data", e)
        }
    }
}