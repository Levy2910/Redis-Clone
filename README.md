# Redis Clone in Java

This is a lightweight Redis-like key-value store built from scratch in Java. It replicates core Redis functionalities such as command processing, data persistence, key expiration, and client-server communication over TCP. The project gave me hands-on experience with Java I/O, networking, and multithreading.

## Features

- Basic commands: `SET`, `GET`, `DEL`, `EXPIRE`, and others
- Append-Only File (AOF) for persisting write operations
- Background thread for expiring keys based on TTL
- Snapshot system that creates backups every 5 minutes
- TCP socket server that handles multiple client connections
- Thread-safe state management with synchronization and locking
- Startup recovery: server loads data from AOF on launch

## What I Learned

- Java networking using `Socket`, `ServerSocket`, and stream handling
- Buffered reading, writing, flushing, and managing I/O resources properly
- Managing concurrency in Java using threads, locks, and synchronization
- Redis internals: how AOF and RDB work, and how Redis handles key expiration
- Building persistent and fault-tolerant systems from the ground up

## Tech Stack

- Java (Core language)
- Java Threads and Synchronization
- File I/O for AOF and snapshot persistence
- TCP sockets for client-server communication

## How It Works

1. The server starts and loads the data from the AOF file if present.
2. Clients connect over TCP and send simple text-based commands.
3. The server executes the commands, updates in-memory data, and appends the changes to the AOF.
4. Background threads:
   - Remove expired keys
   - Create periodic snapshot backups

## Running the Project

```bash
# Clone the repository
git clone https://github.com/your-username/redis-clone-java.git
cd redis-clone-java

# Compile and run
javac RedisServer.java
java RedisServer
