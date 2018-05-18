#! /bin/sh
if command -v clang-format >/dev/null 2>&1
then
    find app/src/main -iname "*.java" -exec clang-format -i {} \;
else
    echo "Could not format code. Please install clang-format"
fi
