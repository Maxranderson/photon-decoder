package com.maxranderson.network.enet;

import java.util.Objects;

public class ENetHeader {
    int peerId;
    int crcEnabled;
    int commandCount;
    long timestamp;
    int challenge;

    public ENetHeader(int peerId, int crcEnabled, int commandCount, long timestamp, int challenge) {
        this.peerId = peerId;
        this.crcEnabled = crcEnabled;
        this.commandCount = commandCount;
        this.timestamp = timestamp;
        this.challenge = challenge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ENetHeader that = (ENetHeader) o;
        return peerId == that.peerId && crcEnabled == that.crcEnabled && commandCount == that.commandCount && timestamp == that.timestamp && challenge == that.challenge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(peerId, crcEnabled, commandCount, timestamp, challenge);
    }

    @Override
    public String toString() {
        return "ENetHeader{" +
                "peerId=" + peerId +
                ", crcEnabled=" + crcEnabled +
                ", commandCount=" + commandCount +
                ", timestamp=" + timestamp +
                ", challenge=" + challenge +
                '}';
    }
}
