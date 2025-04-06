#!/bin/bash

# Define log file
LOG_FILE="/var/log/system_update_$(date +%Y%m%d_%H%M%S).log"

# Detect OS
OS=$(uname -s)

# If macOS, use Homebrew without sudo
if [ "$OS" = "Darwin" ]; then
    echo "Detected macOS. Using Homebrew for updates." | tee -a "$LOG_FILE"

    # Make sure Homebrew is installed
    if ! command -v brew &> /dev/null; then
        echo "Error: Homebrew is not installed. Please install Homebrew first." | tee -a "$LOG_FILE"
        exit 1
    fi

    echo "Updating Homebrew packages..." | tee -a "$LOG_FILE"
    brew update 2>&1 | tee -a "$LOG_FILE"
    echo "Upgrading Homebrew packages..." | tee -a "$LOG_FILE"
    brew upgrade 2>&1 | tee -a "$LOG_FILE"

    echo "System update completed on $(date)" | tee -a "$LOG_FILE"
    echo "Log saved to $LOG_FILE"
    exit 0
fi

# If Linux, make sure the script is run as root
if [ "$(id -u)" -ne 0 ]; then
    echo "This script must be run as root" | tee -a "$LOG_FILE"
    exit 1
fi

# Detect package manager for Linux
if command -v apt &> /dev/null; then
    PKG_MANAGER="apt"
elif command -v dnf &> /dev/null; then
    PKG_MANAGER="dnf"
elif command -v yum &> /dev/null; then
    PKG_MANAGER="yum"
elif command -v pacman &> /dev/null; then
    PKG_MANAGER="pacman"
elif command -v zypper &> /dev/null; then
    PKG_MANAGER="zypper"
else
    echo "Unsupported package manager" | tee -a "$LOG_FILE"
    exit 1
fi

# Start logging
echo "Starting system update on $(date)" | tee -a "$LOG_FILE"
echo "Using package manager: $PKG_MANAGER" | tee -a "$LOG_FILE"

# Update package lists and upgrade packages based on detected package manager
case $PKG_MANAGER in
    apt)
        echo "Updating package lists..." | tee -a "$LOG_FILE"
        apt update 2>&1 | tee -a "$LOG_FILE"
        echo "Upgrading packages..." | tee -a "$LOG_FILE"
        apt upgrade -y 2>&1 | tee -a "$LOG_FILE"
        ;;
    dnf)
        echo "Upgrading packages..." | tee -a "$LOG_FILE"
        dnf upgrade -y 2>&1 | tee -a "$LOG_FILE"
        ;;
    yum)
        echo "Upgrading packages..." | tee -a "$LOG_FILE"
        yum update -y 2>&1 | tee -a "$LOG_FILE"
        ;;
    pacman)
        echo "Upgrading packages..." | tee -a "$LOG_FILE"
        pacman -Syu --noconfirm 2>&1 | tee -a "$LOG_FILE"
        ;;
    zypper)
        echo "Upgrading packages..." | tee -a "$LOG_FILE"
        zypper update -y 2>&1 | tee -a "$LOG_FILE"
        ;;
esac

echo "System update completed on $(date)" | tee -a "$LOG_FILE"
echo "Log saved to $LOG_FILE"
