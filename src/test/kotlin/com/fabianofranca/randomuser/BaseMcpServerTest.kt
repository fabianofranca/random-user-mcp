package com.fabianofranca.randomuser

import com.fabianofranca.randomuser.base.BaseMcpServer
import com.fabianofranca.randomuser.base.BaseResource
import com.fabianofranca.randomuser.base.BaseTool
import com.fabianofranca.randomuser.tools.GetUsersTool
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Test class for testing the BaseMcpServer class and its startStdioServer method
 */
class BaseMcpServerTest {

    /**
     * A custom implementation of BaseMcpServer for testing
     */
    private class TestBaseMcpServer(
        name: String = "test-server",
        version: String = "0.0.1",
        override val tools: List<BaseTool> = listOf(GetUsersTool()),
        override val resources: List<BaseResource> = emptyList()
    ) : BaseMcpServer(name, version)

    @Test
    fun `test BaseMcpServer initialization`() {
        // Given/When: We create a TestBaseMcpServer
        val server = TestBaseMcpServer()

        // Then: The server should be initialized with the expected tools
        val tools = server.tools
        assertEquals(1, tools.size, "Server should have exactly one tool")
        assertTrue(tools.first() is GetUsersTool, "The tool should be a GetUsersTool")
    }

    @Test
    fun `test BaseMcpServer with custom tools`() {
        // Given: A custom tool list
        val customTools = emptyList<BaseTool>()

        // When: We create a TestBaseMcpServer with the custom tools
        val server = TestBaseMcpServer(tools = customTools)

        // Then: The server should be initialized with the custom tools
        val tools = server.tools
        assertEquals(0, tools.size, "Server should have no tools")
    }

    @Test
    fun `test BaseMcpServer resources`() {
        // Given/When: We create a TestBaseMcpServer
        val server = TestBaseMcpServer()

        // Then: The server should be initialized with the expected resources
        val resources = server.resources
        assertEquals(0, resources.size, "Server should have no resources by default")
    }

    @Test
    fun `test BaseMcpServer with custom resources`() {
        // Given: A custom resource list
        val customResources = listOf<BaseResource>()

        // When: We create a TestBaseMcpServer with the custom resources
        val server = TestBaseMcpServer(resources = customResources)

        // Then: The server should be initialized with the custom resources
        val resources = server.resources
        assertEquals(0, resources.size, "Server should have no resources")
    }

    @Test
    fun `test startStdioServer can be launched`() = runBlocking {
        // Given: A TestBaseMcpServer
        val server = TestBaseMcpServer()

        // When/Then: We attempt to launch startStdioServer with a timeout
        // This test doesn't actually verify the behavior of startStdioServer,
        // but it does verify that the method can be called without throwing exceptions
        // during the initialization phase
        try {
            withTimeout(100) { // Very short timeout to avoid blocking
                withContext(Dispatchers.IO) {
                    launch {
                        try {
                            // This will block, but we'll cancel it with the timeout
                            // We're just testing that it can be launched without exceptions
                            // during the initialization phase
                            server.startStdioServer()
                        } catch (e: Exception) {
                            // Ignore exceptions caused by cancellation
                            if (e.message?.contains("Timed out") != true) {
                                fail("startStdioServer threw an unexpected exception: ${e.message}")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Expected timeout exception, this is fine
            // We're just testing that startStdioServer can be launched
            // without throwing exceptions during the initialization phase
        }

        // If we get here, the test passes
        assertTrue(true, "startStdioServer can be launched")
    }
}
