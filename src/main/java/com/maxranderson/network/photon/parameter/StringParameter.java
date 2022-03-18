package com.maxranderson.network.photon.parameter;

public class StringParameter extends Parameter {
    private String value;

    public StringParameter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StringParameter{" +
                "value='" + value + '\'' +
                '}';
    }
}
