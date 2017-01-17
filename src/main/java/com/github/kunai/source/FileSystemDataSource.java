package com.github.kunai.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.github.kunai.entries.Entry;
import com.github.kunai.entries.PathEntry;

public class FileSystemDataSource implements DataSource{
    private FileSystem system;

    public FileSystemDataSource(FileSystem system){
        this.system = system;
    }

    @Override
    public Stream<Entry> stream(){
        return new FileSystemWalker().walk(system)
                .map(path -> convertToEntry(path));
    }

    public InputStream openStream(Entry entry) throws IOException{
        return entry.toUri()
                .toURL().openStream();
    }

    private Entry convertToEntry(Path path){
        return new PathEntry(path, this);
    }

    @Override
    public void close() throws IOException{
        system.close();
    }
}
