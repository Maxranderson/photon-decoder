package com.maxranderson.network.photon.parameter;

public class LongParameter extends Parameter {
    private long value;

    public LongParameter(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongParameter{" +
                "value=" + value +
                '}';
    }
}
