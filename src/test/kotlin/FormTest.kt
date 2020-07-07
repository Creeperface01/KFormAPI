import cn.nukkit.Player
import com.creeperface.nukkit.kformapi.KFormAPI
import com.creeperface.nukkit.kformapi.form.util.ImageType
import com.creeperface.nukkit.kformapi.form.util.showForm

fun form1(p: Player) {
    val form = KFormAPI.simpleForm {
        title("Simple Form")
        button("btn 1") { player ->
            player.sendMessage("clicked btn 1")
        }
        button("btn 2", ImageType.URL, "http://nukkitx.eu:8000/nukkitx.png") { player ->
            player.sendMessage("clicked btn 2")
        }

        onSubmit { player, response ->
            //submit
        }

        onClose { player ->
            //close
        }

        onError {
            //error
        }
    }

    p.showForm(form)
}

fun form2(p: Player) {
    val form = KFormAPI.modalForm {
        title("Modal Form")
        content("content")
        trueValue("true value")
        falseValue("false value")

        onSubmit { player, response ->
            if (response) {
                //true
            } else {
                //false
            }
        }

        onClose {
            //close
        }

        onError {
            //error
        }
    }

    p.showForm(form)
}

fun form3(p: Player) {
    val form = KFormAPI.customForm {
        title("Custom Form")
        label("Label")
        dropdown("Dropdown", listOf("option 1", "option 2"))
        input("Input")
        toggle("Toggle - false", false)
        toggle("Toggle - true", true)
        slider("Slider", 5f, 24f, 1)
        stepSlider("Step slider", listOf("2", "17", "35"))

        onSubmit { player, response ->
            val dropdown = response.getDropdown(1)
            val input = response.getInput(2)
            val toggle1 = response.getToggle(3)
            val toggle2 = response.getToggle(4)
            val slider = response.getSlider(5)
            val stepSlider = response.getStepSlider(6)
        }

        onClose {

        }

        onError {

        }
    }

    p.showForm(form)
}