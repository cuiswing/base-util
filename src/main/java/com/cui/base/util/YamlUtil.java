package com.cui.base.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * yaml文件解析工具类
 *
 * @author CUI
 * @since 2021-04-06
 */
public class YamlUtil {

    /**
     * 加载配置信息
     *
     * @param configFile 配置文件,需要位于 classpath 路径下
     */
    public static <T> T loadConfig(String configFile, Class<T> tClass) {
        InputStream inputStream = YamlUtil.class.getResourceAsStream(configFile);
        T config = new Yaml().loadAs(inputStream, tClass);
        return config;
    }
}
