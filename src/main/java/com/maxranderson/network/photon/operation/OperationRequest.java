package com.maxranderson.network.photon.operation;

public class OperationRequest extends PhotonOperation {
    public OperationRequest(int code) {
        super(code);
    }

    @Override
    public String toString() {
        return "OperationRequest{" +
                "code=" + code +
                '}';
    }
}
