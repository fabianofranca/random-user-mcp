#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

java -jar "$SCRIPT_DIR/build/libs/random-user-mcp-all.jar"