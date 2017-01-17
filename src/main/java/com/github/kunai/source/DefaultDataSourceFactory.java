package com.github.kunai.source;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.kunai.KunaiException;

public class DefaultDataSourceFactory extends DataSourceFactory{
    public DataSource build(Path path) throws KunaiException{
        String fileName = getFileName(path);
        if(Files.isDirectory(path))
            return new DirectoryDataSource(path);
        if(fileName.endsWith(".jar") || fileName.endsWith(".war"))
            return buildJar(path);
        if(fileName.endsWith(".class"))
            return new FileDataSource(path);
        throw new UnsupportedDataSourceException(path + ": not found");
    }

    private DataSource buildJar(Path path) throws KunaiException{
        try {
            ClassLoader loader = getClass().getClassLoader();
            FileSystem system = FileSystems.newFileSystem(path, loader);
            return new FileSystemDataSource(system);
        } catch (IOException e) {
            throw new KunaiException(e.getMessage());
        }
    }

    public DataSource build(File file) throws KunaiException{
        return build(file.toPath());
    }

    public DataSource build(URI uri) throws KunaiException{
        FileSystem system = FileSystems.getFileSystem(uri);

        return new FileSystemDataSource(system);
    }

    private String getFileName(Path path){
        return path.toString();
    }
}
