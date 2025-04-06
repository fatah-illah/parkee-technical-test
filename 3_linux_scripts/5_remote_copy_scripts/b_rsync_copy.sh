#!/bin/bash

# Check if all parameters are provided
if [ $# -ne 3 ]; then
    echo "Usage: $0 <source_file> <username> <remote_ip>"
    exit 1
fi

source_file="$1"
username="$2"
remote_ip="$3"

# Check if source file/directory exists
if [ ! -e "$source_file" ]; then
    echo "Error: Source file or directory '$source_file' does not exist."
    exit 1
fi

# Destination will be the remote user's home directory
destination="$username@$remote_ip:~/"

echo "Copying '$source_file' to $destination using RSYNC..."

# Use RSYNC to copy the file/directory with progress indicator
rsync -avz --progress "$source_file" "$destination"

# Check if copy was successful
# shellcheck disable=SC2181
if [ $? -eq 0 ]; then
    echo "File/directory '$source_file' has been successfully synchronized to $destination"
else
    echo "Failed to synchronize file/directory to remote server."
    exit 1
fi
