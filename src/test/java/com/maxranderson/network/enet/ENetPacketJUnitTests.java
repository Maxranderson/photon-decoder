package com.maxranderson.network.enet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ENetPacketJUnitTests {

    @Test
    void decode() {
        byte[] data = {0, 0, 1, 0, 113, 101, -125, -70, 43, 7, -3, -75};
        ENetHeader header = new ENetHeader(0, 1, 0, 1902478266, 721943989);
        ENetPacket expected = new ENetPacket(header, new byte[0]);
        assertEquals(expected, ENetPacket.decode(data));
    }

    @Test
    void invalidData() {
        byte[] data = {0, 0, 1, 0, 113, 101, -125, -70, 43, 7, -3};
        assertThrows(IllegalArgumentException.class,() -> ENetPacket.decode(data));
    }

    @Test
    void decodeCommands() {
        byte[] payload = {1,0,0,0,0,0,0,12,0,0,0,1};
        ENetHeader header = new ENetHeader(0, 1, 1, 1902478266, 721943989);
        ENetPacket packet = new ENetPacket(header, payload);
        ENetCommandHeader comHeader = new ENetCommandHeader(ENetProtocol.AcknowledgeType, 0,0,0,12, 1);
        ENetCommand expected = new ENetCommand(comHeader, new byte[0]);

        assertEquals(expected, packet.decodeCommands().getCommands().get(0));
    }

    @Test
    void invalidCommandLength() {
        byte[] payload = {1,0,0,0,0,0,0,5,0,0,0,1};
        ENetHeader header = new ENetHeader(0, 1, 1, 1902478266, 721943989);
        ENetPacket packet = new ENetPacket(header, payload);

        assertThrows(IllegalStateException.class,() -> packet.decodeCommands().getCommands());
    }

    @Test
    void invalidCommandPayloadLength() {
        byte[] payload = {1,0,0,0,0,0,0,15,0,0,0,1};
        ENetHeader header = new ENetHeader(0, 1, 1, 1902478266, 721943989);
        ENetPacket packet = new ENetPacket(header, payload);

        assertThrows(IllegalStateException.class,() -> packet.decodeCommands().getCommands());
    }
}
