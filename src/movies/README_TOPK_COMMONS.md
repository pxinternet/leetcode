README: 使用 Apache Commons CSV 的 TopKMoviesCommons

该目录中包含两个实现：
- `TopKMovies.java`：自实现的 RFC4180 风格 CSV 解析器 + Top-K 逻辑（无需额外依赖）
- `TopKMoviesCommons.java`：基于 Apache Commons CSV 库的实现，解析更稳健，代码更简洁

如何编译并运行 TopKMoviesCommons（两种方式）：

1) 手动下载 commons-csv JAR 并在 classpath 指定
   - 下载 JAR（示例）：
     - https://repo1.maven.org/maven2/org/apache/commons/commons-csv/1.10.0/commons-csv-1.10.0.jar
   - 编译并运行：
     javac -d out -cp path\to\commons-csv-1.10.0.jar src\movies\TopKMoviesCommons.java
     java -cp out;path\to\commons-csv-1.10.0.jar movies.TopKMoviesCommons path\to\movies.csv 10

2) 使用 Maven（在项目根创建一个简单 pom.xml 并运行 mvn exec:java）

备注：TopKMoviesCommons 依赖库来处理复杂的 CSV 场景，推荐在生产环境中使用该实现。

