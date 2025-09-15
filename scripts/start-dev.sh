#!/bin/bash

# 开发环境启动脚本

echo "🚀 启动YueHeApp开发环境..."

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
docker-compose -f docker-compose.dev.yml down

# 启动数据库服务
echo "🔨 启动数据库服务..."
docker-compose -f docker-compose.dev.yml up -d

# 等待服务启动
echo "⏳ 等待数据库启动..."
sleep 10

# 检查服务状态
echo "🔍 检查服务状态..."
docker-compose -f docker-compose.dev.yml ps

# 显示访问信息
echo ""
echo "✅ 数据库服务启动完成！"
echo "🗄️  MySQL: localhost:3306 (用户: yuehe, 密码: yuehe123)"
echo "🔴 Redis: localhost:6379"
echo ""
echo "📝 查看日志: docker-compose -f docker-compose.dev.yml logs -f"
echo "🛑 停止服务: docker-compose -f docker-compose.dev.yml down"
echo ""
echo "💡 现在可以启动本地开发服务："
echo "   后端: cd yuehe-backend && mvn spring-boot:run"
echo "   前端: cd frontend && npm run dev"
