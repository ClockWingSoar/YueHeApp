'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useDispatch } from 'react-redux';
import { Form, Input, Button, Card, Typography, Alert, Space } from 'antd';
import { UserOutlined, LockOutlined, LoginOutlined } from '@ant-design/icons';
import { loginStart, loginSuccess, loginFailure } from '../../store/slices/authSlice';
import api from '../../src/lib/api';
import { LoginRequest, LoginResponse } from '../../src/types';
import logger from '../../src/lib/logger';

const { Title, Text } = Typography;

export default function LoginPage() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();
  const dispatch = useDispatch();

  const handleLogin = async (values: LoginRequest) => {
    setLoading(true);
    setError(null);
    dispatch(loginStart());

    logger.info('用户尝试登录', { username: values.username });

    try {
      // 调用后端登录API
      const response = await api.post<LoginResponse>('/auth/login', values);
      console.log('登录API响应:', response.data);
      const { token, user } = response.data.data;

      // 保存token到localStorage
      localStorage.setItem('token', token);
      console.log('Token已保存到localStorage:', token);
      
      logger.info('用户登录成功', { 
        username: user.username, 
        role: user.role,
        userId: user.id 
      });
      
      // 更新Redux状态
      dispatch(loginSuccess({ user, token }));
      console.log('Redux状态已更新');
      
      // 跳转到首页
      console.log('准备跳转到首页...');
      // 使用window.location.href确保跳转
      window.location.href = '/';
      console.log('window.location.href已调用');
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || '登录失败，请检查用户名和密码';
      
      logger.error('用户登录失败', { 
        username: values.username, 
        error: errorMessage,
        status: err.response?.status 
      });
      
      setError(errorMessage);
      dispatch(loginFailure(errorMessage));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <Card className="w-full max-w-md shadow-xl">
        <div className="text-center mb-8">
          <div className="w-16 h-16 bg-primary-600 rounded-full flex items-center justify-center mx-auto mb-4">
            <UserOutlined className="text-2xl text-white" />
          </div>
          <Title level={2} className="!mb-2">
            悦和国际美容院管理系统
          </Title>
          <Text className="text-gray-600">请登录您的账户</Text>
        </div>

        {error && (
          <Alert
            message={error}
            type="error"
            showIcon
            className="mb-4"
            closable
            onClose={() => setError(null)}
          />
        )}

        <Form
          name="login"
          onFinish={handleLogin}
          layout="vertical"
          size="large"
        >
          <Form.Item
            name="username"
            label="用户名"
            rules={[
              { required: true, message: '请输入用户名' },
              { min: 3, message: '用户名至少3个字符' }
            ]}
          >
            <Input
              prefix={<UserOutlined className="text-gray-400" />}
              placeholder="请输入用户名"
            />
          </Form.Item>

          <Form.Item
            name="password"
            label="密码"
            rules={[
              { required: true, message: '请输入密码' },
              { min: 6, message: '密码至少6个字符' }
            ]}
          >
            <Input.Password
              prefix={<LockOutlined className="text-gray-400" />}
              placeholder="请输入密码"
            />
          </Form.Item>

          <Form.Item className="mb-0">
            <Button
              type="primary"
              htmlType="submit"
              loading={loading}
              icon={<LoginOutlined />}
              className="w-full h-12 text-lg"
            >
              {loading ? '登录中...' : '登录'}
            </Button>
          </Form.Item>
        </Form>

        <div className="mt-6 text-center">
          <Space direction="vertical" size="small">
            <Text className="text-sm text-gray-500">
              默认账户信息：
            </Text>
            <Text className="text-xs text-gray-400">
              管理员：admin / admin123
            </Text>
            <Text className="text-xs text-gray-400">
              专家：expert / expert123
            </Text>
            <Text className="text-xs text-gray-400">
              操作员：operator / operator123
            </Text>
          </Space>
        </div>
      </Card>
    </div>
  );
}
