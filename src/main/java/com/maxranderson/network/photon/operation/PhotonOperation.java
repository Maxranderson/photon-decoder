package com.maxranderson.network.photon.operation;

public abstract class PhotonOperation {
    protected int code;

    public PhotonOperation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "PhotonOperation{" +
                "code=" + code +
                '}';
    }
}
