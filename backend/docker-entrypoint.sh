#!/bin/bash
set -e

# 检查数据库是否已初始化的标志文件
INIT_FLAG="/app/db_initialized"

if [ ! -f "$INIT_FLAG" ]; then
    echo "首次运行，初始化数据库..."
    java -cp /app/app.jar org.springframework.boot.loader.JarLauncher --spring.datasource.url=jdbc:h2:~/superstar --spring.datasource.username=root --spring.datasource.password=123456 --spring.datasource.driver-class-name=org.h2.Driver --spring.sql.init.mode=always --spring.sql.init.schema-locations=file:/app/init.sql
    touch "$INIT_FLAG"
else
    echo "数据库已初始化，跳过初始化步骤。"
fi

# 运行主应用
exec java -jar /app/app.jar