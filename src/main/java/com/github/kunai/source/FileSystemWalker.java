package com.github.kunai.source;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileSystemWalker {
    public Stream<Path> walk(FileSystem system){
        return walk(system, system.provider());
    }

    public Stream<Path> walk(FileSystem system, FileSystemProvider provider){
        Spliterator<Path> spliterator = system.getRootDirectories().spliterator();
        List<Path> list = new ArrayList<>();
        stream(spliterator).forEach(path -> traverse(provider, path, list));
        return list.stream();
    }

    private void traverse(FileSystemProvider provider, Path path, List<Path> list){
        try(DirectoryStream<Path> stream = provider.newDirectoryStream(path, null)){
            stream.forEach(next -> traverseDirectory(provider, next, list));
        } catch(IOException e){ }
    }

    private void traverseDirectory(FileSystemProvider provider, Path path, List<Path> list){
        try{
            traverseDirectoryImpl(provider, path, list);
        } catch(IOException e){
        }
    }

    private void traverseDirectoryImpl(FileSystemProvider provider, Path path, List<Path> list) throws IOException{
        BasicFileAttributes attr = provider.readAttributes(path, BasicFileAttributes.class);
        if(attr.isDirectory()){
            traverse(provider, path, list);
            return;
        }
        list.add(path);
    }

    private <T> Stream<T> stream(Spliterator<T> spliterator){
        return StreamSupport.stream(spliterator, false);
    }
}
