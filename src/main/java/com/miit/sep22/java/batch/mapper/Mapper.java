package com.miit.sep22.java.batch.mapper;


public interface Mapper<T, R> {
    T map(R record);
}
