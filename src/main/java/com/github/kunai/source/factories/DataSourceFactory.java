package com.github.kunai.source.factories;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import com.github.kunai.entries.KunaiException;
import com.github.kunai.source.DataSource;
import com.github.kunai.util.PathHelper;

public interface DataSourceFactory {
    boolean isTarget(Path path, FileSystem system, BasicFileAttributes attributes);

    default boolean isTarget(Path path, FileSystem system){
        Optional<BasicFileAttributes> attributes = PathHelper.readAttributes(path, system);
        return attributes.isPresent() && 
                isTarget(path, system, attributes.get());
    }

    default boolean isTarget(Path path){
        FileSystem system = FileSystems.getDefault();
        return isTarget(path, system);
    }

    DataSource build(Path path, FileSystem system) throws KunaiException;

    default DataSource build(Path path) throws KunaiException{
        FileSystem system = FileSystems.getDefault();
        return build(path, system);
    }

    default DataSource build(File file) throws KunaiException{
        return build(file.toPath());
    }
}
