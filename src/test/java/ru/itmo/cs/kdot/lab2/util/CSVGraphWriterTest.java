package ru.itmo.cs.kdot.lab2.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.List;

import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CSVGraphWriterTest {

    private CSVGraphWriter writer;

    @Mock
    private AbstractFunction mockFunction;

    @Test
    void shouldCreateFile() {
        writer = new CSVGraphWriter(mockFunction);
        File expectedFile = new File(System.getProperty("user.dir") + "\\graphs\\" + mockFunction.getClass().getSimpleName() + ".csv");

        assertTrue(expectedFile.exists(), "Файл должен быть создан");
    }

    @Test
    void shouldWriteToFile() throws IOException {
        when(mockFunction.calculate(any(), any())).thenReturn(ONE);

        writer = new CSVGraphWriter(mockFunction);
        writer.write(ZERO, TEN, ONE, BigDecimal.valueOf(0.01));

        File file = new File(System.getProperty("user.dir") + "\\graphs\\" + mockFunction.getClass().getSimpleName() + ".csv");
        List<String> lines = Files.readAllLines(file.toPath());

        assertEquals("x,y", lines.get(0));
        assertEquals("0.000000,1.000000", lines.get(1));
    }

    @Test
    void shouldHandleArithmeticException() throws IOException {
        when(mockFunction.calculate(any(), any())).thenThrow(new ArithmeticException("Разрыв"));

        writer = new CSVGraphWriter(mockFunction);
        writer.write(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.valueOf(0.01));

        File file = new File(System.getProperty("user.dir") + "\\graphs\\" + mockFunction.getClass().getSimpleName() + ".csv");
        List<String> lines = Files.readAllLines(file.toPath());

        assertTrue(lines.get(1).isEmpty(), "Должна быть пустая строка при разрыве");
        assertEquals("x,y", lines.get(0));
    }

    @Test
    void shouldCallFlush() throws IOException {
        mockFunction = mock(AbstractFunction.class);

        BufferedWriter mockWriter = mock(BufferedWriter.class);

        writer = new CSVGraphWriter(mockWriter, mockFunction);
        writer.write(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.valueOf(0.01));

        verify(mockWriter, atLeastOnce()).flush();
    }

    @AfterEach
    void tearDown() {
        writer = null;
        File file = new File(System.getProperty("user.dir") + "\\graphs\\" + mockFunction.getClass().getSimpleName() + ".csv");
        if (!(file.delete())) {
            System.out.println("Файл удален.");
        } else {
            System.out.println("Файл не удален.");
        }
    }
}
