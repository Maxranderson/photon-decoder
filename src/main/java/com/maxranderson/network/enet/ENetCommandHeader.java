package com.maxranderson.network.enet;

import java.util.Objects;

public class ENetCommandHeader {
    static final int HEADER_LENGTH = 12;

    private ENetProtocol protocol;
    private int channelId;
    private int flags;
    private int reservedByte;
    private int commandLength;
    private int reliableSequenceNumber;

    public ENetCommandHeader(ENetProtocol protocol, int channelId, int flags, int reservedByte, int length, int reliableSequenceNumber) {
        this.protocol = protocol;
        this.channelId = channelId;
        this.flags = flags;
        this.reservedByte = reservedByte;
        this.commandLength = length;
        this.reliableSequenceNumber = reliableSequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ENetCommandHeader that = (ENetCommandHeader) o;
        return channelId == that.channelId && flags == that.flags && reservedByte == that.reservedByte && commandLength == that.commandLength && reliableSequenceNumber == that.reliableSequenceNumber && protocol == that.protocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, channelId, flags, reservedByte, commandLength, reliableSequenceNumber);
    }

    @Override
    public String toString() {
        return "ENetCommandHeader{" +
                "protocol=" + protocol +
                ", channelId=" + channelId +
                ", flags=" + flags +
                ", reservedByte=" + reservedByte +
                ", commandLength=" + commandLength +
                ", reliableSequenceNumber=" + reliableSequenceNumber +
                '}';
    }

    public int commandPayloadLength() {
        return this.commandLength - HEADER_LENGTH;
    }

    public ENetProtocol getProtocol() {
        return protocol;
    }

    public int getChannelId() {
        return channelId;
    }

    public int getFlags() {
        return flags;
    }

    public int getReservedByte() {
        return reservedByte;
    }

    public int getCommandLength() {
        return commandLength;
    }

    public int getReliableSequenceNumber() {
        return reliableSequenceNumber;
    }

    public void setProtocol(ENetProtocol protocol) {
        this.protocol = protocol;
    }
}

