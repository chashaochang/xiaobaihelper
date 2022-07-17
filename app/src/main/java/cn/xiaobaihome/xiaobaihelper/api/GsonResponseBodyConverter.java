package cn.xiaobaihome.xiaobaihelper.api;

import static cn.xiaobaihome.xiaobaihelper.helper.UtilsKt.getResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = getResponseBody(value);
            if (response.startsWith("{")) {
                JsonReader jsonReader = gson.newJsonReader(value.charStream());

                T result = adapter.read(jsonReader);
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
                return result;

            } else {//网络请求返回值是html的直接返回字符串
                return (T) response;
            }
        } catch (Exception e) {
            return null;
        } finally {
            value.close();
        }
    }
}
