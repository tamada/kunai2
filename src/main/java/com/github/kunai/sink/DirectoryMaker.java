package com.github.kunai.sink;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import com.github.kunai.util.Exceptions;
import com.github.kunai.util.PathHelper;

public class DirectoryMaker {
    public static void mkdirs(Path givenPath, FileSystem givenSystem){
        Exceptions.isThrowed(givenPath, givenSystem, 
                (path, system) -> mkdirsImpl(path, system));
    }

    private static void mkdirsImpl(Path path, FileSystem system) throws IOException{
        if(path == null || PathHelper.exists(path, system)) return;
        mkdirsImpl(path.getParent(), system);
        system.provider().createDirectory(path);
    }
}
