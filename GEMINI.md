# Gemini Context: random-user-mcp Project

This document serves as a guide for generating code within this project. The goal is to maintain consistency with the existing architecture and technologies.

## 1. Core Technologies

- **Language**: Kotlin
- **Web Framework/HTTP Client**: Ktor
- **MCP Framework**: [Model Context Protocol - Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
- **Serialization**: `kotlinx.serialization`. All data classes representing API payloads (both internal and external) must be annotated with `@Serializable`.

## 2. Project Structure and Code Conventions

When adding or modifying code, **strictly** follow the package structure, naming conventions, and coding patterns already established in the project. The primary goal is to maintain internal consistency.

- **Mimic Existing Code**: Before writing new code, analyze the existing files. For example, a new tool must follow the exact same structure and pattern as the tools already created in the `tools` package.

- **`RandomUserMcpServer.kt`**: The server's entry point. Only initialization and tool registration should be done here.

- **`tools/`**: All MCP tool implementations **must** be created here. Maintain the one-file-per-tool pattern.

- **`RandomUserClient.kt`**: Centralizes communication with the external API. New client methods for the `randomuser.me` API should be added here.

- **`RandomUserModels.kt`**: Contains all data classes for deserialization. New data models from the API must be added to this file.

## 3. How to Add a New Tool

To create and register a new tool in the MCP server, follow these steps:

1.  **Create the tool file**: Add a new Kotlin file in the `com.fabianofranca.randomuser.tools` package.
2.  **Implement the tool logic**: Inside the new file, create a function that uses the `server` object to register the new tool (e.g., `server.tool("myNewTool") { ... }`).
3.  **Add models (if necessary)**: If the tool interacts with API data that is not yet modeled, add the corresponding `data classes` in `RandomUserModels.kt`.
4.  **Add the client method (if necessary)**: If the tool requires a new API endpoint, add the call method in `RandomUserClient.kt`.
5.  **Register the tool**: Call the implementation function created in step 2 from within the `setupTools()` method in `RandomUserMcpServer.kt`.

## 4. Standards and Implementation

### Official MCP Documentation

All MCP-related implementations must **strictly** follow the official documentation available at [https://modelcontextprotocol.io/](https://modelcontextprotocol.io/). Do not assume patterns or behaviors. Follow exactly what is specified in the documentation for defining tools, data types, and communication.

### Kotlin SDK Implementation

In addition to the documentation, **faithfully** follow the reference implementation of the [Kotlin SDK for MCP](https://github.com/modelcontextprotocol/kotlin-sdk). Do not assume the existence of code or features that are not explicitly present in the SDK repository. The generated code must be compatible with and based on the existing implementation in the SDK.

## 5. Build Command

After any code modification, the project must be compiled using the following command to ensure there are no compilation errors:

```bash
./gradlew build
```

## 6. Commit Message Guidelines

When writing commit messages for this project:

- **Use English**: All commit messages must be written in English.
- **Write Based on Staged Files**: Commit messages should be written based only on the staged files (files that have been added to the commit with `git add`).
- **Follow Best Practices**:
  - Use the imperative mood (e.g., "Add feature" not "Added feature")
  - Keep the first line under 50 characters
  - Provide a concise summary in the first line
  - If needed, add a blank line followed by a more detailed explanation
  - Reference issue numbers when applicable
- **Structure**:
  ```
  Subject line (50 chars or less)

  More detailed explanatory text, if necessary. Wrap it to about 72
  characters. The blank line separating the summary from the body is
  critical.

  Reference issues at the bottom: #123, #456
  ```
