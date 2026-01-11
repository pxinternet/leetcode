#!/bin/bash

# 编译所有 Java 文件
echo "正在编译 Java 文件..."
find src -name "*.java" -print0 | xargs -0 javac -d out -encoding UTF-8

if [ $? -eq 0 ]; then
    echo "编译成功！"
    echo "运行示例："
    echo "  java -cp out leetCode.Main"
    echo "  java -cp out dp.LC115numDistince"
else
    echo "编译失败！"
    exit 1
fi
