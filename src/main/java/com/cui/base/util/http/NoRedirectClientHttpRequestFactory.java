package com.cui.base.util.http;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * 禁止响应码 302 时重定向
 *
 * @author CUI
 * @since 2020-10-16
 */
public class NoRedirectClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection,
                                     String httpMethod) throws IOException {
        super.prepareConnection(connection, httpMethod);
        connection.setInstanceFollowRedirects(false);
    }
}
