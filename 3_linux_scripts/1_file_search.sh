#!/bin/bash

# Check if both parameters are provided
if [ $# -ne 2 ]; then
    echo "Usage: $0 <directory> <extension>"
    exit 1
fi

directory="$1"
extension="$2"

# Check if directory exists
if [ ! -d "$directory" ]; then
    echo "Error: Directory '$directory' does not exist."
    exit 1
fi

dir_name=$(basename "$(cd "$directory" && pwd)")

# Remove leading dot from extension if present
extension="${extension#.}"

echo "Files with extension '.$extension' in directory '$dir_name':"
find "$directory" -type f -name "*.$extension" | while read -r file; do
    # shellcheck disable=SC2005
    echo "$(basename "$file")"
done
