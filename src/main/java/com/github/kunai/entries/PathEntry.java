package com.github.kunai.entries;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

import com.github.kunai.source.DataSource;

public class PathEntry implements Entry{
    private Path path;
    private DataSource source;

    public PathEntry(Path path, DataSource source){
        this.path = path;
        this.source = source;
    }

    @Override
    public InputStream openStream() throws IOException{
        return path.toUri()
                .toURL().openStream();
    }

    public DataSource source(){
        return source;
    }

    @Override
    public ClassName className() {
        return ClassName.parse(path);
    }

    boolean startsWith(Path other){
        return path.startsWith(other);
    }

    @Override
    public boolean isName(Name name){
        return path.endsWith(name.toString());
    }

    @Override
    public String toString(){
        return path.toAbsolutePath().toString();
    }

    @Override
    public URI toUri(){
        return path.toUri();
    }
}
