package ru.itmo.cs.kdot.lab2;

import lombok.extern.log4j.Log4j2;
import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;
import ru.itmo.cs.kdot.lab2.util.CSVGraphWriter;
import ru.itmo.cs.kdot.lab2.util.FunctionGraph;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;

@Log4j2
public class Main {
    public static void main(String[] args) {
        final BigDecimal PRECISION = new BigDecimal("0.0000001");
        final BigDecimal POSITIVE_END = ONE.setScale(7, HALF_EVEN);
        final BigDecimal NEGATIVE_END = POSITIVE_END.negate();
        final BigDecimal STEP = new BigDecimal("0.01");
        final String outputDir = System.getProperty("user.dir") + "/graphSrc/";

        new CSVGraphWriter(new Cosine(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Secant(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cosecant(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Tangent(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cotangent(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new NaturalLogarithm(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new BaseNLogarithm(5), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new EquationSystem(), outputDir).write(new BigDecimal(-10), new BigDecimal(10), STEP, new BigDecimal("0.0001"));

        List<String> files = new ArrayList<>();
        try {
            File[] folder = new File(outputDir).listFiles();
            for (File file : folder) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }

//        for (String fileName : files) {
        FunctionGraph.displayChart(EquationSystem.class.getSimpleName().trim(), outputDir + EquationSystem.class.getSimpleName().trim() + ".csv");
//        }
    }
}
