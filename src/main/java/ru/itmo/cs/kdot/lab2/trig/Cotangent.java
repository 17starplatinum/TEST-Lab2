package ru.itmo.cs.kdot.lab2.trig;

import ch.obermuhlner.math.big.BigDecimalMath;
import ru.itmo.cs.kdot.lab2.function.AbstractFunction;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

public class Cotangent extends AbstractFunction {

    private static final BigDecimal PI = BigDecimalMath.pi(new MathContext(DECIMAL128.getPrecision()));
    private static final BigDecimal PI_2 = PI.divide(BigDecimal.valueOf(2), HALF_EVEN);
    private final Cosecant cosecant;

    public Cotangent() {
        super();
        cosecant = new Cosecant();
    }

    public Cotangent(final Cosecant cosecant) {
        super();
        this.cosecant = cosecant;
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) throws ArithmeticException {
        isValid(x, precision);
        MathContext mc = new MathContext(precision.scale() + 10, HALF_EVEN);

        if(x.remainder(PI).abs().compareTo(precision) <= 0) {
            throw new ArithmeticException(format("У котангенса нет значения при x = %s", x));
        }
        BigDecimal csc = cosecant.calculate(x, precision);

        BigDecimal result = csc.pow(2, mc).subtract(BigDecimal.ONE).sqrt(mc);

        if (result.abs().compareTo(precision) < 0) {
            return ZERO.setScale(precision.scale(), HALF_EVEN);
        }
        BigDecimal remainder = x.remainder(PI);

        if((remainder.compareTo(PI_2) > 0 && remainder.compareTo(PI) < 0) || (remainder.compareTo(PI_2.negate()) > 0 && remainder.compareTo(ZERO) < 0)) {
            result = result.negate();
        }
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
