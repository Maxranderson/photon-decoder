package com.maxranderson.network.photon;

import com.maxranderson.network.enet.ENetCommand;
import com.maxranderson.network.enet.ENetProtocol;
import com.maxranderson.network.photon.operation.EventData;
import com.maxranderson.network.photon.operation.OperationRequest;
import com.maxranderson.network.photon.operation.OperationResponse;
import com.maxranderson.network.photon.operation.PhotonOperation;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class PhotonMessage {
    private int signature;
    private PhotonOperation operation;
    private short parameterCount;
    private byte[] data;

    public PhotonMessage(int signature, PhotonOperation operation, short parameterCount, byte[] data) {
        this.signature = signature;
        this.operation = operation;
        this.parameterCount = parameterCount;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PhotonMessage{" +
                "signature=" + signature +
                ", operation=" + operation +
                ", parameterCount=" + parameterCount +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    static PhotonMessage decode(ENetCommand command) {
        if(command.getProtocol() != ENetProtocol.SendReliableType) {
            throw new IllegalArgumentException("Command can't be converted");
        }
        ByteBuffer buffer = ByteBuffer.wrap(command.getPayload());
        int signature = Byte.toUnsignedInt(buffer.get());
        int messageType = Byte.toUnsignedInt(buffer.get());
        if(messageType > 128) {
            throw new IllegalArgumentException("Command is encrypted");
        }
        PhotonOperation operation;
        switch (messageType) {
            case 2:
                operation = new OperationRequest(Byte.toUnsignedInt(buffer.get()));
                break;
            case 3:
            case 7:
                OperationRequest request = new OperationRequest(Byte.toUnsignedInt(buffer.get()));
                int code = Byte.toUnsignedInt(buffer.get());
                String debug = "";
                operation = new OperationResponse(code, request, debug);
                break;
            case 4:
                operation = new EventData(Byte.toUnsignedInt(buffer.get()));
                break;
            default:
                throw new IllegalArgumentException("Command with unknown message type");
        }
        short parameterCount = buffer.getShort();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return new PhotonMessage(signature, operation, parameterCount, data);
    }
}
