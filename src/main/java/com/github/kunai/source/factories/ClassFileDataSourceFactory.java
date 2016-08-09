package com.github.kunai.source.factories;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.kunai.entries.KunaiException;
import com.github.kunai.source.ClassFileDataSource;
import com.github.kunai.source.DataSource;

public class ClassFileDataSourceFactory implements DataSourceFactory{
    public ClassFileDataSourceFactory(){
    }

    @Override
    public boolean isTarget(Path path, BasicFileAttributes attributes){
        String name = path.toString();
        return name.endsWith(".class") && attributes.isRegularFile();
    }

    @Override
    public DataSource build(Path path) throws KunaiException{
        return new ClassFileDataSource(path);
    }
}