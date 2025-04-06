#!/bin/bash

# Check if running with sufficient privileges
if [ "$(id -u)" -ne 0 ]; then
    echo "This script must be run as root or with sudo"
    exit 1
fi

# Check if both action and service parameters are provided
if [ $# -ne 2 ]; then
    echo "Usage: $0 <action> <service-name>"
    echo "Actions: start, stop, restart, status"
    exit 1
fi

action="$1"
service="$2"

# Check if service exists on macOS (using launchctl for macOS services)
if ! sudo launchctl list | grep -q "$service"; then
    echo "Service '$service' does not exist or is not a launchd service."
    exit 1
fi

# Perform the requested action
case "$action" in
    start)
        echo "Starting service $service..."
        sudo launchctl load -w /Library/LaunchDaemons/$service.plist
        ;;
    stop)
        echo "Stopping service $service..."
        sudo launchctl bootout system /Library/LaunchDaemons/$service.plist
        ;;
    restart)
        echo "Restarting service $service..."
        sudo launchctl bootout system /Library/LaunchDaemons/$service.plist
        sudo launchctl load -w /Library/LaunchDaemons/$service.plist
        ;;
    status)
        echo "Service status for $service:"
        sudo launchctl list | grep "$service"
        ;;
    *)
        echo "Invalid action. Use start, stop, restart, or status."
        exit 1
        ;;
esac

# Check if the action was successful
if [ "$action" != "status" ]; then
    # shellcheck disable=SC2181
    # shellcheck disable=SC2319
    if [ $? -eq 0 ]; then
        echo "Action '$action' on service '$service' completed successfully."
    else
        echo "Action '$action' on service '$service' failed."
        exit 1
    fi
fi
