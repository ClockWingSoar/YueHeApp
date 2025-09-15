'use client';

import { useState } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import { useSelector, useDispatch } from 'react-redux';
import {
  Layout,
  Menu,
  Button,
  Dropdown,
  Avatar,
  Space,
  Typography,
  Badge,
} from 'antd';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  LogoutOutlined,
  SettingOutlined,
  HomeOutlined,
  TeamOutlined,
  ShoppingCartOutlined,
  ToolOutlined,
  BarChartOutlined,
  ShopOutlined,
  ExperimentOutlined,
  UserAddOutlined,
  FileTextOutlined,
} from '@ant-design/icons';
import { RootState } from '../store/store';
import { logout } from '../store/slices/authSlice';

const { Header, Sider } = Layout;
const { Text } = Typography;

interface NavigationProps {
  children: React.ReactNode;
}

export default function Navigation({ children }: NavigationProps) {
  const [collapsed, setCollapsed] = useState(false);
  const router = useRouter();
  const pathname = usePathname();
  const dispatch = useDispatch();
  const { user } = useSelector((state: RootState) => state.auth);

  const handleLogout = () => {
    dispatch(logout());
    localStorage.removeItem('token');
    router.push('/login');
  };

  const userMenuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: '个人资料',
      onClick: () => router.push('/profile'),
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: '系统设置',
      onClick: () => router.push('/settings'),
    },
    {
      type: 'divider' as const,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: handleLogout,
    },
  ];

  // 根据用户角色生成菜单项
  const getMenuItems = () => {
    const baseItems = [
      {
        key: '/',
        icon: <HomeOutlined />,
        label: '首页',
      },
    ];

    const clientItems = [
      {
        key: 'client',
        icon: <TeamOutlined />,
        label: '客户管理',
        children: [
          {
            key: '/clients',
            label: '客户列表',
          },
          {
            key: '/clients/new',
            label: '新增客户',
          },
          {
            key: '/clients/profile',
            label: '客户档案',
          },
        ],
      },
    ];

    const saleItems = [
      {
        key: 'sale',
        icon: <ShoppingCartOutlined />,
        label: '销售管理',
        children: [
          {
            key: '/sales',
            label: '销售列表',
          },
          {
            key: '/sales/new',
            label: '新增销售',
          },
          {
            key: '/sales/summary',
            label: '销售汇总',
          },
        ],
      },
    ];

    const operationItems = [
      {
        key: 'operation',
        icon: <ToolOutlined />,
        label: '操作管理',
        children: [
          {
            key: '/operations',
            label: '操作列表',
          },
          {
            key: '/operations/new',
            label: '新增操作',
          },
        ],
      },
    ];

    const employeeItems = [
      {
        key: 'employee',
        icon: <UserAddOutlined />,
        label: '员工管理',
        children: [
          {
            key: '/employees',
            label: '员工列表',
          },
          {
            key: '/employees/new',
            label: '新增员工',
          },
        ],
      },
    ];

    const shopItems = [
      {
        key: 'shop',
        icon: <ShopOutlined />,
        label: '美容院管理',
        children: [
          {
            key: '/shops',
            label: '美容院列表',
          },
          {
            key: '/shops/new',
            label: '新增美容院',
          },
        ],
      },
    ];

    const itemItems = [
      {
        key: 'item',
        icon: <ExperimentOutlined />,
        label: '项目管理',
        children: [
          {
            key: '/items',
            label: '项目列表',
          },
          {
            key: '/items/new',
            label: '新增项目',
          },
        ],
      },
    ];

    const reportItems = [
      {
        key: 'report',
        icon: <BarChartOutlined />,
        label: '报表管理',
        children: [
          {
            key: '/reports/sales',
            label: '销售报表',
          },
          {
            key: '/reports/clients',
            label: '客户报表',
          },
          {
            key: '/reports/employees',
            label: '员工报表',
          },
        ],
      },
    ];

    // 根据用户角色返回不同的菜单项
    if (!user) return baseItems;

    switch (user.role) {
      case 'ADMIN':
        return [
          ...baseItems,
          ...clientItems,
          ...saleItems,
          ...operationItems,
          ...employeeItems,
          ...shopItems,
          ...itemItems,
          ...reportItems,
        ];
      case 'EXPERT':
        return [
          ...baseItems,
          ...clientItems,
          ...saleItems,
          ...operationItems,
          ...employeeItems,
          ...shopItems,
          ...itemItems,
          ...reportItems,
        ];
      case 'OPERATOR':
        return [
          ...baseItems,
          ...clientItems,
          ...saleItems,
          ...operationItems,
        ];
      default:
        return baseItems;
    }
  };

  const handleMenuClick = ({ key }: { key: string }) => {
    if (key.startsWith('/')) {
      router.push(key);
    }
  };

  return (
    <Layout className="min-h-screen">
      <Sider
        trigger={null}
        collapsible
        collapsed={collapsed}
        className="bg-white shadow-lg"
        width={250}
      >
        <div className="h-16 flex items-center justify-center border-b border-gray-200">
          <Text className="text-lg font-bold text-primary-600">
            {collapsed ? '悦和' : '悦和国际美容院管理系统'}
          </Text>
        </div>
        <Menu
          mode="inline"
          selectedKeys={[pathname]}
          items={getMenuItems()}
          onClick={handleMenuClick}
          className="border-r-0"
        />
      </Sider>

      <Layout>
        <Header className="bg-white shadow-sm px-4 flex items-center justify-between">
          <Button
            type="text"
            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            onClick={() => setCollapsed(!collapsed)}
            className="text-lg"
          />
          
          <div className="flex items-center space-x-4">
            <Badge count={0} size="small">
              <Button type="text" icon={<FileTextOutlined />} />
            </Badge>
            
            <Dropdown
              menu={{ items: userMenuItems }}
              placement="bottomRight"
              arrow
            >
              <Space className="cursor-pointer">
                <Avatar icon={<UserOutlined />} />
                <div className="hidden md:block">
                  <Text strong>{user?.username}</Text>
                  <br />
                  <Text type="secondary" className="text-xs">
                    {user?.role === 'ADMIN' ? '管理员' : 
                     user?.role === 'EXPERT' ? '专家' : '操作员'}
                  </Text>
                </div>
              </Space>
            </Dropdown>
          </div>
        </Header>

        <div className="p-6 bg-gray-50 min-h-screen">
          {children}
        </div>
      </Layout>
    </Layout>
  );
}
