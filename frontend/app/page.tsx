'use client';

import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Layout, Card, Row, Col, Statistic, Typography, Space, Button, Spin } from 'antd';
import {
  UserOutlined,
  ShoppingCartOutlined,
  TeamOutlined,
  BarChartOutlined,
  PlusOutlined,
  EyeOutlined,
} from '@ant-design/icons';
import Link from 'next/link';
import AuthGuard from '../components/AuthGuard';
import Navigation from '../components/Navigation';
import { RootState } from '../store/store';
import api from '../src/lib/api';

const { Header, Content } = Layout;
const { Title, Text } = Typography;

function DashboardContent() {
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState([
    {
      title: '客户总数',
      value: 0,
      icon: <UserOutlined style={{ fontSize: '24px', color: '#1890ff' }} />,
      color: '#1890ff',
    },
    {
      title: '本月销售',
      value: 0,
      prefix: '¥',
      icon: <ShoppingCartOutlined style={{ fontSize: '24px', color: '#52c41a' }} />,
      color: '#52c41a',
    },
    {
      title: '员工数量',
      value: 0,
      icon: <TeamOutlined style={{ fontSize: '24px', color: '#faad14' }} />,
      color: '#faad14',
    },
    {
      title: '美容院数量',
      value: 0,
      icon: <BarChartOutlined style={{ fontSize: '24px', color: '#f5222d' }} />,
      color: '#f5222d',
    },
  ]);

  useEffect(() => {
    // 加载仪表板数据
    const loadDashboardData = async () => {
      try {
        // 这里可以调用API获取真实数据
        // const response = await api.get('/dashboard/stats');
        // setStats(response.data);
        
        // 暂时使用模拟数据
        setStats([
          {
            title: '客户总数',
            value: 1234,
            icon: <UserOutlined style={{ fontSize: '24px', color: '#1890ff' }} />,
            color: '#1890ff',
          },
          {
            title: '本月销售',
            value: 56789,
            prefix: '¥',
            icon: <ShoppingCartOutlined style={{ fontSize: '24px', color: '#52c41a' }} />,
            color: '#52c41a',
          },
          {
            title: '员工数量',
            value: 45,
            icon: <TeamOutlined style={{ fontSize: '24px', color: '#faad14' }} />,
            color: '#faad14',
          },
          {
            title: '美容院数量',
            value: 8,
            icon: <BarChartOutlined style={{ fontSize: '24px', color: '#f5222d' }} />,
            color: '#f5222d',
          },
        ]);
      } catch (error) {
        console.error('Failed to load dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, []);

  const quickActions = [
    {
      title: '新增客户',
      description: '添加新的客户信息',
      icon: <UserOutlined />,
      href: '/clients/new',
      color: '#1890ff',
    },
    {
      title: '创建销售',
      description: '记录新的销售订单',
      icon: <ShoppingCartOutlined />,
      href: '/sales/new',
      color: '#52c41a',
    },
    {
      title: '查看报表',
      description: '查看业务统计报表',
      icon: <BarChartOutlined />,
      href: '/reports',
      color: '#faad14',
    },
  ];

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Spin size="large" />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* 欢迎区域 */}
      <Card className="bg-gradient-to-r from-primary-50 to-secondary-50 border-0">
        <div className="text-center py-8">
          <Title level={2} className="!mb-2">
            欢迎使用悦和国际美容院管理系统
          </Title>
          <Text className="text-lg text-gray-600">
            专业的美容院运营管理平台，助力您的业务发展
          </Text>
        </div>
      </Card>

      {/* 统计卡片 */}
      <Row gutter={[16, 16]}>
        {stats.map((stat, index) => (
          <Col xs={24} sm={12} lg={6} key={index}>
            <Card className="hover:shadow-lg transition-shadow duration-300">
              <div className="flex items-center justify-between">
                <div>
                  <Text className="text-gray-500 text-sm">{stat.title}</Text>
                  <div className="mt-2">
                    <Statistic
                      value={stat.value}
                      prefix={stat.prefix}
                      valueStyle={{ color: stat.color, fontSize: '24px' }}
                    />
                  </div>
                </div>
                <div className="text-right">{stat.icon}</div>
              </div>
            </Card>
          </Col>
        ))}
      </Row>

      {/* 快速操作 */}
      <Card title="快速操作" className="shadow-sm">
        <Row gutter={[16, 16]}>
          {quickActions.map((action, index) => (
            <Col xs={24} sm={12} lg={8} key={index}>
              <Link href={action.href}>
                <Card
                  hoverable
                  className="text-center h-full border-2 hover:border-primary-300 transition-colors duration-300"
                >
                  <div className="py-4">
                    <div
                      className="text-4xl mb-4 mx-auto w-16 h-16 rounded-full flex items-center justify-center"
                      style={{ backgroundColor: `${action.color}20`, color: action.color }}
                    >
                      {action.icon}
                    </div>
                    <Title level={4} className="!mb-2">
                      {action.title}
                    </Title>
                    <Text className="text-gray-500">{action.description}</Text>
                  </div>
                </Card>
              </Link>
            </Col>
          ))}
        </Row>
      </Card>

      {/* 最近活动 */}
      <Row gutter={[16, 16]}>
        <Col xs={24} lg={12}>
          <Card title="最近客户" extra={<Link href="/clients">查看全部</Link>}>
            <div className="space-y-3">
              {[1, 2, 3, 4, 5].map((item) => (
                <div key={item} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div className="flex items-center space-x-3">
                    <div className="w-10 h-10 bg-primary-100 rounded-full flex items-center justify-center">
                      <UserOutlined className="text-primary-600" />
                    </div>
                    <div>
                      <Text strong>客户 {item}</Text>
                      <div className="text-sm text-gray-500">2小时前</div>
                    </div>
                  </div>
                  <Button type="text" icon={<EyeOutlined />} size="small">
                    查看
                  </Button>
                </div>
              ))}
            </div>
          </Card>
        </Col>

        <Col xs={24} lg={12}>
          <Card title="销售概览" extra={<Link href="/sales">查看全部</Link>}>
            <div className="space-y-3">
              {[1, 2, 3, 4, 5].map((item) => (
                <div key={item} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div className="flex items-center space-x-3">
                    <div className="w-10 h-10 bg-green-100 rounded-full flex items-center justify-center">
                      <ShoppingCartOutlined className="text-green-600" />
                    </div>
                    <div>
                      <Text strong>销售订单 #{item}</Text>
                      <div className="text-sm text-gray-500">¥{1000 + item * 100}</div>
                    </div>
                  </div>
                  <Button type="text" icon={<EyeOutlined />} size="small">
                    查看
                  </Button>
                </div>
              ))}
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  );
}

export default function Home() {
  return (
    <AuthGuard>
      <Navigation>
        <DashboardContent />
      </Navigation>
    </AuthGuard>
  );
}

