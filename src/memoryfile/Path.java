package memoryfile;

public class Path {

    private final String path;

    public Path(String path) {
        if (path == null || path.isEmpty() || !path.startsWith("/")) {
            throw new IllegalArgumentException("Invalid path");
        }
        this.path = path;
    }

    public String[] split() {
        return path.split("/");
    }

    public String getPath() {
        return path;
    }

    //重写一下  equals 和 hashcode



}
