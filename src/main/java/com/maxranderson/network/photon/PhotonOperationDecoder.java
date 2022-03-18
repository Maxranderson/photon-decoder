package com.maxranderson.network.photon;

import com.maxranderson.network.enet.ENetCommand;
import com.maxranderson.network.enet.ENetCommandHeader;
import com.maxranderson.network.enet.ENetProtocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class PhotonOperationDecoder {
    private ConcurrentHashMap<Integer, PhotonMessageFragmentEntry> cache;
    private static PhotonOperationDecoder instance;

    private PhotonOperationDecoder() {
        this.cache = new ConcurrentHashMap<>();
    }

    public static PhotonOperationDecoder getInstance() {
        if(instance == null) {
            instance = new PhotonOperationDecoder();
        }
        return instance;
    }

    private class PhotonMessageFragmentEntry {
        int sequenceNumber;
        int fragmentsNeeded;
        ConcurrentHashMap<Integer, PhotonMessageFragment> fragments;

        public PhotonMessageFragmentEntry(PhotonMessageFragment fragment) {
            this.sequenceNumber = fragment.getSequenceNumber();
            this.fragmentsNeeded = fragment.getFragmentCount();
            this.fragments = new ConcurrentHashMap<>();
            this.fragments.put(fragment.getFragmentNumber(), fragment);
        }

        boolean isFinished() {
            return fragments.size() == fragmentsNeeded;
        }

        PhotonMessageFragmentEntry add(PhotonMessageFragment fragment) {
            fragments.put(fragment.getFragmentNumber(), fragment);
            return this;
        }

        public ENetCommand build() {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            SortedSet<Integer> sortedKeys = new TreeSet<>(fragments.keySet());
            for (Integer key : sortedKeys) {
                try {
                    os.write(fragments.get(key).getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            byte[] data = os.toByteArray();
            ENetCommandHeader header = new ENetCommandHeader(ENetProtocol.SendReliableType, 0, 0, 0, data.length, this.sequenceNumber);
            return new ENetCommand(header, data);
        }
    }

    private PhotonMessageFragment decodeFragment(ENetCommand command) {
        if(command.getProtocol() != ENetProtocol.SendReliableFragmentType) {
            throw new IllegalArgumentException("Command cant' be decoded");
        }
        ByteBuffer buffer = ByteBuffer.wrap(command.getPayload());
        int sequenceNumber = buffer.getInt();
        int fragmentCount = buffer.getInt();
        int fragmentNumber = buffer.getInt();
        int totalLength = buffer.getInt();
        int fragmentOffset = buffer.getInt();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return new PhotonMessageFragment(sequenceNumber, fragmentCount, fragmentNumber, totalLength,fragmentOffset, data);
    }

    Optional<PhotonMessage> decode(ENetCommand command) {
        switch (command.getProtocol()) {
            case SendReliableType:
                return Optional.of(PhotonMessage.decode(command));
            case SendUnreliableType:
                ByteBuffer newPayloadBfr = ByteBuffer.wrap(new byte[command.getPayload().length - 4]);
                IntStream.range(4, command.getPayload().length)
                                .mapToObj(i -> command.getPayload()[i])
                                .forEach(newPayloadBfr::put);

                command.setPayload(newPayloadBfr.array());
                command.setProtocol(ENetProtocol.SendReliableType);
                return Optional.of(PhotonMessage.decode(command));
            case SendReliableFragmentType:
                PhotonMessageFragment fragment = decodeFragment(command);
                PhotonMessageFragmentEntry entry =
                    Optional.ofNullable(cache.get(fragment.getSequenceNumber()))
                        .map(e -> e.add(fragment))
                        .orElseGet(() -> new PhotonMessageFragmentEntry((fragment)));
                if(!entry.isFinished()) {
                    return Optional.empty();
                }
                return Optional.of(PhotonMessage.decode(entry.build()));
            default:
                return Optional.empty();
        }
    }
}
