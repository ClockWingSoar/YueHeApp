'use client';

import { useState, useEffect } from 'react';
import {
  Card,
  Table,
  Button,
  Space,
  Input,
  Select,
  DatePicker,
  Tag,
  Modal,
  Form,
  message,
  Popconfirm,
  Row,
  Col,
  Typography,
  Tooltip,
} from 'antd';
import {
  PlusOutlined,
  SearchOutlined,
  EditOutlined,
  DeleteOutlined,
  EyeOutlined,
  ReloadOutlined,
} from '@ant-design/icons';
import Link from 'next/link';
import AuthGuard from '../../components/AuthGuard';
import Navigation from '../../components/Navigation';
import { Client, QueryParams } from '../../src/types';
import api from '../../src/lib/api';

const { Title } = Typography;
const { Option } = Select;
const { RangePicker } = DatePicker;

function ClientListContent() {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });
  const [searchParams, setSearchParams] = useState<QueryParams>({});
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingClient, setEditingClient] = useState<Client | null>(null);
  const [form] = Form.useForm();

  const columns = [
    {
      title: '客户ID',
      dataIndex: 'id',
      key: 'id',
      width: 120,
    },
    {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
      render: (text: string, record: Client) => (
        <Link href={`/clients/${record.id}`}>
          <Button type="link" className="p-0">
            {text}
          </Button>
        </Link>
      ),
    },
    {
      title: '年龄',
      dataIndex: 'age',
      key: 'age',
      width: 80,
    },
    {
      title: '性别',
      dataIndex: 'gender',
      key: 'gender',
      width: 80,
      render: (gender: string) => (
        <Tag color={gender === '男' ? 'blue' : 'pink'}>
          {gender}
        </Tag>
      ),
    },
    {
      title: '美容院',
      dataIndex: ['cosmeticShop', 'name'],
      key: 'cosmeticShop',
    },
    {
      title: '症状描述',
      dataIndex: 'symptom',
      key: 'symptom',
      ellipsis: true,
      render: (text: string) => (
        <Tooltip title={text}>
          <span>{text}</span>
        </Tooltip>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      width: 120,
      render: (date: string) => new Date(date).toLocaleDateString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 150,
      render: (_, record: Client) => (
        <Space size="small">
          <Tooltip title="查看详情">
            <Button
              type="text"
              icon={<EyeOutlined />}
              onClick={() => handleView(record)}
            />
          </Tooltip>
          <Tooltip title="编辑">
            <Button
              type="text"
              icon={<EditOutlined />}
              onClick={() => handleEdit(record)}
            />
          </Tooltip>
          <Tooltip title="删除">
            <Popconfirm
              title="确定要删除这个客户吗？"
              onConfirm={() => handleDelete(record.id)}
              okText="确定"
              cancelText="取消"
            >
              <Button type="text" danger icon={<DeleteOutlined />} />
            </Popconfirm>
          </Tooltip>
        </Space>
      ),
    },
  ];

  const loadClients = async (params: QueryParams = {}) => {
    setLoading(true);
    try {
      const response = await api.get('/clients', { params });
      const { content, totalElements, currentPage, pageSize } = response.data;
      
      setClients(content);
      setPagination({
        current: currentPage + 1,
        pageSize,
        total: totalElements,
      });
    } catch (error) {
      message.error('加载客户列表失败');
      console.error('Failed to load clients:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadClients(searchParams);
  }, [searchParams]);

  const handleSearch = (values: any) => {
    const params: QueryParams = {
      ...values,
      page: 0,
      size: pagination.pageSize,
    };
    setSearchParams(params);
  };

  const handleTableChange = (pagination: any) => {
    const params = {
      ...searchParams,
      page: pagination.current - 1,
      size: pagination.pageSize,
    };
    setSearchParams(params);
  };

  const handleView = (client: Client) => {
    // 跳转到客户详情页面
    window.open(`/clients/${client.id}`, '_blank');
  };

  const handleEdit = (client: Client) => {
    setEditingClient(client);
    form.setFieldsValue({
      name: client.name,
      age: client.age,
      gender: client.gender,
      symptom: client.symptom,
      shopId: client.cosmeticShop.id,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (id: string) => {
    try {
      await api.delete(`/clients/${id}`);
      message.success('删除成功');
      loadClients(searchParams);
    } catch (error) {
      message.error('删除失败');
      console.error('Failed to delete client:', error);
    }
  };

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();
      
      if (editingClient) {
        // 更新客户
        await api.put(`/clients/${editingClient.id}`, values);
        message.success('更新成功');
      } else {
        // 创建客户
        await api.post('/clients', values);
        message.success('创建成功');
      }
      
      setIsModalVisible(false);
      setEditingClient(null);
      form.resetFields();
      loadClients(searchParams);
    } catch (error) {
      message.error(editingClient ? '更新失败' : '创建失败');
      console.error('Failed to save client:', error);
    }
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    setEditingClient(null);
    form.resetFields();
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <Title level={2}>客户管理</Title>
        <Space>
          <Button
            icon={<ReloadOutlined />}
            onClick={() => loadClients(searchParams)}
          >
            刷新
          </Button>
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              setEditingClient(null);
              form.resetFields();
              setIsModalVisible(true);
            }}
          >
            新增客户
          </Button>
        </Space>
      </div>

      <Card>
        <Form
          layout="inline"
          onFinish={handleSearch}
          className="mb-4"
        >
          <Row gutter={[16, 16]} className="w-full">
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="name">
                <Input
                  placeholder="客户姓名"
                  prefix={<SearchOutlined />}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="gender">
                <Select placeholder="性别" allowClear>
                  <Option value="男">男</Option>
                  <Option value="女">女</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="shopId">
                <Select placeholder="美容院" allowClear>
                  {/* 这里应该从API获取美容院列表 */}
                  <Option value="mr001">美容院1</Option>
                  <Option value="mr002">美容院2</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={6}>
              <Space>
                <Button type="primary" htmlType="submit" icon={<SearchOutlined />}>
                  搜索
                </Button>
                <Button onClick={() => {
                  form.resetFields();
                  setSearchParams({});
                }}>
                  重置
                </Button>
              </Space>
            </Col>
          </Row>
        </Form>

        <Table
          columns={columns}
          dataSource={clients}
          rowKey="id"
          loading={loading}
          pagination={{
            ...pagination,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) =>
              `第 ${range[0]}-${range[1]} 条/共 ${total} 条`,
          }}
          onChange={handleTableChange}
        />
      </Card>

      <Modal
        title={editingClient ? '编辑客户' : '新增客户'}
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        width={600}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Form.Item
            name="name"
            label="客户姓名"
            rules={[{ required: true, message: '请输入客户姓名' }]}
          >
            <Input placeholder="请输入客户姓名" />
          </Form.Item>
          
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="age"
                label="年龄"
                rules={[
                  { type: 'number', min: 0, max: 120, message: '年龄必须在0-120之间' }
                ]}
              >
                <Input type="number" placeholder="请输入年龄" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="gender"
                label="性别"
              >
                <Select placeholder="请选择性别">
                  <Option value="男">男</Option>
                  <Option value="女">女</Option>
                </Select>
              </Form.Item>
            </Col>
          </Row>
          
          <Form.Item
            name="shopId"
            label="所属美容院"
            rules={[{ required: true, message: '请选择美容院' }]}
          >
            <Select placeholder="请选择美容院">
              <Option value="mr001">美容院1</Option>
              <Option value="mr002">美容院2</Option>
            </Select>
          </Form.Item>
          
          <Form.Item
            name="symptom"
            label="症状描述"
          >
            <Input.TextArea
              rows={4}
              placeholder="请输入症状描述"
              maxLength={500}
              showCount
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default function ClientsPage() {
  return (
    <AuthGuard>
      <Navigation>
        <ClientListContent />
      </Navigation>
    </AuthGuard>
  );
}
