package com.njxm.smart.api;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author huangxin
 * @date 2021/7/29
 */
public class ConvertFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter<>(type.getClass());
    }

    private static class MyConverter<M> implements Converter<ResponseBody, List<M>> {
        private final Class<M> clazz;

        public MyConverter(Class<M> clazz) {
            this.clazz = clazz;
        }

        @Override
        public List<M> convert(ResponseBody value) throws IOException {
            return JSON.parseArray(value.string(), clazz);
        }
    }
}
