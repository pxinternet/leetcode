#!/bin/bash

# 运行指定的类
if [ -z "$1" ]; then
    echo "用法: ./run.sh <完整类名>"
    echo "示例: ./run.sh leetCode.Main"
    echo "示例: ./run.sh dp.LC115numDistince"
    exit 1
fi

# 确保已经编译
if [ ! -d "out" ]; then
    echo "未找到编译输出目录，正在编译..."
    ./compile.sh
fi

# 运行指定的类
java -cp out "$1"
