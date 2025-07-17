package com.fabianofranca.randomuser

import com.fabianofranca.com.fabianofranca.randomuser.DI
import com.fabianofranca.randomuser.base.BaseMcpServer
import com.fabianofranca.randomuser.resources.ModelsResource
import com.fabianofranca.randomuser.tools.GetModelsDocTool
import com.fabianofranca.randomuser.tools.GetPictureInBase64
import com.fabianofranca.randomuser.tools.GetUsersTool

class RandomUserMcpServer : BaseMcpServer("names-server", "0.0.1") {
    override val tools get() = listOf(GetUsersTool(), GetModelsDocTool(), GetPictureInBase64())
    override val resources get() = listOf(ModelsResource())

    override fun onClose() {
        DI.httpClient.close()
    }
}
