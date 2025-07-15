package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.tools.GetUsersTool
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RandomUserMcpServerTest {

    @Test
    fun `test server initialization with correct tools`() {
        // Given/When: We create a RandomUserMcpServer
        val server = RandomUserMcpServer()

        // Then: The server should be initialized with the expected tools
        val tools = server.tools
        assertEquals(1, tools.size, "Server should have exactly one tool")
        assertTrue(tools.first() is GetUsersTool, "The tool should be a GetUsersTool")
    }
}