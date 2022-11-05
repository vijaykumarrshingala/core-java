package com.miit.sep22.java.batch.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileReader<R> {
    R read(String name) throws IOException;
}
