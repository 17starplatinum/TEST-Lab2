package ru.itmo.cs.kdot.lab2.trig;

import ch.obermuhlner.math.big.BigDecimalMath;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_DOWN;
import static java.math.RoundingMode.HALF_EVEN;

public class Tangent extends AbstractFunction {

    private final Secant secant;
    
    public Tangent() {
        super();
        secant = new Secant();
    }

    public Tangent(final Secant secant) {
        super();
        this.secant = secant;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        isValid(x, precision);

        final BigDecimal pi = BigDecimalMath.pi(new MathContext(precision.scale() + 1, HALF_DOWN));
        final BigDecimal piHalf = pi.divide(BigDecimal.valueOf(2), new MathContext(precision.scale() + 1, HALF_DOWN));

        BigDecimal re = x.abs().remainder(piHalf);
        if(re.compareTo(precision) <= 0 && x.compareTo(ZERO) != 0) {
            throw new ArithmeticException(format("У тангенса нет значения при x = %s", x));
        }
        BigDecimal sec = secant.calculate(x, precision);
        BigDecimal result = (sec.pow(2).subtract(BigDecimal.ONE)).sqrt(new MathContext(precision.scale() + 5, HALF_EVEN));
        BigDecimal remainder = x.remainder(pi);
        boolean secondQuarter = remainder.compareTo(piHalf) > 0 && remainder.compareTo(pi) < 0;
        boolean fourthQuarter = remainder.compareTo(piHalf.negate()) > 0 && remainder.compareTo(ZERO) < 0;
        if(secondQuarter || fourthQuarter) {
            result = result.negate();
        }
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
