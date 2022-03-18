package com.maxranderson.network.photon.parameter;

public class ShortParameter extends Parameter {
    private short value;

    public ShortParameter(short value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ShortParameter{" +
                "value=" + value +
                '}';
    }
}
