package ru.itmo.cs.kdot.lab2.trig;

import ch.obermuhlner.math.big.BigDecimalMath;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_EVEN;

public class Cosecant extends AbstractFunction {

    private final Secant secant;

    public Cosecant() {
        super();
        secant = new Secant();
    }

    public Cosecant(final Secant secant) {
        super();
        this.secant = secant;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) throws ArithmeticException {
        isValid(x, precision);
        MathContext mc = new MathContext(precision.scale() + 10, HALF_EVEN);
        if(x.abs().remainder(BigDecimalMath.pi(mc)).compareTo(precision) <= 0) {
            throw new ArithmeticException(format("У косеканса нет значения при x = %s", x));
        }
        return secant.calculate(x.subtract(BigDecimalMath.pi(mc).divide(BigDecimal.valueOf(2), HALF_EVEN)), precision).setScale(precision.scale(), HALF_EVEN);
    }
}
