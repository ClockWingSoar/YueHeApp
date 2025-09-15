#!/bin/bash

# 生产环境启动脚本

echo "🚀 启动YueHeApp生产环境..."

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker未运行，请先启动Docker"
    exit 1
fi

# 检查Docker Compose是否安装
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose未安装，请先安装Docker Compose"
    exit 1
fi

# 停止现有容器
echo "🛑 停止现有容器..."
docker-compose -f docker-compose.prod.yml down

# 构建并启动服务
echo "🔨 构建并启动服务..."
docker-compose -f docker-compose.prod.yml up --build -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 30

# 检查服务状态
echo "🔍 检查服务状态..."
docker-compose -f docker-compose.prod.yml ps

# 显示访问信息
echo ""
echo "✅ 服务启动完成！"
echo "🌐 前端地址: http://localhost:3000"
echo "🔧 后端API: http://localhost:8080/api"
echo "📊 健康检查: http://localhost:8080/actuator/health"
echo "🗄️  数据库: localhost:3306"
echo "🔴 Redis: localhost:6379"
echo ""
echo "📝 查看日志: docker-compose -f docker-compose.prod.yml logs -f"
echo "🛑 停止服务: docker-compose -f docker-compose.prod.yml down"