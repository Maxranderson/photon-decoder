package com.maxranderson.network.photon.parameter;

public class IntParameter extends Parameter {
    private int value;

    public IntParameter(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntParameter{" +
                "value=" + value +
                '}';
    }
}
