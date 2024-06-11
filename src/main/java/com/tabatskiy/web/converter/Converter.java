package com.tabatskiy.web.converter;

public interface Converter<S, T> {
    T convert(S source);
}