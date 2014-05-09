package org.apollo.fs;

import java.io.IOException;

public interface FileIndex {

    byte[] get(int file) throws IOException;

}