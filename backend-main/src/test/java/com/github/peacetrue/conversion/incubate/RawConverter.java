package com.github.peacetrue.conversion.incubate;

import com.github.peacetrue.conversion.Formatter;
import com.github.peacetrue.conversion.FormatterRegistrar;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * @author peace
 **/
public class RawConverter implements Formatter<String> {

    @Override
    public String parse(String text, @Nullable String options) throws IOException {
        return text;
    }

    @Override
    public String print(String object, @Nullable String options) throws IOException {
        return object;
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter("raw", this);
    }
}
