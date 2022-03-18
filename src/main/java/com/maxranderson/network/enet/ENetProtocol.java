package com.maxranderson.network.enet;

import java.util.Arrays;

public enum ENetProtocol {
    AcknowledgeType(1),
    ConnectType(2),
    VerifyConnectType(3),
    DisconnectType(4),
    PingType(5),
    SendReliableType(6),
    SendUnreliableType(7),
    SendReliableFragmentType(8);

    private final int id;

    ENetProtocol(int id) {
        this.id = id;
    }

    static ENetProtocol valueOf(int id) {
        return Arrays.stream(values())
            .filter(e -> e.id == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not a valid id: " + id));
    }

}
