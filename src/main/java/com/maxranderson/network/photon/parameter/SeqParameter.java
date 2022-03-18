package com.maxranderson.network.photon.parameter;

import java.util.Arrays;

public class SeqParameter extends Parameter {
    private Parameter[] value;

    public SeqParameter(Parameter[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SeqParameter{" +
                "value=" + Arrays.toString(value) +
                '}';
    }
}
