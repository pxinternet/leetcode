package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InMemorySystem - 内存文件系统实现
 *
 * 题目（概要）：设计并实现一个内存文件系统，支持文件和目录的创建、读取、写入等基本操作。
 * 所有数据存储在内存中，不涉及磁盘 I/O。
 *
 * 设计说明：
 * - 使用树形结构组织文件和目录，根目录为 "/"
 * - InMemoryFile：表示文件，包含名称、内容、创建时间、修改时间、权限等属性
 * - InMemoryDirectory：表示目录，包含子目录和文件的映射
 * - InMemoryFileSystem：文件系统主类，提供文件系统操作的 API
 *
 * 核心功能：
 * - createFile(path): 创建文件（如果目录不存在会自动创建）
 * - writeFile(path, content): 向文件写入内容（追加模式）
 * - readFile(path): 读取文件内容
 * - deleteFile(path): 删除文件
 * - createDirectory(path): 创建目录
 * - deleteDirectory(path): 删除目录（递归删除）
 * - listDirectory(path): 列出目录内容
 * - fileExists(path): 检查文件是否存在
 * - directoryExists(path): 检查目录是否存在
 *
 * 路径处理：
 * - 路径格式："/path/to/file" 或 "/path/to/directory"
 * - 路径分割：使用 "/" 作为分隔符
 * - 路径验证：确保路径以 "/" 开头，且不包含空字符串组件
 *
 * 时间复杂度：
 * - createFile/writeFile/readFile: O(n)，n 为路径深度
 * - deleteFile/deleteDirectory: O(n)
 * - listDirectory: O(m)，m 为目录中的文件和子目录数量
 *
 * 空间复杂度：O(F + D)，F 为文件数量，D 为目录数量
 *
 * 边界与注意事项：
 * - 路径必须以 "/" 开头
 * - 创建文件时，如果父目录不存在，会自动创建
 * - 删除目录时，如果目录非空，会递归删除所有内容
 * - 文件操作（读写）会更新文件的修改时间
 * - 所有操作都是线程不安全的（如需线程安全，需要添加同步机制）
 */
public class InMemorySystem {

    /**
     * InMemoryFile - 内存文件类
     *
     * 表示文件系统中的文件，包含以下属性：
     * - name: 文件名
     * - content: 文件内容（使用 StringBuilder 支持高效追加）
     * - createdTime: 创建时间（毫秒时间戳）
     * - modifiedTime: 最后修改时间（毫秒时间戳）
     * - permission: 文件权限（默认 "rw" 表示可读可写）
     */
    public static class InMemoryFile {
        private String name;
        private StringBuilder content;
        private long createdTime;
        private long modifiedTime;
        private String permission;

        /**
         * 构造一个内存文件
         *
         * @param name 文件名，不能为 null 或空字符串
         */
        public InMemoryFile(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("File name cannot be null or empty");
            }
            this.name = name;
            this.content = new StringBuilder();
            this.createdTime = System.currentTimeMillis();
            this.modifiedTime = this.createdTime;
            this.permission = "rw"; // 默认权限：可读可写
        }

        /**
         * 向文件追加内容
         *
         * 关键点说明：
         * - 使用 append 方法追加内容，而不是替换
         * - 每次写入都会更新修改时间
         *
         * @param content 要追加的内容
         */
        public void writeContent(String content) {
            if (content != null) {
                this.content.append(content);
                this.modifiedTime = System.currentTimeMillis();
            }
        }

        /**
         * 替换文件内容（先清空再写入）
         *
         * @param content 新的文件内容
         */
        public void setContent(String content) {
            this.content.setLength(0);
            if (content != null) {
                this.content.append(content);
            }
            this.modifiedTime = System.currentTimeMillis();
        }

        /**
         * 清空文件内容
         */
        public void clearContent() {
            this.content.setLength(0);
            this.modifiedTime = System.currentTimeMillis();
        }

        /**
         * 读取文件内容
         *
         * @return 文件内容的字符串表示
         */
        public String readContent() {
            return this.content.toString();
        }

        // Getter 方法
        public String getName() {
            return name;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public long getModifiedTime() {
            return modifiedTime;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        /**
         * 获取文件大小（字符数）
         *
         * @return 文件内容长度
         */
        public int getSize() {
            return this.content.length();
        }
    }

    /**
     * InMemoryDirectory - 内存目录类
     *
     * 表示文件系统中的目录，包含以下属性：
     * - name: 目录名
     * - directories: 子目录映射（目录名 -> 目录对象）
     * - files: 文件映射（文件名 -> 文件对象）
     * - createdTime: 创建时间
     * - modifiedTime: 最后修改时间
     * - permissions: 目录权限（默认 "rw"）
     */
    public static class InMemoryDirectory {
        private String name;
        private Map<String, InMemoryDirectory> directories;
        private Map<String, InMemoryFile> files;
        private long createdTime;
        private long modifiedTime;
        private String permissions;

        /**
         * 构造一个内存目录
         *
         * @param name 目录名，不能为 null 或空字符串
         */
        public InMemoryDirectory(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Directory name cannot be null or empty");
            }
            this.name = name;
            this.directories = new HashMap<>();
            this.files = new HashMap<>();
            this.createdTime = System.currentTimeMillis();
            this.modifiedTime = this.createdTime;
            this.permissions = "rw"; // 默认权限：可读可写
        }

        /**
         * 添加文件到当前目录
         *
         * 关键点说明：
         * - 如果同名文件已存在，会被替换
         * - 添加文件会更新目录的修改时间
         *
         * @param file 要添加的文件对象
         */
        public void addFile(InMemoryFile file) {
            if (file == null) {
                throw new IllegalArgumentException("File cannot be null");
            }
            files.put(file.getName(), file);
            this.modifiedTime = System.currentTimeMillis();
        }

        /**
         * 添加子目录到当前目录
         *
         * @param directory 要添加的子目录对象
         */
        public void addDirectory(InMemoryDirectory directory) {
            if (directory == null) {
                throw new IllegalArgumentException("Directory cannot be null");
            }
            directories.put(directory.getName(), directory);
            this.modifiedTime = System.currentTimeMillis();
        }

        /**
         * 获取文件
         *
         * @param fileName 文件名
         * @return 文件对象，如果不存在返回 null
         */
        public InMemoryFile getFile(String fileName) {
            return files.get(fileName);
        }

        /**
         * 获取子目录
         *
         * @param dirName 子目录名
         * @return 子目录对象，如果不存在返回 null
         */
        public InMemoryDirectory getDirectory(String dirName) {
            return directories.get(dirName);
        }

        /**
         * 删除文件
         *
         * @param fileName 文件名
         * @return 如果文件存在并成功删除返回 true，否则返回 false
         */
        public boolean removeFile(String fileName) {
            boolean removed = files.remove(fileName) != null;
            if (removed) {
                this.modifiedTime = System.currentTimeMillis();
            }
            return removed;
        }

        /**
         * 删除子目录
         *
         * @param dirName 子目录名
         * @return 如果目录存在并成功删除返回 true，否则返回 false
         */
        public boolean removeDirectory(String dirName) {
            boolean removed = directories.remove(dirName) != null;
            if (removed) {
                this.modifiedTime = System.currentTimeMillis();
            }
            return removed;
        }

        /**
         * 检查文件是否存在
         *
         * @param fileName 文件名
         * @return 如果文件存在返回 true，否则返回 false
         */
        public boolean hasFile(String fileName) {
            return files.containsKey(fileName);
        }

        /**
         * 检查子目录是否存在
         *
         * @param dirName 子目录名
         * @return 如果目录存在返回 true，否则返回 false
         */
        public boolean hasDirectory(String dirName) {
            return directories.containsKey(dirName);
        }

        // Getter 方法
        public String getName() {
            return name;
        }

        public Map<String, InMemoryDirectory> getDirectories() {
            return directories;
        }

        public Map<String, InMemoryFile> getFiles() {
            return files;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public long getModifiedTime() {
            return modifiedTime;
        }

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        /**
         * 检查目录是否为空
         *
         * @return 如果目录中没有文件和子目录返回 true，否则返回 false
         */
        public boolean isEmpty() {
            return directories.isEmpty() && files.isEmpty();
        }
    }

    /**
     * InMemoryFileSystem - 内存文件系统主类
     *
     * 提供文件系统的核心功能，包括文件和目录的创建、读取、写入、删除等操作。
     * 使用树形结构组织文件系统，根目录为 "/"。
     */
    public static class InMemoryFileSystem {
        private final InMemoryDirectory root;

        /**
         * 构造一个内存文件系统
         *
         * 初始化根目录 "/"
         */
        public InMemoryFileSystem() {
            this.root = new InMemoryDirectory("/");
        }

        /**
         * 获取根目录
         *
         * @return 根目录对象
         */
        public InMemoryDirectory getRoot() {
            return root;
        }

        /**
         * 分割路径为组件数组
         *
         * 关键点说明：
         * - 路径必须以 "/" 开头
         * - 使用 "/" 分割路径，过滤空字符串
         * - 例如："/a/b/c" -> ["a", "b", "c"]
         *
         * @param path 文件或目录路径
         * @return 路径组件数组
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        private String[] splitPath(String path) {
            if (path == null || !path.startsWith("/")) {
                throw new IllegalArgumentException("Path must start with '/'");
            }
            // 分割路径并过滤空字符串
            return Arrays.stream(path.split("/"))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
        }

        /**
         * 遍历到指定路径的父目录
         *
         * 关键点说明：
         * - 从根目录开始，按照路径组件逐级向下遍历
         * - 如果路径中的任何目录不存在，会抛出异常
         * - 返回的是目标文件/目录的父目录
         *
         * @param pathComponents 路径组件数组
         * @return 父目录对象
         * @throws IllegalArgumentException 如果路径中的任何目录不存在
         */
        private InMemoryDirectory traverseToParentDirectory(String[] pathComponents) {
            InMemoryDirectory current = root;

            // 遍历所有组件
            for (String component : pathComponents) {
                if (component == null || component.isEmpty()) {
                    continue;
                }

                InMemoryDirectory next = current.getDirectory(component);
                if (next == null) {
                    throw new IllegalArgumentException(
                            "Directory does not exist: /" + String.join("/", pathComponents));
                }
                current = next;
            }
            return current;
        }

        /**
         * 遍历到指定路径的目录（如果不存在则创建）
         *
         * 关键点说明：
         * - 如果路径中的目录不存在，会自动创建
         * - 用于支持创建文件时自动创建父目录的功能
         *
         * @param pathComponents 路径组件数组
         * @return 目标目录对象
         */
        private InMemoryDirectory traverseToDirectoryOrCreate(String[] pathComponents) {
            InMemoryDirectory current = root;

            // 遍历所有组件，如果不存在则创建
            for (String component : pathComponents) {
                if (component == null || component.isEmpty()) {
                    continue;
                }

                InMemoryDirectory next = current.getDirectory(component);
                if (next == null) {
                    // 目录不存在，创建它
                    next = new InMemoryDirectory(component);
                    current.addDirectory(next);
                }
                current = next;
            }
            return current;
        }

        /**
         * 创建文件
         *
         * 关键点说明：
         * - 如果父目录不存在，会自动创建
         * - 如果文件已存在，会被替换（创建新文件对象）
         *
         * @param path 文件路径，例如 "/a/b/file.txt"
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        public void createFile(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                throw new IllegalArgumentException("Cannot create file at root");
            }

            // 获取父目录（如果不存在则创建）
            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir = traverseToDirectoryOrCreate(parentPath);

            // 创建文件
            String fileName = pathComponents[pathComponents.length - 1];
            InMemoryFile file = new InMemoryFile(fileName);
            parentDir.addFile(file);
        }

        /**
         * 向文件写入内容（追加模式）
         *
         * 关键点说明：
         * - 如果文件不存在，会先创建文件
         * - 内容以追加方式写入，不会覆盖原有内容
         *
         * @param path    文件路径
         * @param content 要写入的内容
         * @throws IllegalArgumentException 如果路径格式不正确或路径指向目录
         */
        public void writeFile(String path, String content) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                throw new IllegalArgumentException("Cannot write to root");
            }

            // 获取父目录（如果不存在则创建）
            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir = traverseToDirectoryOrCreate(parentPath);

            // 获取或创建文件
            String fileName = pathComponents[pathComponents.length - 1];
            InMemoryFile file = parentDir.getFile(fileName);
            if (file == null) {
                // 文件不存在，创建它
                file = new InMemoryFile(fileName);
                parentDir.addFile(file);
            }

            // 写入内容（追加模式）
            file.writeContent(content);
        }

        /**
         * 读取文件内容
         *
         * @param path 文件路径
         * @return 文件内容，如果文件不存在返回 null
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        public String readFile(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                throw new IllegalArgumentException("Cannot read from root");
            }

            // 获取父目录
            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir;
            try {
                parentDir = traverseToParentDirectory(parentPath);
            } catch (IllegalArgumentException e) {
                return null; // 父目录不存在，文件也不存在
            }

            // 获取文件
            String fileName = pathComponents[pathComponents.length - 1];
            InMemoryFile file = parentDir.getFile(fileName);
            if (file == null) {
                return null; // 文件不存在
            }

            return file.readContent();
        }

        /**
         * 删除文件
         *
         * @param path 文件路径
         * @return 如果文件存在并成功删除返回 true，否则返回 false
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        public boolean deleteFile(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                throw new IllegalArgumentException("Cannot delete root");
            }

            // 获取父目录
            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir;
            try {
                parentDir = traverseToParentDirectory(parentPath);
            } catch (IllegalArgumentException e) {
                return false; // 父目录不存在，文件也不存在
            }

            // 删除文件
            String fileName = pathComponents[pathComponents.length - 1];
            return parentDir.removeFile(fileName);
        }

        /**
         * 创建目录
         *
         * 关键点说明：
         * - 如果父目录不存在，会自动创建
         * - 如果目录已存在，不会重复创建（不会抛出异常）
         *
         * @param path 目录路径
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        public void createDirectory(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                return; // 根目录已存在
            }

            // 遍历到目录位置（如果不存在则创建）
            traverseToDirectoryOrCreate(pathComponents);
        }

        /**
         * 删除目录（递归删除）
         *
         * 关键点说明：
         * - 如果目录非空，会递归删除所有子目录和文件
         * - 不能删除根目录
         *
         * @param path 目录路径
         * @return 如果目录存在并成功删除返回 true，否则返回 false
         * @throws IllegalArgumentException 如果路径格式不正确或尝试删除根目录
         */
        public boolean deleteDirectory(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                throw new IllegalArgumentException("Cannot delete root directory");
            }

            // 获取父目录
            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir;
            try {
                parentDir = traverseToParentDirectory(parentPath);
            } catch (IllegalArgumentException e) {
                return false; // 父目录不存在，目标目录也不存在
            }

            // 删除目录
            String dirName = pathComponents[pathComponents.length - 1];
            return parentDir.removeDirectory(dirName);
        }

        /**
         * 列出目录内容
         *
         * @param path 目录路径
         * @return 包含子目录名和文件名的列表，如果目录不存在返回 null
         * @throws IllegalArgumentException 如果路径格式不正确
         */
        public List<String> listDirectory(String path) {
            String[] pathComponents = splitPath(path);
            InMemoryDirectory dir;

            if (pathComponents.length == 0) {
                // 列出根目录
                dir = root;
            } else {
                // 获取目标目录
                try {
                    InMemoryDirectory parentDir = traverseToParentDirectory(
                            Arrays.copyOf(pathComponents, pathComponents.length - 1));
                    String dirName = pathComponents[pathComponents.length - 1];
                    dir = parentDir.getDirectory(dirName);
                    if (dir == null) {
                        return null; // 目录不存在
                    }
                } catch (IllegalArgumentException e) {
                    return null; // 路径中的某个目录不存在
                }
            }

            // 收集所有子目录名和文件名
            List<String> result = new ArrayList<>();
            result.addAll(dir.getDirectories().keySet());
            result.addAll(dir.getFiles().keySet());
            return result;
        }

        /**
         * 检查文件是否存在
         *
         * @param path 文件路径
         * @return 如果文件存在返回 true，否则返回 false
         */
        public boolean fileExists(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                return false; // 根目录不是文件
            }

            String[] parentPath = Arrays.copyOf(pathComponents, pathComponents.length - 1);
            InMemoryDirectory parentDir;
            try {
                parentDir = traverseToParentDirectory(parentPath);
            } catch (IllegalArgumentException e) {
                return false;
            }

            String fileName = pathComponents[pathComponents.length - 1];
            return parentDir.hasFile(fileName);
        }

        /**
         * 检查目录是否存在
         *
         * @param path 目录路径
         * @return 如果目录存在返回 true，否则返回 false
         */
        public boolean directoryExists(String path) {
            String[] pathComponents = splitPath(path);
            if (pathComponents.length == 0) {
                return true; // 根目录总是存在
            }

            try {
                InMemoryDirectory parentDir = traverseToParentDirectory(
                        Arrays.copyOf(pathComponents, pathComponents.length - 1));
                String dirName = pathComponents[pathComponents.length - 1];
                return parentDir.hasDirectory(dirName);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}
