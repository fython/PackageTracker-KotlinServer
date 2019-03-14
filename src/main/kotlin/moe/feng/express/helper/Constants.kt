package moe.feng.express.helper

import moe.feng.express.helper.model.SimpleMessage

object Constants {

    val MESSAGE_SUCCEED = SimpleMessage("Succeed.", 0)
    val MESSAGE_NO_REGISTERED = SimpleMessage("This token hasn't been registered.", -2)
    val MESSAGE_MISSING_TOKEN_PARAMS = SimpleMessage("Missing \"token\" param.", -3)
    val MESSAGE_WRONG_PARAMETERS = SimpleMessage("Wrong parameters.", -3)

}