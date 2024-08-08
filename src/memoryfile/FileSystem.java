package memoryfile;

import javax.naming.directory.DirContext;

public class FileSystem {

    private final Directory root;

    public FileSystem() {
        this.root = new Directory("/");
    }

    private Directory traverseToDirectory(Path path) throws Exception {
        String[] pathComponents = path.split();
        Directory current = root;
        for (int i = 1; i < pathComponents.length - 1; i++) {
            current = current.getDirectory(pathComponents[i]);
            if (current == null) {
                throw new Exception("GGGG");
            }
        }
        return current;
    }

    public void createFile(Path path) {
        Directory directory = traverseToDirectory(path);
        String[] pathComponents = path.split();
        File file = new File(pathComponents[pathComponents.length - 1]);
        directory.addFile(file);
    }

    public void writeFile(Path path, String content) {
        Directory directory = traverseToDirectory(path);
        String[] pathComponents = path.split();
        File file = directory.getFile(pathComponents[pathComponents.length - 1]);
        if (file == null) {

        }
        file.writeContent(content);
    }

    public String readFile(Path path) {
        Directory directory = traverseToDirectory(path);
        String[] pathComponents = path.split();
        File file = directory.getFile(pathComponents[pathComponents.length - 1]);
        if (file == null) {
            //异常处理
        }
        return file.readContent();
    }

    public void createDirectory(Path path) {
        Directory directory = traverseToDirectory(path);
        String[] pathComponents = path.split();
        Directory newDirectory = new Directory(pathComponents[pathComponents.length - 1]);
        directory.addDirectory(newDirectory);
    }

    public void deleteFile(Path path) {
        Directory directory = traverseToDirectory(path);
        String[] pathComponents = path.split();
        directory.removeDirectory(pathComponents[pathComponents.length - 1]);
    }


}
