package com.maxranderson.network.photon.parameter;

public class FloatParameter extends Parameter {
    private float value;

    public FloatParameter(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FloatParameter{" +
                "value=" + value +
                '}';
    }
}
