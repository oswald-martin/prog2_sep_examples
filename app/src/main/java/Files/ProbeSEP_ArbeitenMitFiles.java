package Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ProbeSEP_ArbeitenMitFiles {

    private static void filterLinesStartingWith(File originFile, String prefix) throws IOException {
        // Verify arguments, prepare filtered dir and output file object [3P]
        if (originFile.exists() && !originFile.isDirectory() && prefix != null) {

            File unterverzeichnis = new File(originFile.getParent(), "filtered");
            unterverzeichnis.mkdirs();

            File output = new File(unterverzeichnis, originFile.getName());

            // Open files, copy content line by line filtering those beginning with the
            // prefix [6P]

            try (BufferedReader reader = new BufferedReader(new FileReader(originFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(output));) {
                String line = reader.readLine();
                while (line != null) {
                    if (!line.startsWith(prefix)) {
                        writer.write(line);
                        writer.newLine();
                    }
                    line = reader.readLine();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


    public static void main(String[] args) throws IOException {
        //  all to the exam!
        RandomAccessFile file = new RandomAccessFile("data.txt", "rw");
        int length = (int)file.length();
        byte[] buffer = new byte[length];
      
        file.skipBytes(15);
        int pos = (int) file.getFilePointer();
        file.read(buffer, 0, length - pos);
        file.seek(pos+1);
        file.write("all".getBytes());
        file.write(buffer, 0, length - pos - 3);
        file.write("!".getBytes());
        file.close();
     }
}
