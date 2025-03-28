package ru.itmo.cs.kdot.lab2;

import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;
import ru.itmo.cs.kdot.lab2.util.CSVGraphWriter;
import ru.itmo.cs.kdot.lab2.util.FunctionGraph;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;

public class Main {
    public static void main(String[] args) {
        final BigDecimal PRECISION = new BigDecimal("0.0000001");
        final BigDecimal POSITIVE_END = ONE.setScale(7, HALF_EVEN);
        final BigDecimal NEGATIVE_END = POSITIVE_END.negate();
        final BigDecimal STEP = new BigDecimal("0.01");
        final String[] functionNames = new String[]{"cos(x)", "sec(x)", "csc(x)", "tan(x)", "cot(x)", "ln(x)", "log(x)", "f(x)"};
        new CSVGraphWriter(new Cosine()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Secant()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cosecant()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Tangent()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cotangent()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new NaturalLogarithm()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new BaseNLogarithm(5)).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new EquationSystem()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);

        for(String name : functionNames) {
            FunctionGraph graph = new FunctionGraph("График функций", name);
            graph.pack();
            graph.setVisible(true);
        }
    }
}
