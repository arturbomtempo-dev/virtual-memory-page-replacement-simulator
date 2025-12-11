#!/usr/bin/env bash

#####################################################
# Virtual Memory Paging Simulator - Build & Run
# Platform: macOS / Linux
# Usage: ./run.sh
#####################################################

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "=========================================="
echo "  Virtual Memory Paging Simulator"
echo "=========================================="
echo ""

# Ensure required directories exist
if [ ! -d "bin" ]; then
    mkdir -p bin
fi

if [ ! -d "input" ]; then
    echo -e "${RED}ERROR: 'input/' directory not found!${NC}"
    echo "Please create the 'input/' directory and add .txt test files."
    exit 1
fi

if [ ! -d "output" ]; then
    mkdir -p output
fi

# Compile Java sources
echo "Compiling Java sources..."
javac -d bin \
    src/exception/*.java \
    src/validation/*.java \
    src/model/*.java \
    src/parser/*.java \
    src/policy/*.java \
    src/app/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Compilation successful!${NC}"
else
    echo -e "${RED}✗ Compilation failed!${NC}"
    exit 1
fi

echo ""

# Run the simulator
echo "Running simulator..."
echo "------------------------------------------"
java -cp bin app.Application

if [ $? -eq 0 ]; then
    echo "------------------------------------------"
    echo -e "${GREEN}✓ Processing completed!${NC}"
    echo ""
    echo "Output files generated in 'output/' directory:"
    ls -lh output/*.txt 2>/dev/null || echo "  (no output files found)"
else
    echo -e "${RED}✗ Execution failed!${NC}"
    exit 1
fi

echo ""
echo "=========================================="-+