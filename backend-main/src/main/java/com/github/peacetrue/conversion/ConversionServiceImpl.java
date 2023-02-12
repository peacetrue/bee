package com.github.peacetrue.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.util.*;

/**
 * 转换服务实现。
 *
 * @author peace
 **/
@Slf4j
public class ConversionServiceImpl implements ConversionService, FormatterRegistrar {

    private final Map<String, Formatter<?>> formatters = new LinkedHashMap<>();

    @Override
    public void registerFormatter(String format, Formatter<?> formatter) {
        formatters.put(format, formatter);
        log.debug("registered formatter: {} -> {}", format, formatter);
    }

    @Override
    public List<String> getFormats() {
        return new LinkedList<>(formatters.keySet());
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String convert(String sourceContent, FormatWrapper sourceFormat, FormatWrapper targetFormat) throws IOException {
        String localSourceFormat = sourceFormat.getFormat();
        String localTargetFormat = targetFormat.getFormat();
        log.info("convert source content from {} to {}:\n{}", localSourceFormat, localTargetFormat, sourceContent);

        Formatter<?> sourceFormatter = formatters.get(localSourceFormat);
        log.debug("got source Formatter: {}", sourceFormatter);
        if (sourceFormatter == null)
            throw new IllegalStateException("Can't found Formatter which supports " + localSourceFormat);

        Formatter<Object> targetFormatter = (Formatter) formatters.get(localTargetFormat);
        log.debug("got target Formatter: {}", targetFormatter);
        if (targetFormatter == null)
            throw new IllegalStateException("Can't found Formatter which supports " + localTargetFormat);

        Class<?> sourceType = resolveType(sourceFormatter.getClass());
        Class<?> targetType = resolveType(targetFormatter.getClass());
        if (!targetType.isAssignableFrom(sourceType)) {
            throw new IllegalStateException(String.format(
                    "Can't assign source object type %s to target object type %s",
                    sourceType.getName(), targetType.getName()
            ));
        }

        Object object = sourceFormatter.parse(sourceContent, sourceFormat.getOptions());
        log.debug("got memory object: {}", object);

        return targetFormatter.print(object, targetFormat.getOptions());
    }

    private static Class<?> resolveType(Class<?> clazz) {
        return Objects.requireNonNull(
                ResolvableType.forClass(Formatter.class, clazz).resolveGeneric(0)
        );
    }

}
