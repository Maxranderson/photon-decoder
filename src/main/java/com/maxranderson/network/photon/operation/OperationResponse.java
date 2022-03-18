package com.maxranderson.network.photon.operation;

public class OperationResponse extends PhotonOperation{
    OperationRequest request;
    String debugString;
    public OperationResponse(int code, OperationRequest request, String debugString) {
        super(code);
        this.request = request;
        this.debugString = debugString;
    }

    @Override
    public String toString() {
        return "OperationResponse{" +
                "request=" + request +
                ", debugString='" + debugString + '\'' +
                '}';
    }
}
