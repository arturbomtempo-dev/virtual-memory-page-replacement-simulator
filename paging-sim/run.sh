#!/usr/bin/env bash

# Detect OS for compatibility

OS="$(uname -s 2>/dev/null || echo Windows)"

# Ensure bin directory exists
if [ ! -d "bin" ]; then
    mkdir bin
fi

# Compile
echo "Compilando..."

javac -d bin src/exception/*.java src/validation/*.java src/model/*.java src/parser/*.java src/app/*.java

if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação!"
    exit 1
fi

echo "Compilação concluída!"

# Execute
echo "Executando simulador..."

java -cp bin app.Application