package com.maxranderson.network.photon.parameter;

public class ByteParameter extends Parameter {
    private byte value;

    public ByteParameter(byte value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ByteParameter{" +
                "value=" + value +
                '}';
    }
}
