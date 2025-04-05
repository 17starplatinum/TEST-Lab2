package ru.itmo.cs.kdot.lab2;

import lombok.extern.log4j.Log4j2;
import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;
import ru.itmo.cs.kdot.lab2.util.CSVGraphWriter;
import ru.itmo.cs.kdot.lab2.util.FunctionGraph;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;

@Log4j2
public class Main {
    public static void main(String[] args) {
        final BigDecimal PRECISION = new BigDecimal("0.0000001");
        final BigDecimal POSITIVE_END = new BigDecimal(10).setScale(7, HALF_EVEN);
        final BigDecimal NEGATIVE_END = POSITIVE_END.negate();
        final BigDecimal STEP = new BigDecimal("0.01");
        final String outputDir = System.getProperty("user.dir") + "\\graphSrc\\";

        // ВАРНИНГ: КОСТЫЛЬ
        final List<String> functionNames = Arrays.asList("log(x)", "csc(x)", "cos(x)", "cot(x)",  "f(x)", "ln(x)", "sec(x)", "tan(x)");

        new CSVGraphWriter(new Cosine(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Secant(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cosecant(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Tangent(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cotangent(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new NaturalLogarithm(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new BaseNLogarithm(5), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new EquationSystem(), outputDir).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);

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

        for(Iterator<String> it1 = functionNames.iterator(), it2 = files.iterator(); it1.hasNext() && it2.hasNext();){
            String fileName = outputDir + it2.next();
            String functionName = it1.next();
            FunctionGraph.displayChart(functionName, fileName);
        }
    }
}
