package com.maxranderson.network.photon.parameter;

import java.util.Arrays;

public enum Type {
    NilType(42),
    DictionaryType(68),
    StringSliceType(97),
    Int8Type(98),
    Custom(99),
    DoubleType(100),
    EventDateType(101),
    Float32Type(102),
    Hashtable(104),
    Int32Type(105),
    Int16Type(107),
    Int64Type(108),
    Int32SliceType(110),
    BooleanType(111),
    OperationResponseType(112),
    OperationRequestType(113),
    StringType(115),
    Int8SliceType(120),
    SliceType(121),
    ObjectSliceType(122);
    private final int id;
    Type(int id) { this.id = id; }

    static Type valueOf(int id) {
        if(id == 0) {
            return NilType;
        }
        if(id == 7) {
            return Int16Type;
        }
        return Arrays.stream(values())
            .filter(e -> e.id == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not a valid id: " + id));
    }
}
