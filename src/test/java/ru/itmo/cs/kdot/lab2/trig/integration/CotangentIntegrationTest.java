package ru.itmo.cs.kdot.lab2.trig.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.cs.kdot.lab2.trig.Cosecant;
import ru.itmo.cs.kdot.lab2.trig.Cotangent;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotangentIntegrationTest {

    private static final BigDecimal PRECISION = new BigDecimal("0.0000001");

    @Mock
    private Cosecant mockCsc;

    @Spy
    private Cosecant spyCsc;

    @Test
    @DisplayName("Test 1: Call cosecant")
    void shouldCallCosecantFunction() {
        Cotangent cot = new Cotangent(spyCsc);
        cot.calculate(new BigDecimal(979), PRECISION);
        verify(spyCsc, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest(name = "mock.csc({0}) = {1}")
    @DisplayName("Test 2: Call cotangent")
    @CsvFileSource(resources = "/Integration/cotIT.csv", numLinesToSkip = 1, delimiter = ',')
    void shouldCallCotangentFunction(BigDecimal x, BigDecimal y) {
        when(mockCsc.calculate(x, PRECISION)).thenReturn(BigDecimal.valueOf(1 / Math.sin(x.doubleValue())));
        Cotangent cot = new Cotangent(mockCsc);
        assertEquals(y, cot.calculate(x, PRECISION));
    }
}
