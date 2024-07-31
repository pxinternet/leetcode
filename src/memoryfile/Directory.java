package memoryfile;

import java.nio.file.FileAlreadyExistsException;

import org.xml.sax.HandlerBase;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private final String name;
    private final Map<String, Directory> directories;
    private final Map<String, File> files;

    public Directory(String name) {
        this.name = name;
        this.directories = new HashMap<>();
        this.file = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
    }

    public void addDirectory(Directory directory) {
        directories.put(directory.getName(), directory);
    }

    public void removeFile(String fileName) {
        files.remove(fileName);
    }

    public void removeDirectory(String directoryName) {
        directories.remove(directoryName);
    }

    public File getFile(String fileName) {
        return files.get(fileName);
    }

    public Directory getDirectory(String directoryName) {
        return directories.get(directoryName);
    }



}
