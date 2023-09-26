package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;
import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.time.Instant.now;

public class Main {
    public static void main(String[] args) throws IOException {
        var main = new Main();
         main.runBST();
        // main.runCountingSort();

    }

    void runBST() throws IOException {
        Files.deleteIfExists(Paths.get("put.csv"));
        Files.deleteIfExists(Paths.get("get.csv"));
        Files.deleteIfExists(Paths.get("delete.csv"));
        var putFile = Files.createFile(Paths.get("put.csv"));
        var getFile = Files.createFile(Paths.get("get.csv"));
        var deleteFile = Files.createFile(Paths.get("delete.csv"));

        int k = 10_000;
        for (int i = 0; i < 100; i++) {
            var in = new ArrayList<Integer>(k);
            for (int j = 0; j < k; j++) {
                in.add(ThreadLocalRandom.current().nextInt());
            }

            var rbt = new RedBlackTree<Integer, Integer>();

            var startInsert = now().toEpochMilli();
            for (var n : in)
                rbt.put(n, n);
            writeString(putFile, "" + k + "," + (now().toEpochMilli() - startInsert) + lineSeparator(), APPEND);

            var startGet = now().toEpochMilli();
            for (var n : in)
                rbt.get(n);
            writeString(getFile, "" + k + "," + (now().toEpochMilli() - startGet) + lineSeparator(), APPEND);

            var startDelete = now().toEpochMilli();
            for (var n : in)
                rbt.delete(n);
            writeString(deleteFile, "" + k + "," + (now().toEpochMilli() - startDelete) + lineSeparator(), APPEND);

            k += 10_000;
        }
    }

    void runCountingSort() {
        int[] arr = {2, 5, 2, 3, 8, 6, 3};
        new CountingSort().sort(arr);
        out.println(Arrays.toString(arr));
    }
}