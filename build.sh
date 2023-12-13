#!/bin/bash

destination_path="/run/media/trouper/1TB drive/IJ/TestingServer (Paper)/plugins/"

./gradlew build

# Check if the build/libs directory exists
if [ -d "build/libs" ]; then
    # Move the contents of build/libs to the specified destination
    mv build/libs/* "$destination_path"
    echo "Build output moved to: $destination_path"
else
    echo "Error: build/libs directory not found. Gradle build might have failed."
fi
