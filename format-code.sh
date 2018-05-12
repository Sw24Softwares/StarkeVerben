#! /bin/sh
find app/src/main -iname "*.java" -exec clang-format -i {} \;
