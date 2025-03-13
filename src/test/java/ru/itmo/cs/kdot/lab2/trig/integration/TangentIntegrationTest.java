package ru.itmo.cs.kdot.lab2.trig.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.cs.kdot.lab2.trig.Secant;
import ru.itmo.cs.kdot.lab2.trig.Tangent;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TangentIntegrationTest {
    private static final BigDecimal PRECISION = new BigDecimal("0.0000001");

    @Mock
    private Secant mockSec;

    @Spy
    private Secant spySec;

    @Test
    @DisplayName("Test 1: Call secant")
    void shouldCallSecantFunction() {
        Tangent tan = new Tangent(spySec);
        tan.calculate(new BigDecimal(972), PRECISION);
        verify(spySec, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest(name = "mock.tan({0}) = {1}")
    @DisplayName("Test 2: Call tangent")
    @CsvFileSource(resources = "/Integration/tanIT.csv", numLinesToSkip = 1, delimiter = ',')
    void shouldCallTangentFunction(BigDecimal x, BigDecimal y) {
        when(mockSec.calculate(x, PRECISION)).thenReturn(BigDecimal.valueOf(1 / Math.cos(x.doubleValue())));
        Tangent tan = new Tangent(mockSec);
        assertEquals(y, tan.calculate(x, PRECISION));
    }
}
