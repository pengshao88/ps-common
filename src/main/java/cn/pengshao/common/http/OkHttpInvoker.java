package cn.pengshao.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * Description: Interface for http invoker
 *
 * @Author: yezp
 * @date 2024/3/21 21:58
 */
@Slf4j
public class OkHttpInvoker implements HttpInvoker {

    final OkHttpClient okHttpClient;

    /**
     * OkHttpInvoker
     *
     * @param timeout 超时时间 TimeUnit.SECONDS
     */
    public OkHttpInvoker(int timeout) {
        okHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(16, 60, TimeUnit.SECONDS))
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    final static MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    @Override
    public String post(String requestString, String url) {
        log.debug(" ===> post  url = {}, requestString = {}", requestString, url);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestString, JSON_TYPE))
                .build();
        try {
            String respJson = okHttpClient.newCall(request).execute().body().string();
            log.debug(" ===> respJson = " + respJson);
            return respJson;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String url) {
        log.debug(" ===> get url = " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            String respJson = okHttpClient.newCall(request).execute().body().string();
            log.debug(" ===> respJson = " + respJson);
            return respJson;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public <T> T httpGet(String url, Class<T> clazz) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = get(url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }

    @SneakyThrows
    public <T> T httpGet(String url, TypeReference<T> typeReference) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = get(url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, typeReference);
    }

    @SneakyThrows
    public <T> T httpPost(String requestString,String url, Class<T> clazz) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = post(requestString, url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }
}
