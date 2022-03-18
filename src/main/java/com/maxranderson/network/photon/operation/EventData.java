package com.maxranderson.network.photon.operation;

public class EventData extends PhotonOperation {

    public EventData(int code) {
        super(code);
    }

    @Override
    public String toString() {
        return "EventData{" +
                "code=" + code +
                '}';
    }
}
