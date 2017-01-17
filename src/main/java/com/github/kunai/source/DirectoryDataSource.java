package com.github.kunai.source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.github.kunai.entries.Entry;
import com.github.kunai.entries.PathEntry;

public class DirectoryDataSource implements DataSource {
    private Path base;

    public DirectoryDataSource(Path base){
        this.base = base;
    }

    @Override
    public Stream<Entry> stream() {
        try{
            return Files.walk(base)
                .map(item -> new PathEntry(item, DirectoryDataSource.this));
        } catch(IOException e){}
        return Stream.empty();
    }

    @Override
    public void close() throws IOException{
    }
}
