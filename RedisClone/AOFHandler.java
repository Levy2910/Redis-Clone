package RedisClone;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AOFHandler {
    private final BufferedWriter bufferedWriter;

    public AOFHandler() throws IOException {
        FileWriter fileWriter = new FileWriter("data.aof", true);
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    public synchronized void append(String stringToAppend) throws IOException {
        bufferedWriter.write(stringToAppend + "\n");
        bufferedWriter.flush();
    }

    public void close() throws IOException {
        bufferedWriter.close();
    }
}
