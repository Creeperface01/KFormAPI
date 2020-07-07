package com.creeperface.nukkit.kformapi

import cn.nukkit.plugin.PluginBase

class KFormAPIPlugin : PluginBase() {

    override fun onEnable() {
        KFormAPI.init(this)
    }
}