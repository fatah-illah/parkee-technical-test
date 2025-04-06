#!/bin/bash

# Check if a directory parameter is provided
if [ $# -ne 1 ]; then
    echo "Usage: $0 <directory>"
    exit 1
fi

key_dir="$1"

# Create directory if it doesn't exist
if [ ! -d "$key_dir" ]; then
    echo "Directory '$key_dir' does not exist. Creating it..."
    mkdir -p "$key_dir"

    # Check if directory creation was successful
    # shellcheck disable=SC2181
    if [ $? -ne 0 ]; then
        echo "Failed to create directory '$key_dir'"
        exit 1
    fi
    echo "Directory '$key_dir' created successfully."
fi

# Generate SSH key pair
key_name="$key_dir/id_rsa"
echo "Generating SSH key pair in '$key_dir'..."

# Check if key already exists
if [ -f "$key_name" ]; then
    echo "Warning: SSH key already exists at '$key_name'"
    # shellcheck disable=SC2162
    read -p "Do you want to overwrite it? (y/n): " answer
    if [[ "$answer" != "y" && "$answer" != "Y" ]]; then
        echo "Operation cancelled."
        exit 0
    fi
fi

# Generate the key pair
ssh-keygen -t rsa -b 4096 -f "$key_name" -N ""

# Check if key generation was successful
# shellcheck disable=SC2181
if [ $? -eq 0 ]; then
    echo "SSH key pair generated successfully:"
    echo "  Private key: $key_name"
    echo "  Public key: $key_name.pub"

    # Set appropriate permissions
    chmod 700 "$key_dir"
    chmod 600 "$key_name"
    chmod 644 "$key_name.pub"

    echo "Key permissions set for security."
else
    echo "Failed to generate SSH key pair."
    exit 1
fi
