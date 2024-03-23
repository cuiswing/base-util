package com.cui.base.util.http;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * restTemplate 处理数据 HttpResponse中的 body 转为对象时，只支持MediaType.APPLICATION_JSON,如果其他格式的响应，则不会处理；
 * 此处如果明确对方返回的数据是json，但是使用的 Content-type 不对，则手动添加一下支持的转换工具
 * 可将响应头为content-type:text/plain 的响应内容解析为 json对象。content-type:application/json
 * <p>
 * 参考：https://blog.csdn.net/kinginblue/article/details/52706155
 * <p>
 * MappingJackson2HttpMessageConverter ： 是 Spring 框架中用于将 HTTP 请求和响应转换为 JSON 的转换器。
 * 在处理 JSON 数据时，它会自动将字符串中的 Unicode 字符转换为 UTF-8 编码。
 * 比如接口中返回的有 unicode 数据，且使用了对象来接收，则会使用MappingJackson2HttpMessageConverter来进行反序列化；
 * 如果使用 String 类型来接收，则使用的是StringHttpMessageConverter来处理，则不会将 unicode 处理，保持了原样；
 *
 * @author CUI
 * @since 2021-05-16
 */
public class MyMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public MyMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
        setSupportedMediaTypes(mediaTypes);
    }
}
