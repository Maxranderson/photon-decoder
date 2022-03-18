package com.maxranderson.network.photon.parameter;

import java.util.Map;

public class MapParameter extends Parameter {
    private Map<Parameter, Parameter> value;

    public MapParameter(Map<Parameter, Parameter> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MapParameter{" +
                "value=" + value +
                '}';
    }
}
