package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.resources.ModelsResource
import com.fabianofranca.randomuser.tools.GetModelsDocTool
import com.fabianofranca.randomuser.tools.GetUsersTool

class RandomUserMcpServer : BaseMcpServer("names-server", "0.0.1") {
    override val tools get() = listOf(GetUsersTool(), GetModelsDocTool())
    override val resources get() = listOf(ModelsResource())
}
