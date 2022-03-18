package com.maxranderson.network.photon.parameter;

import com.maxranderson.network.photon.PhotonMessage;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class Parameter {

    public static Optional<Parameter> decode(Type paramType, ByteBuffer buffer) {
        switch (paramType) {
            case NilType:
                return Optional.of(new EmptyParameter());
            case Int8Type:
                return Optional.of(new ByteParameter(buffer.get()));
            case Int16Type:
                return Optional.of(new ShortParameter(buffer.getShort()));
            case Float32Type:
                return Optional.of(new FloatParameter(buffer.getFloat()));
            case Int32Type:
                return Optional.of(new IntParameter(buffer.getInt()));
            case Int64Type:
                return Optional.of(new LongParameter(buffer.getLong()));
            case BooleanType:
                return Optional.of(new BooleanParameter(buffer.get() == 1));
            case Int8SliceType:
                int length = buffer.getInt();
                byte[] value = new byte[length];
                buffer.get(value);
                return Optional.of(new ByteSeqParameter(value));
            case SliceType:
                short sliceLength = buffer.getShort();
                Type sliceType = Type.valueOf(buffer.get());
                Parameter[] sliceValue = new Parameter[sliceLength];
                IntStream.range(0, sliceLength)
                        .forEach(i -> sliceValue[i] = decode(sliceType, buffer).orElseThrow());
                return Optional.of(new SeqParameter(sliceValue));
            case DictionaryType:
                Type keyType = Type.valueOf(buffer.get());
                Type valueType = Type.valueOf(buffer.get());
                short dictionaryLength = buffer.getShort();
                HashMap<Parameter, Parameter> dictionary = new HashMap<>();
                IntStream.range(1, dictionaryLength + 1)
                    .forEach(i ->
                        dictionary.put(
                            decode(keyType, buffer).orElseThrow(),
                            decode(valueType, buffer).orElseThrow()
                        )
                    );
                return Optional.of(new MapParameter(dictionary));
            case StringType:
                short strLength = buffer.getShort();
                byte[] strArr = new byte[strLength];
                buffer.get(strArr);
                return Optional.of(new StringParameter(new String(strArr, StandardCharsets.UTF_8)));
            default:
                return Optional.empty();
        }
    }

    public static Map<Integer, Parameter> decode(PhotonMessage message) {
        ByteBuffer buffer = ByteBuffer.wrap(message.getData());
        HashMap<Integer, Parameter> parameters = new HashMap<>();

        IntStream.range(1, message.getParameterCount() + 1)
            .forEach(i -> {
                int id = Byte.toUnsignedInt(buffer.get());
                Type paramType = Type.valueOf(Byte.toUnsignedInt(buffer.get()));
                decode(paramType, buffer).ifPresent(parameter -> {
                    parameters.put(id, parameter);
                });
            });

        return parameters;
    }
}

