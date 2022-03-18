package com.maxranderson.network.enet;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.lang.Integer;
import java.util.Objects;
import java.util.stream.IntStream;

public class ENetPacket {
    static final int MIN_PACKET_LENGTH = 12;

    ENetHeader header;
    byte[] payload;
    private ENetCommand[] commands;

    public ENetPacket(ENetHeader header, byte[] payload) {
        this.header = header;
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ENetPacket that = (ENetPacket) o;
        return header.equals(that.header) && Arrays.equals(payload, that.payload) && Arrays.equals(commands, that.commands);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(header);
        result = 31 * result + Arrays.hashCode(payload);
        result = 31 * result + Arrays.hashCode(commands);
        return result;
    }

    @Override
    public String toString() {
        return "ENetPacket{" +
                "header=" + header +
                ", payload=" + Arrays.toString(payload) +
                ", commands=" + Arrays.toString(commands) +
                '}';
    }

    public static ENetPacket decode(byte[] packetData) {
        if(packetData.length < MIN_PACKET_LENGTH) {
            throw new IllegalArgumentException("Packet data isn't a valid ENetPacket");
        }
        ByteBuffer buffer = ByteBuffer.wrap(packetData);

        int peerId = Short.toUnsignedInt(buffer.getShort());
        int crcEnabled = Short.toUnsignedInt(buffer.get());
        int commandCount = Short.toUnsignedInt(buffer.get());
        long timestamp = Integer.toUnsignedLong(buffer.getInt());
        int challenge = buffer.getInt();
        ENetHeader header = new ENetHeader(peerId, crcEnabled, commandCount, timestamp, challenge);
        byte[] payload = new byte[buffer.remaining()];
        buffer.get(payload);

        return new ENetPacket(header, payload);
    }

    ENetPacket decodeCommands() {
        ByteBuffer buffer = ByteBuffer.wrap(payload);

        this.commands = IntStream.range(0, this.header.commandCount)
            .mapToObj(i -> {
                ENetProtocol protocol = ENetProtocol.valueOf(Byte.toUnsignedInt(buffer.get()));
                int channelId = Byte.toUnsignedInt(buffer.get());
                int flags = Byte.toUnsignedInt(buffer.get());
                int reservedByte = Byte.toUnsignedInt(buffer.get());
                int commandLength = buffer.getInt();
                if(commandLength < ENetCommandHeader.HEADER_LENGTH) {
                    throw new IllegalStateException("Invalid length for a Command: " + commandLength);
                }
                int reliableSequenceNumber = buffer.getInt();
                ENetCommandHeader header = new ENetCommandHeader(protocol, channelId, flags, reservedByte, commandLength, reliableSequenceNumber);
                if(header.commandPayloadLength() > buffer.remaining()) {
                    throw new java.lang.IllegalStateException("Packet payload doesn't have the correct length.");
                }
                byte[] commandPayload = new byte[header.commandPayloadLength()];
                buffer.get(commandPayload);
                return new ENetCommand(header, commandPayload);
            }).toArray(ENetCommand[]::new);

        return this;
    }

    List<ENetCommand> getCommands() {
        if(this.commands != null) {
            return Arrays.asList(this.commands);
        }

        return Collections.emptyList();
    }

}
