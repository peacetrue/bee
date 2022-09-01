package com.github.peacetrue.conversion;

import com.github.peacetrue.net.URLCodecUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * url 编解码格式化器。
 *
 * @author peace
 **/
public class URLCodecFormatter implements Formatter<String> {

    @Override
    public String parse(String text, @Nullable String options) throws IOException {
        return URLCodecUtils.encode(text);
    }

    @Override
    public String print(String object, @Nullable String options) throws IOException {
        return URLCodecUtils.decode(object);
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter("url-codec", this);
    }
}
