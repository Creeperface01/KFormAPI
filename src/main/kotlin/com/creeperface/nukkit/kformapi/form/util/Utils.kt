package com.creeperface.nukkit.kformapi.form.util

import cn.nukkit.Player
import com.creeperface.nukkit.kformapi.KFormAPI
import com.creeperface.nukkit.kformapi.form.Form

fun Player.showForm(form: Form<*>, id: Int? = null) {
    KFormAPI.showForm(this, form, id)
}