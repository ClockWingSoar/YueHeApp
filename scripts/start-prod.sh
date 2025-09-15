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
docker-compose down

# 拉取最新镜像
echo "📥 拉取最新镜像..."
docker-compose pull

# 启动服务
echo "🔨 启动服务..."
docker-compose up -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 60

# 检查服务状态
echo "🔍 检查服务状态..."
docker-compose ps

# 健康检查
echo "🏥 执行健康检查..."
sleep 10
curl -f http://localhost:8080/actuator/health || echo "❌ 后端健康检查失败"
curl -f http://localhost:3000 || echo "❌ 前端健康检查失败"

# 显示访问信息
echo ""
echo "✅ 生产环境启动完成！"
echo "🌐 应用地址: http://localhost"
echo "🔧 API地址: http://localhost/api"
echo "📊 监控地址: http://localhost:8080/actuator/prometheus"
echo ""
echo "📝 查看日志: docker-compose logs -f"
echo "🛑 停止服务: docker-compose down"
