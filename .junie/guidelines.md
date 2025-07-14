# Project Guidelines for Junie

## Project Overview
This project is a Random User MCP (Model Context Protocol) Server built with Kotlin and Ktor. It integrates with the randomuser.me API and exposes functionalities as tools that can be consumed by AI agents and other MCP-compatible clients.

## Project Structure
- **src/main/kotlin/Application.kt**: Entry point of the application, handling command-line arguments to start the server in either stdio or sse mode.
- **src/main/kotlin/com/fabianofranca/randomuser/RandomUserMcpServer.kt**: Main server class that initializes the MCP server and sets up tools.
- **src/main/kotlin/com/fabianofranca/randomuser/RandomUserClient.kt**: Client for the randomuser.me API.
- **src/main/kotlin/com/fabianofranca/randomuser/RandomUserModels.kt**: Data models for deserializing API responses.
- **src/main/kotlin/com/fabianofranca/randomuser/tools/**: Directory containing tool implementations.

## Core Technologies
- **Language**: Kotlin
- **Web Framework/HTTP Client**: Ktor
- **MCP Framework**: Model Context Protocol - Kotlin SDK
- **Serialization**: kotlinx.serialization

## Testing Guidelines
- When implementing new features or fixing bugs, Junie should run tests to verify the correctness of the solution.
- Tests can be run using the `run_test` command.
- Note that the build command (`.\gradlew.bat build`) already runs all tests, so it's not necessary to execute tests class by class when making changes - simply building the project is sufficient to verify that all tests pass.
- If no tests are available for the specific functionality, Junie should suggest creating appropriate tests.
- All tests should follow the Given/When/Then pattern:
  - **Given**: Set up the test context and preconditions
  - **When**: Execute the action being tested
  - **Then**: Verify the expected outcomes

## Building the Project
- After making changes, Junie should build the project to ensure there are no compilation errors.
- The build command is: `.\gradlew.bat build`
- Junie should run this command before submitting the final solution.

## Code Style Guidelines
- Follow the existing code style and patterns in the project.
- All data classes representing API payloads must be annotated with `@Serializable`.
- Maintain the package structure and naming conventions already established.
- When adding new tools, follow the pattern in the existing tools in the `tools` package.
- New client methods for the randomuser.me API should be added to RandomUserClient.kt.
- New data models should be added to RandomUserModels.kt.
- All dependencies added in build.gradle.kts must be defined in the libs.versions.toml file, following the pattern of existing dependencies. Dependency versions should always be defined as variables in the [versions] section and referenced using version.ref in the [libraries] and [plugins] sections.

## Commit Message Guidelines
- All commit messages must be written in English.
- Write commit messages based only on the staged files (files that have been added to the commit with `git add`).
- Follow good commit message practices:
  - Use the imperative mood (e.g., "Add feature" not "Added feature")
  - Keep the first line under 50 characters
  - Provide a concise summary in the first line
  - If needed, add a blank line followed by a more detailed explanation
  - Reference issue numbers when applicable
- Structure commit messages with a clear subject and optional body:
  ```
  Subject line (50 chars or less)

  More detailed explanatory text, if necessary. Wrap it to about 72
  characters. The blank line separating the summary from the body is
  critical.

  Reference issues at the bottom: #123, #456
  ```

## Adding New Tools
1. Create a new Kotlin file in the `com.fabianofranca.randomuser.tools` package.
2. Implement the tool logic using the `server` object to register the new tool.
3. Add any necessary models to RandomUserModels.kt.
4. Add any necessary client methods to RandomUserClient.kt.
5. Register the tool in the `setupTools()` method in RandomUserMcpServer.kt.

## Documentation
- All code should be well-documented with comments explaining the purpose and functionality.
- Follow the official MCP documentation at https://modelcontextprotocol.io/.
- Adhere to the reference implementation of the Kotlin SDK for MCP.

## Building the Project Correctly for Future Tasks

Based on the previous issue with path format mismatches between WSL and Windows, I'll implement the following approach when you ask me to execute tasks that require building the project:

### Primary Approach: Use Windows Native Commands

I'll always use Windows-native commands through PowerShell to build and run the project:

```powershell
.\gradlew.bat build
```

This avoids the path format mismatch issue entirely by using Windows-style paths throughout the build process.

### If Build Issues Persist

If we encounter any incremental compilation issues, I'll:

1. First try cleaning the build cache:
   ```powershell
   .\gradlew.bat clean build
   ```

### For Testing

When running tests, I'll also use Windows-native commands:
```powershell
.\gradlew.bat test
```

Or for specific tests:
```powershell
.\gradlew.bat test --tests "com.fabianofranca.randomuser.UserLocationTest"
```

By consistently using these Windows-native approaches, we'll avoid the path format mismatch issues that occurred previously, ensuring smooth execution of build tasks in the future.
