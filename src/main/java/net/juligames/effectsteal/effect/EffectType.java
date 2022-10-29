package net.juligames.effectsteal.effect;

import net.juligames.effectsteal.Calculable;

public enum EffectType implements Calculable {
    GOOD(1),
    NEUTRAL(0),
    UNKNOWN(0),
    BAD(-1);
    private final int calc;

    EffectType(int calc) {
        this.calc = calc;
    }

    @Override
    public int calc(int value) {
        return value + calc;
    }


}
