package com.maxranderson.network.enet;

import java.util.Arrays;
import java.util.Objects;

public class ENetCommand {
    private ENetCommandHeader header;

    private byte[] payload;

    public ENetCommand(ENetCommandHeader header, byte[] payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ENetCommand that = (ENetCommand) o;
        return header.equals(that.header) && Arrays.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(header);
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return "ENetCommand{" +
                "header=" + header +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }

    public ENetProtocol getProtocol() {
        return header.getProtocol();
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public void setProtocol(ENetProtocol protocol) {
        this.header.setProtocol(protocol);
    }
}
