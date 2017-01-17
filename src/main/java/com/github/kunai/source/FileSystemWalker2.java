package com.github.kunai.source;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * なぜか traverse メソッド内で，サブディレクトリを traverse しない．
 * DirectoryStream を stream に変換しているから？
 * DirectoryStream を forEach するとそれぞれを見るみたい．
 * 
 * I do not understand the reason that the directories were not traversed in traverse method.
 * 
 * 
 * @author Haruaki Tamada
 */
public class FileSystemWalker2 {
    public Stream<Path> walk(FileSystem system){
        return walk(system, system.provider());
    }

    public Stream<Path> walk(FileSystem system, FileSystemProvider provider){
        Spliterator<Path> spliterator = system.getRootDirectories().spliterator();
        return stream(spliterator)
                .flatMap(path -> traverse(provider, path));
    }

    private Stream<Path> traverse(FileSystemProvider provider, Path path){
        try(DirectoryStream<Path> stream = provider.newDirectoryStream(path, null)){
            return stream(stream.spliterator())
                    .flatMap(next -> traverseDirectory(provider, next));
        } catch(IOException e){ }
        return Stream.empty();
    }

    private Stream<Path> traverseDirectory(FileSystemProvider provider, Path path){
        try{
            return traverseDirectoryImpl(provider, path);
        } catch(IOException e){
            return Stream.empty();
        }
    }

    private Stream<Path> traverseDirectoryImpl(FileSystemProvider provider, Path path) throws IOException{
        BasicFileAttributes attr = provider.readAttributes(path, BasicFileAttributes.class);
        if(attr.isDirectory())
            return traverse(provider, path);
        return Stream.of(path);
    }

    private <T> Stream<T> stream(Spliterator<T> spliterator){
        return StreamSupport.stream(spliterator, false);
    }
}
