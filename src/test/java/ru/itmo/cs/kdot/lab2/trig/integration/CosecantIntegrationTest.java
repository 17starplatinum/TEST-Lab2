package ru.itmo.cs.kdot.lab2.trig.integration;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.cs.kdot.lab2.trig.Cosecant;
import ru.itmo.cs.kdot.lab2.trig.Secant;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CosecantIntegrationTest {

    private static final BigDecimal PRECISION = new BigDecimal("0.0000001");

    @Mock
    private Secant mockSec;

    @Spy
    private Secant spySec;

    @Test
    @DisplayName("Test 1: Call secant")
    void shouldCallSecantFunction() {
        Cosecant csc = new Cosecant(spySec);
        csc.calculate(new BigDecimal(965), PRECISION);
        verify(spySec, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest(name = "mock.sec({0}) = {1}")
    @DisplayName("Test 2: Call cosecant")
    @CsvFileSource(resources = "/Integration/cscIT.csv", numLinesToSkip = 1, delimiter = ',')
    void shouldCallCosecantFunction(BigDecimal x, BigDecimal y) {
        when(mockSec.calculate(x.subtract(BigDecimalMath.pi(new MathContext(PRECISION.scale() + 10, HALF_EVEN)).divide(new BigDecimal(2), HALF_EVEN)), PRECISION)).thenReturn(BigDecimal.valueOf(1 / Math.sin(x.doubleValue())));
        Cosecant csc = new Cosecant(mockSec);
        assertEquals(y, csc.calculate(x, PRECISION));
    }
}
