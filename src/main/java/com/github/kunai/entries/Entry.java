package com.github.kunai.entries;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface Entry {
    public InputStream openStream() throws IOException;

    public ClassName className();

    public boolean isName(Name name);

    public URI toUri();
}
