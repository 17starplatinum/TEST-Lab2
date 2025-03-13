package ru.itmo.cs.kdot.lab2.function.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.cs.kdot.lab2.EquationSystem;
import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquationSystemIntegrationTest {

    private final EquationSystem equationSystem = new EquationSystem();
    private static final BigDecimal PRECISION = new BigDecimal("0.0000001");
    @Spy
    private Cosine spyCos;
    @Spy
    private NaturalLogarithm spyLn;
    @Spy
    private Secant spySec;
    @Spy
    private Cosecant spyCsc;
    @Spy
    private Tangent spyTan;
    @Spy
    private Cotangent spyCot;
    @Spy
    private BaseNLogarithm spyLog2;
    @Spy
    private BaseNLogarithm spyLog3;
    @Spy
    private BaseNLogarithm spyLog5;
    @Spy
    private BaseNLogarithm spyLg;

    @Mock
    private Cosine mockCos;
    @Mock
    private Secant mockSec;
    @Mock
    private Cosecant mockCsc;
    @Mock
    private Tangent mockTan;
    @Mock
    private Cotangent mockCot;
    @Mock
    private NaturalLogarithm mockLn;
    @Mock
    private BaseNLogarithm mockLog2;
    @Mock
    private BaseNLogarithm mockLog3;
    @Mock
    private BaseNLogarithm mockLog5;
    @Mock
    private BaseNLogarithm mockLg;

    @Test
    void shouldCallAllTrigFunctions() {
        EquationSystem system = new EquationSystem(spyLn, spyCos, spySec, spyCsc, spyTan, spyCot, spyLog2, spyLog3, spyLog5, spyLg);
        system.calculate(new BigDecimal(-5), new BigDecimal("0.0001"));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spySec, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCsc, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyTan, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCot, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verifyNoInteractions(spyLn);
        verifyNoInteractions(spyLog2);
        verifyNoInteractions(spyLog3);
        verifyNoInteractions(spyLog5);
        verifyNoInteractions(spyLg);
    }
    @Test
    void shouldCallAllLogFunctions() {
        EquationSystem system = new EquationSystem(spyLn, spyCos, spySec, spyCsc, spyTan, spyCot, spyLog2, spyLog3, spyLog5, spyLg);
        system.calculate(new BigDecimal(5), new BigDecimal("0.0001"));
        verify(spyLn, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyLog2, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyLog3, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyLog5, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyLg, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verifyNoInteractions(spyCos);
        verifyNoInteractions(spySec);
        verifyNoInteractions(spyCsc);
        verifyNoInteractions(spyTan);
        verifyNoInteractions(spyCot);
    }

    @ParameterizedTest(name = "f({0}) = {1}")
    @DisplayName("Test 3: Call function")
    @CsvFileSource(resources = "/Integration/systemIT.csv", numLinesToSkip = 1, delimiter = ',')
    void shouldCalculateWithMockFunctions(BigDecimal x, BigDecimal y) {
        if(x.compareTo(ZERO) > 0) {
            when(mockLn.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.log(x.doubleValue())));
            when(mockLog2.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.log(x.doubleValue()) / Math.log(2)));
            when(mockLog3.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.log(x.doubleValue()) / Math.log(3)));
            when(mockLog5.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.log(x.doubleValue()) / Math.log(5)));
            when(mockLg.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.log10(x.doubleValue())));
        } else {
            when(mockCos.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.cos(x.doubleValue())));
            when(mockSec.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1 / Math.cos(x.doubleValue())));
            when(mockCsc.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1 / Math.sin(x.doubleValue())));
            when(mockTan.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(Math.tan(x.doubleValue())));
            when(mockCot.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1 / Math.tan(x.doubleValue())));
        }
        EquationSystem system = new EquationSystem(mockLn, mockCos, mockSec, mockCsc, mockTan, mockCot, mockLog2, mockLog3, mockLog5, mockLg);
        assertEquals(y, system.calculate(x, PRECISION));
    }
}
