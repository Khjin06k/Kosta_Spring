package com.codingrecipe.springEx1.sample2;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutputter implements Outputter{
    private String filePath;

    public FileOutputter(String filePath){
        this.filePath=filePath;
    }

    @Override
    public void output(String message) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(message);
        writer.close();
    }
}
