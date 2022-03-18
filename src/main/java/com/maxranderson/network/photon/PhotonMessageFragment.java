package com.maxranderson.network.photon;

import java.util.Arrays;

public class PhotonMessageFragment {
    private int sequenceNumber;
    private int fragmentCount;
    private int fragmentNumber;
    private int totalLength;
    private int fragmentOffset;
    private byte[] data;

    public PhotonMessageFragment(int sequenceNumber, int fragmentCount, int fragmentNumber, int totalLength, int fragmentOffset, byte[] data) {
        this.sequenceNumber = sequenceNumber;
        this.fragmentCount = fragmentCount;
        this.fragmentNumber = fragmentNumber;
        this.totalLength = totalLength;
        this.fragmentOffset = fragmentOffset;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PhotonMessageFragment{" +
                "sequenceNumber=" + sequenceNumber +
                ", fragmentCount=" + fragmentCount +
                ", fragmentNumber=" + fragmentNumber +
                ", totalLength=" + totalLength +
                ", fragmentOffset=" + fragmentOffset +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public int getFragmentCount() {
        return fragmentCount;
    }

    public int getFragmentNumber() {
        return fragmentNumber;
    }

    public byte[] getData() {
        return data;
    }
}
