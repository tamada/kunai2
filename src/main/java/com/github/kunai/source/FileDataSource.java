package com.github.kunai.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.github.kunai.entries.Entry;
import com.github.kunai.entries.PathEntry;

public class FileDataSource implements DataSource{
    private PathEntry entry;

    public FileDataSource(Path path){
        this.entry = new PathEntry(path, this);
    }

    @Override
    public Stream<Entry> stream() {
        return Stream.of(entry);
    }

    @Override
    public void close(){
    }

    public InputStream openStream(Entry otherEntry) throws IOException {
        if(entry == otherEntry)
            return entry.openStream();
        throw new IOException("no in this source");
    }
}
