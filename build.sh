#!/bin/bash

# Run Gradle build
./gradlew build

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "Gradle build successful."

    # SFTP upload
    SFTP_HOST="192.168.1.199"
    SFTP_USER="trouper"
    SFTP_PASSWORD="Trouper12()1"
    SFTP_REMOTE_DIR="/home/trouper/docker/data/plugins/"

    # Create a temporary file with a unique name
    TEMP_FILE=$(mktemp)

    # Specify the local file to upload
    LOCAL_FILE="/run/media/trouper/'1TB drive'/IJ/IdeaProjects/YessirBox/build/libs/YessirBox-0.0.2.jar"

    # Write the SFTP commands to the temporary file
    echo "put $LOCAL_FILE $SFTP_REMOTE_DIR" > "$TEMP_FILE"
    echo "bye" >> "$TEMP_FILE"

    # Use sftp non-interactively with the specified commands
    sftp -oStrictHostKeyChecking=no -oBatchMode=no -b "$TEMP_FILE" "$SFTP_USER@$SFTP_HOST" <<EOF
    $SFTP_PASSWORD
EOF

    # Remove the temporary file
    rm -f "$TEMP_FILE"

    # Wait for a short delay (adjust as needed)
    sleep 0.5

    # SSH command to reload the plugin on the host
    SSH_COMMAND="docker exec docker-mc-1 rcon-cli pm reload YessirBox"
    SSH_COMMAND2="docker exec docker-mc-1 rcon-cli say Plugin imported Successfully"
    ssh -oStrictHostKeyChecking=no -oBatchMode=no "$SFTP_USER@$SFTP_HOST" "$SSH_COMMAND"
    ssh -oStrictHostKeyChecking=no -oBatchMode=no "$SFTP_USER@$SFTP_HOST" "$SSH_COMMAND2"


    echo "Plugin reloaded."
else
    echo "Gradle build failed."
fi
