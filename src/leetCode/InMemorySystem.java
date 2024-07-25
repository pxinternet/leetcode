package leetCode;

import java.util.HashMap;

import java.lang.reflect.InaccessibleObjectException;

import com.sun.source.doctree.ReturnTree;

public class InMemorySystem {

}


class InMemoryFile {
    private String name;
    private StringBuilder content;
    private long createdTime;
    private long modifiedTime;
    private String permission;

    public InMemoryFile(String name) {
        this.name = name;
        this.content = new StringBuilder();
        this.createdTime = System.currentTimeMillis();
        this.modifiedTime = this.createdTime;
        this.permission = "rw";
    }

    public void writeContent(String content) {
        this.content.append(content);
        this.modifiedTime = System.currentTimeMillis();
    }

    public void clearContent() {
        this.content.setLength(0);
        this.modifiedTime = System.currentTimeMillis();
    }
 }

 class InMemeoryDirectory {
    private String name;
    private Map<String, InMemeoryDirectory> directories;
    private Map<String ,InMemoryFile> files;

    private long createdTime;
    private long modifiedTime;

    private String permissions;

    public InMemeoryDirectory(String name) {
        this.name = name;
        this.directories = new HashMap<>();
        this.files = new HashMap<>();

        this.createdTime = System.currentTimeMillis();
        this.modifiedTime = createdTime;
        this.permissions = "rw";
    }

    publci void addFile(InMemeoryFile file) {
        files.put(file.getName(), file);
        this.modifiedTime = System.currentTimeMillis();
    }

    public void addDirectory(InMemeoryDirectory directory) {
        directories.put(directory.getName(), directory);
        this.modifiedTime = System.currentTimeMillis();

    }

 }

 class InMemoryFileSystem {
    private InMemeoryDirectory root;

    public InMemoryFileSystem() {
        this.root = new InMemeoryDirectory("/");
    }

    public InMemeoryDirectory getRoot() {
        return root;
    }

    private String[] splitPath(String path) {
        return path.split("/");
    }

    private InMemeoryDirectory traverseToDirectory(Stirng[] pathComponents) {
        InMemeoryDirectory current = root;

        for (int i = 1; i < pathComponents.length - 1; i++) {
            current = current.getDirectories().get(pathComponents[i]);
            if (current == null) {
                throw new Exception();
            }
        }
        return current;
    }

    public void createFile(String path) {
        String[] pathComponents = splitPath(path);
        InMemeoryDirectory directory = traverseToDirectory(pathComponents);
        InMemoryFile file = new InMemoryFile(pathComponents[pathComponents.length - 1]);
        directory.addFile(file);
    }

    public void writeFile(String path, String content) {
        String[] pathComponents = splitPath(path);
        InMemeoryDirectory directory = traverseToDirectory(pathComponents)
    }
 }