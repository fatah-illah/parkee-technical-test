# Linux Shell Scripts Collection

This repository contains a collection of useful Linux shell scripts for various system administration tasks. Each script is designed to be executable and includes error handling for better reliability.

## Table of Contents

1. [File Search by Extension](#1-file-search-by-extension)
2. [System Update Automation](#2-system-update-automation)
3. [SSH Key Generation](#3-ssh-key-generation)
4. [Service Management](#4-service-management)
5. [Remote File Transfer](#5-remote-file-transfer)
    - [Using SCP](#5a-file-transfer-using-scp)
    - [Using RSYNC](#5b-file-transfer-using-rsync)

## Prerequisites

- Bash shell
- Root/sudo access for some scripts
- SSH/SCP/RSYNC installed for file transfer scripts

## Installation

1. Download the scripts to your preferred directory
2. Make them executable with the following command:

```bash
chmod +x *.sh
```

## Usage Instructions

### 1. File Search by Extension

Searches for files with a specific extension in a directory.

```bash
./1_file_search.sh <directory> <extension>
```

**Example:**
```bash
./1_file_search.sh . sh
```

This will list all sh files in the specified directory.

### 2. System Update Automation

Automatically updates all packages on the system and logs the results.

**For Linux:**
```bash
sudo ./2_system_update.sh
```

**For Linux, if you're using a root account already, just run::**
```bash
./2_system_update.sh
```

**For macOS:**
```bash
./2_system_update.sh
```

The script will:
- Detect your Linux distribution's package manager
- Update package lists
- Upgrade installed packages
- Log all operations to `/var/log/system_update_YYYYMMDD_HHMMSS.log`

### 3. SSH Key Generation

Generates an SSH key pair and saves it to a specified directory.

```bash
./3_ssh_key_generator.sh <directory>
```

**Example:**
```bash
./3_ssh_key_generator.sh ~/.ssh
```

This creates:
- The directory if it doesn't exist
- A 4096-bit RSA key pair in the specified directory
- Appropriate permissions for the keys

### 4. Service Management

Manages system services (start, stop, restart, check status).

```bash
sudo ./4_service_management.sh <action> <service-name>
```

**Example:**
```bash
sudo ./4_service_management.sh status com.apple.mDNSResponder
```

Supported actions: `start`, `stop`, `restart`, `status`

### 5. Remote File Transfer

#### 5a. File Transfer using SCP

Copies files or directories to a remote server using SCP.

```bash
scp <source_file> <username@$remote_ip:~/>
```

**Example:**
```bash
scp ./5_remote_copy_scripts/a_scp_copy.sh Fatahillah@localhost:/Users/fatah/Documents/opportunities/parkee
```

This will copy `scp_copy.sh` to your destination directory on the remote server.

#### 5b. File Transfer using RSYNC

Synchronizes files or directories to a remote server using RSYNC.

```bash
rsync -avz <source_file> <username@$remote_ip:~/>
```

**Example:**
```bash
rsync -avz ./5_remote_copy_scripts/b_rsync_copy.sh Fatahillah@localhost:/Users/fatah/Documents/opportunities/parkee
```

RSYNC advantages over SCP:
- Only transfers files that have changed
- Shows a progress indicator
- More efficient for large directories
- Can resume interrupted transfers

## Troubleshooting

- If a script fails with "Permission denied", ensure it has execution permissions
- For remote transfer scripts, make sure you have proper SSH access to the remote server
- System update script requires root privileges

## License

This collection is released under the MIT License. Feel free to modify and distribute according to your needs.
