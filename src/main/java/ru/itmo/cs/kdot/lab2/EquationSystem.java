package ru.itmo.cs.kdot.lab2;

import ch.obermuhlner.math.big.BigDecimalMath;
import lombok.AllArgsConstructor;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;
import ru.itmo.cs.kdot.lab2.log.BaseNLogarithm;
import ru.itmo.cs.kdot.lab2.log.NaturalLogarithm;
import ru.itmo.cs.kdot.lab2.trig.*;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_DOWN;
import static java.math.RoundingMode.HALF_EVEN;

@AllArgsConstructor
public class EquationSystem extends AbstractFunction {
    private final NaturalLogarithm ln;
    private final Cosine cos;
    private final Secant sec;
    private final Cosecant csc;
    private final Tangent tan;
    private final Cotangent cot;
    private final BaseNLogarithm log2;
    private final BaseNLogarithm log3;
    private final BaseNLogarithm log5;
    private final BaseNLogarithm lg;

    public EquationSystem() {
        super();
        ln = new NaturalLogarithm();
        cos = new Cosine();
        sec = new Secant();
        csc = new Cosecant();
        tan = new Tangent();
        cot = new Cotangent();
        log2 = new BaseNLogarithm(2);
        log3 = new BaseNLogarithm(3);
        log5 = new BaseNLogarithm(5);
        lg = new BaseNLogarithm(10);
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        BigDecimal tmpPrecision = precision.setScale(precision.scale() + 10, HALF_EVEN);
        if(x.compareTo(ZERO) <= 0) {
            // x <= 0: (((((tan(x) * cos(x)) + (cot(x) + sec(x))) + csc(x)) * csc(x)) + sec(x))
            try {
                BigDecimal tanCos = tan.calculate(x, tmpPrecision).multiply(cos.calculate(x, tmpPrecision), mc);
                BigDecimal cotSec = cot.calculate(x, tmpPrecision).add(sec.calculate(x, tmpPrecision));
                BigDecimal sum = tanCos.add(cotSec).add(csc.calculate(x, tmpPrecision));
                BigDecimal result = sum.multiply(csc.calculate(x, tmpPrecision)).add(sec.calculate(x, tmpPrecision));
                return result.setScale(precision.scale(), HALF_EVEN);
            } catch (ArithmeticException e) {
                throw new ArithmeticException(format("У функции нет значения при x = %s", x));
            }

        } else {
            // x > 0: (((((log_10(x) - log_10(x)) * (log_2(x) - log_3(x))) * ((log_3(x) - log_5(x)) / log_10(x))) ^ 3) + ((ln(x) * log_10(x)) / log_2(x)))
            BigDecimal logTen = lg.calculate(x, tmpPrecision);
            BigDecimal logFive = log5.calculate(x, tmpPrecision);
            BigDecimal logThree = log3.calculate(x, tmpPrecision);
            BigDecimal logTwo = log2.calculate(x, tmpPrecision);
            try {
                return (logTen.subtract(logTen))
                        .multiply(logTwo.subtract(logThree), mc)
                        .multiply(logThree.subtract(logFive).divide(logTen, HALF_EVEN))
                        .pow(3, mc)
                        .add(ln.calculate(x, tmpPrecision).multiply(logTen, mc)).divide(logTwo, mc.getPrecision(), HALF_EVEN).setScale(precision.scale(), HALF_EVEN);
            } catch (ArithmeticException e) {
                throw new ArithmeticException(format("У функции нет значения при x = %s", x));
            }
        }
    }
}