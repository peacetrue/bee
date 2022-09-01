package com.github.peacetrue.conversion;

import com.github.peacetrue.beanmap.BeanMap;
import com.github.peacetrue.beanmap.BeanMapUtils;
import com.github.peacetrue.net.URLCodecUtils;
import com.github.peacetrue.net.URLQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 表单格式化器。
 *
 * @author peace
 **/
public class FormUrlEncodedFormatter implements Formatter<BeanMap> {

    @Override
    public BeanMap parse(String text, @Nullable String options) {
        text = text.replaceAll("[\n\r\t]", "");
        Map<String, List<String>> params = URLQueryUtils.parseQuery(text);
        return new BeanMap(BeanMapUtils.tier(params));
    }

    @Override
    public String print(BeanMap object, @Nullable String options) {
        Map<String, List<String>> flatten = URLQueryUtils.flatten(object);
        return URLCodecUtils.decode(URLQueryUtils.toQuery(flatten)).replace("&", "\n&");
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter("x-www-form-urlencoded", this);
    }

}
