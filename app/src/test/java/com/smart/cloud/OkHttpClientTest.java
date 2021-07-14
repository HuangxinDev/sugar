package com.smart.cloud;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientTest {
    /**
     * 测试服务端的网络的连通性
     */
    @Test
    public void testRequest() {
        long startMemory = Runtime.getRuntime().freeMemory();
        try {
            Request request = new Request.Builder().url("https://www.baidu.com").build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            Assert.assertEquals(200, response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endMemory = Runtime.getRuntime().freeMemory();
        System.out.println("消耗的内容：" + (startMemory - endMemory) / 1024);
    }

    @Test
    public void testAdd() {
        Assert.assertEquals(2, 1 + 1);
    }

    @Test
    public void testCreateLog() {
        // 测试框架，mock技巧
    }

    @Test
    public void testNumber() {
        System.out.println(test(10, 22));
    }

    public long test(int x, int y) {
        long result = 1;
        for (int i = 0; i < y; i++) {
            result *= x;
        }
        return result;
    }

    /**
     * 使用OKHttp3上传多个文件
     */
    @Test
    public void uploadMultiFile() throws IOException {
//        MultipartBody body = new MultipartBody.Builder().build();
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://www.baidu.com")
//                .post(body)
//                .build();
//        client.newCall(request).execute();
    }
}
