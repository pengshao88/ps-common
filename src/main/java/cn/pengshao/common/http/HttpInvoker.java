package cn.pengshao.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/3/21 21:57
 */
public interface HttpInvoker {

    Logger LOGGER = LoggerFactory.getLogger(HttpInvoker.class);

    HttpInvoker Default = new OkHttpInvoker(3);


    String post(String requestString, String url);
    String get(String url);

    @SneakyThrows
    static <T> T httpGet(String url, Class<T> clazz) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.get(url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }

    @SneakyThrows
    static <T> T httpGet(String url, TypeReference<T> typeReference) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.get(url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, typeReference);
    }

    @SneakyThrows
    static <T> T httpPost(String requestString,String url, Class<T> clazz) {
        LOGGER.debug(" =====>>>>>> httpGet: " + url);
        String respJson = Default.post(requestString, url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, clazz);
    }

    @SneakyThrows
    static <T> T httpPost(String requestString,String url, TypeReference<T> typeReference) {
        LOGGER.debug(" =====>>>>>> httpPost: " + url);
        String respJson = Default.post(requestString, url);
        LOGGER.debug(" =====>>>>>> response: " + respJson);
        return JSON.parseObject(respJson, typeReference);
    }
}
