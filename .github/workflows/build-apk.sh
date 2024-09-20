#!/bin/bash

# Checkout the specified branch
git checkout $1

# Set up Java and Android SDK (ensure these are installed and configured)
echo "Setting up Java and Android SDK..."
# Example commands (adjust paths accordingly)
# sudo apt-get install openjdk-17-jdk
# sdkmanager --install "build-tools;30.0.3"

# Build the APK
./gradlew assembleDebug