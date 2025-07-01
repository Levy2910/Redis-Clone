# Redis Clone in Java

A lightweight Redis-like key-value store built from scratch using Java. This project mimics core Redis functionalities, including command processing, data persistence, and key expiration — while providing hands-on experience with Java networking, multithreading, and I/O.

## 🚀 Features

- **Basic Commands**: `SET`, `GET`, `DEL`, `EXPIRE`, and more
- **Append-Only File (AOF)**: Persists write commands to disk for data durability
- **Key Expiry**: Background thread removes expired keys based on TTL
- **Periodic Snapshots**: Backup mechanism that creates data snapshots every 5 minutes
- **TCP Socket Communication**: Accepts client connections over TCP
- **Thread-Safe Operations**: Synchronized access and locking for shared state
- **Startup Recovery**: Loads data from AOF to restore state on server restart

## 🧠 What I Learned

- Low-level networking using Java’s `Socket`, `ServerSocket`, and streams
- Java I/O: Buffered reading, writing, flushing, and proper resource handling
- Threading and concurrency: `synchronized`, locks, and managing background workers
- Redis architecture and persistence strategies (AOF and RDB concepts)
- Building reliable systems that persist and recover state

## 📦 Tech Stack

- **Language**: Java
- **Concurrency**: Java Threads & Synchronization
- **Persistence**: File I/O, AOF-style logging, and custom snapshot mechanism
- **Networking**: Java TCP sockets

## 🔧 How It Works

1. Server starts and loads data from the AOF file (if it exists).
2. Clients connect via TCP and send Redis-like commands.
3. The server processes commands, modifies in-memory state, and appends to AOF.
4. Background threads:
   - Expire old keys based on TTL
   - Create periodic snapshots for backup

## 🛠️ Setup & Run

```bash
# Clone the repository
git clone https://github.com/your-username/redis-clone-java.git
cd redis-clone-java

# Compile and run (example if using CLI)
javac RedisServer.java
java RedisServer
