package com.maxranderson.network.photon.parameter;

public class BooleanParameter extends Parameter {
    private boolean value;

    public BooleanParameter(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanParameter{" +
                "value=" + value +
                '}';
    }
}
