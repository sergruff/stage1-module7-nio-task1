package com.epam.mjc.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;


public class FileReader {
    private static final Logger log = Logger.getLogger(FileReader.class.getName());

    public Profile getDataFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r")) {
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(64);
            while (fileChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    builder.append((char) byteBuffer.get());
                }
            }
        } catch (FileNotFoundException ex) {
            log.info(ex.getMessage() + " File not found");
        } catch (IOException ex) {
            log.info(ex.getMessage() + " IO exception");
        }
        String[] contents = builder.toString().split("\n");
        for (String s :
                contents) {
            System.out.println(s);
        }
        return new Profile(contents[0].substring(6).strip(),
                Integer.parseInt(contents[1].substring(5).strip()),
                contents[2].substring(7).strip(),
                Long.parseLong(contents[3].substring(7).strip()));
    }
}







