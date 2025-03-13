package ru.itmo.cs.kdot.lab2;

import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;
import ru.itmo.cs.kdot.lab2.util.CSVGraphWriter;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;

public class Main {
    public static void main(String[] args) throws IOException {
        final BigDecimal PRECISION = new BigDecimal("0.0000001");
        final BigDecimal POSITIVE_END = ONE.setScale(7, HALF_EVEN);
        final BigDecimal NEGATIVE_END = POSITIVE_END.negate();
        final BigDecimal STEP = new BigDecimal("0.01");
        new CSVGraphWriter(new Cosine()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Secant()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cosecant()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Tangent()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new Cotangent()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new NaturalLogarithm()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new BaseNLogarithm(5)).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
        new CSVGraphWriter(new EquationSystem()).write(NEGATIVE_END, POSITIVE_END, STEP, PRECISION);
    }
}
