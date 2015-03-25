package com.nomad.internethaber.helper;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

public class LoggerConverter extends GsonConverter {

    public LoggerConverter(Gson gson) {
        super(gson);
    }

    public LoggerConverter(Gson gson, String charset) {
        super(gson, charset);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            String response = toString(body);
            Logger.json(response);
            body = new JsonTypedInput(response.getBytes());
        } catch (IOException e) {
            Logger.e(e);
        }

        return super.fromBody(body, type);
    }

    private String toString(TypedInput body) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(body.in()));
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
        } finally {
            if (bufferedReader != null)
                bufferedReader.close();
        }

        return stringBuilder.toString();
    }

    private static class JsonTypedInput implements TypedInput {

        private final byte[] mStringBytes;

        JsonTypedInput(byte[] stringBytes) {
            this.mStringBytes = stringBytes;
        }

        @Override
        public String mimeType() {
            return "application/json; charset=UTF-8";
        }

        @Override
        public long length() {
            return mStringBytes.length;
        }

        @Override
        public InputStream in() throws IOException {
            return new ByteArrayInputStream(mStringBytes);
        }
    }
}
