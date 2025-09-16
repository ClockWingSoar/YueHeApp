'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { Spin } from 'antd';

interface AuthGuardProps {
  children: React.ReactNode;
  requiredRoles?: string[];
}

export default function AuthGuard({ children, requiredRoles = [] }: AuthGuardProps) {
  const { isAuthenticated, user, isLoading } = useSelector((state: RootState) => state.auth);
  const router = useRouter();
  const [isChecking, setIsChecking] = useState(true);

  console.log('AuthGuard状态:', { isAuthenticated, user, isLoading, isChecking });

  useEffect(() => {
    const checkAuth = async () => {
      // 检查是否有token
      const token = localStorage.getItem('token');
      
      if (!token) {
        router.push('/login');
        return;
      }

      // 如果用户信息不存在，尝试从token中获取
      if (!user && token) {
        try {
          // 这里可以调用API验证token并获取用户信息
          // 暂时跳过，假设token有效
          setIsChecking(false);
        } catch (error) {
          localStorage.removeItem('token');
          router.push('/login');
        }
      } else {
        setIsChecking(false);
      }
    };

    checkAuth();
  }, [user, router]);

  // 添加一个effect来处理登录成功后的状态
  useEffect(() => {
    if (isAuthenticated && user && !isChecking) {
      setIsChecking(false);
    }
  }, [isAuthenticated, user, isChecking]);

  useEffect(() => {
    if (!isChecking && isAuthenticated && user) {
      // 检查角色权限
      if (requiredRoles.length > 0 && !requiredRoles.includes(user.role)) {
        router.push('/unauthorized');
        return;
      }
    }
  }, [isChecking, isAuthenticated, user, requiredRoles, router]);

  if (isLoading || isChecking) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <Spin size="large" />
      </div>
    );
  }

  if (!isAuthenticated) {
    return null;
  }

  return <>{children}</>;
}
