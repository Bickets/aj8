package org.apollo.fs;

import java.io.IOException;

@FunctionalInterface
public interface FileIndex {

    byte[] get(int file) throws IOException;

}