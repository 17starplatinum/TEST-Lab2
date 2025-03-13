package ru.itmo.cs.kdot.lab2.util;

import lombok.SneakyThrows;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class CSVGraphWriter {
    private final BufferedWriter writer;
    private final AbstractFunction function;
    private FileWriter fileWriter;
    String fileName = System.getProperty("user.dir") + "\\graphs\\";

    public CSVGraphWriter(AbstractFunction function) throws IOException {
        createFileAndDirectory(function);
        this.writer = new BufferedWriter(fileWriter);
        this.function = function;
    }

    private FileWriter createFileAndDirectory(AbstractFunction function) {
        try {
            fileName = fileName + function.getClass().getSimpleName() + ".csv";
            File file = new File(fileName);
            if(file.exists()) {
                fileWriter = new FileWriter(file, false);
            } else {
                file.createNewFile();
                fileWriter = new FileWriter(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileWriter;
    }

    @SneakyThrows
    public void write(BigDecimal x1, BigDecimal x2, BigDecimal d, BigDecimal precision) {
        writer.write("x,y");
        writer.newLine();
        for (BigDecimal i = x1; i.compareTo(x2) <= 0; i = i.add(d)) {
            try {
                BigDecimal y = function.calculate(i, precision);
                writer.write(String.format("%f,%f%n", i, y));
            } catch (ArithmeticException e) {
                writer.write("");
            }
        }
        writer.flush();
    }
}
