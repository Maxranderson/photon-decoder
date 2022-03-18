package com.maxranderson.network.photon.parameter;

import java.util.Arrays;

public class ByteSeqParameter extends Parameter {
    private byte[] value;

    public ByteSeqParameter(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ByteSeqParameter{" +
                "value=" + Arrays.toString(value) +
                '}';
    }
}
