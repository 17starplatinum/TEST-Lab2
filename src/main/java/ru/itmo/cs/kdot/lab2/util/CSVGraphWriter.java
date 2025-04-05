package ru.itmo.cs.kdot.lab2.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

import static java.lang.String.format;

@Log4j2
public class CSVGraphWriter {
    private final BufferedWriter writer;
    private final AbstractFunction function;
    @Getter
    private final String filePath;

    public CSVGraphWriter(AbstractFunction function, String outputDir) {
        this.filePath = getFilePath(outputDir, function);
        this.writer = createWriter();
        this.function = function;
    }

    public CSVGraphWriter(BufferedWriter writer, AbstractFunction function, String outputDir) {
        this.filePath = getFilePath(outputDir, function);
        this.writer = writer;
        this.function = function;
    }

    private String getFilePath(String outputDir, AbstractFunction function) {
        return outputDir + function.getClass().getSimpleName() + ".csv";
    }

    private BufferedWriter createWriter() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            if (file.exists()) {
                return new BufferedWriter(new FileWriter(file, false));
            } else {
                return new BufferedWriter(new FileWriter(file));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void write(BigDecimal x1, BigDecimal x2, BigDecimal d, BigDecimal precision) {
        try {
            writer.write("x,y");
            writer.newLine();
            for (BigDecimal i = x1; i.compareTo(x2) <= 0; i = i.add(d)) {
                calculateFunction(writer, function, i, precision);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void calculateFunction(BufferedWriter writer, AbstractFunction function, BigDecimal i, BigDecimal precision) throws IOException{
        try {
            BigDecimal y = function.calculate(i, precision);
            if(y != null && (y.compareTo(new BigDecimal(10)) <= 0 && y.compareTo(new BigDecimal(-10)) >= 0)) {
                writer.write(format(Locale.ENGLISH, "%f,%f%n", i, y));
            } else {
                writer.newLine();
            }
        } catch (ArithmeticException e) {
            writer.newLine();
        } finally {
            writer.flush();
        }
    }
}
