package ru.itmo.cs.kdot.lab2.trig.module;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.itmo.cs.kdot.lab2.trig.Cotangent;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CotangentTest {
    private static final BigDecimal PRECISION = new BigDecimal("0.0000001");
    private Cotangent cot;

    @BeforeEach
    void init() {
        cot = new Cotangent();
    }

    @Test
    void shouldCalculateForPiHalf() {
        MathContext mc = new MathContext(PRECISION.scale(), HALF_EVEN);
        BigDecimal arg = BigDecimalMath.pi(mc).divide(BigDecimal.valueOf(2), mc);
        assertEquals(ZERO.setScale(PRECISION.scale(), HALF_EVEN), cot.calculate(arg, PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI, 0, Math.PI})
    void shouldNotCalculateForPi(double x) {
        BigDecimal arg = BigDecimal.valueOf(x).setScale(PRECISION.scale(), HALF_EVEN);
        assertAll(
                () -> {
                    Throwable exception = assertThrows(ArithmeticException.class, () -> cot.calculate(arg, PRECISION));
                    String msg = format("У котангенса нет значения при x = %s", arg);
                    assertEquals(msg, exception.getMessage());
                }
        );
    }

    @ParameterizedTest(name = "cot({0})")
    @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1, delimiter = ',')
    void testCot(BigDecimal x, BigDecimal y) {
        assertEquals(y, cot.calculate(x, PRECISION));
    }
}
