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
  Statistic,
} from 'antd';
import {
  PlusOutlined,
  SearchOutlined,
  EditOutlined,
  DeleteOutlined,
  EyeOutlined,
  ReloadOutlined,
  DollarOutlined,
} from '@ant-design/icons';
import Link from 'next/link';
import AuthGuard from '../../components/AuthGuard';
import Navigation from '../../components/Navigation';
import { Sale, QueryParams } from '../../src/types';
import api from '../../src/lib/api';

const { Title } = Typography;
const { Option } = Select;
const { RangePicker } = DatePicker;

function SaleListContent() {
  const [sales, setSales] = useState<Sale[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });
  const [searchParams, setSearchParams] = useState<QueryParams>({});
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingSale, setEditingSale] = useState<Sale | null>(null);
  const [form] = Form.useForm();
  const [summary, setSummary] = useState({
    totalAmount: 0,
    receivedAmount: 0,
    totalCount: 0,
  });

  const columns = [
    {
      title: '销售ID',
      dataIndex: 'id',
      key: 'id',
      width: 120,
    },
    {
      title: '客户姓名',
      dataIndex: ['client', 'name'],
      key: 'clientName',
      render: (text: string, record: Sale) => (
        <Link href={`/clients/${record.client.id}`}>
          <Button type="link" className="p-0">
            {text}
          </Button>
        </Link>
      ),
    },
    {
      title: '美容项目',
      dataIndex: ['beautifySkinItem', 'name'],
      key: 'beautifySkinItemName',
    },
    {
      title: '销售员工',
      dataIndex: ['employee', 'name'],
      key: 'employeeName',
    },
    {
      title: '项目数量',
      dataIndex: 'itemNumber',
      key: 'itemNumber',
      width: 100,
    },
    {
      title: '开卡总金额',
      dataIndex: 'createCardTotalAmount',
      key: 'createCardTotalAmount',
      width: 120,
      render: (amount: number) => `¥${amount.toLocaleString()}`,
    },
    {
      title: '实际回款',
      dataIndex: 'receivedAmount',
      key: 'receivedAmount',
      width: 120,
      render: (amount: number) => `¥${amount.toLocaleString()}`,
    },
    {
      title: '公司收入',
      dataIndex: 'receivedEarnedAmount',
      key: 'receivedEarnedAmount',
      width: 120,
      render: (amount: number) => `¥${amount.toLocaleString()}`,
    },
    {
      title: '开卡日期',
      dataIndex: 'createCardDate',
      key: 'createCardDate',
      width: 120,
      render: (date: string) => new Date(date).toLocaleDateString(),
    },
    {
      title: '状态',
      key: 'status',
      width: 100,
      render: (_, record: Sale) => {
        const isPaid = record.receivedAmount >= record.createCardTotalAmount;
        return (
          <Tag color={isPaid ? 'green' : 'orange'}>
            {isPaid ? '已付清' : '未付清'}
          </Tag>
        );
      },
    },
    {
      title: '操作',
      key: 'action',
      width: 150,
      render: (_, record: Sale) => (
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
              title="确定要删除这个销售记录吗？"
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

  const loadSales = async (params: QueryParams = {}) => {
    setLoading(true);
    try {
      const response = await api.get('/sales', { params });
      const { content, totalElements, currentPage, pageSize } = response.data;
      
      setSales(content);
      setPagination({
        current: currentPage + 1,
        pageSize,
        total: totalElements,
      });

      // 计算汇总数据
      const totalAmount = content.reduce((sum: number, sale: Sale) => sum + sale.createCardTotalAmount, 0);
      const receivedAmount = content.reduce((sum: number, sale: Sale) => sum + sale.receivedAmount, 0);
      setSummary({
        totalAmount,
        receivedAmount,
        totalCount: content.length,
      });
    } catch (error) {
      message.error('加载销售列表失败');
      console.error('Failed to load sales:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSales(searchParams);
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

  const handleView = (sale: Sale) => {
    // 跳转到销售详情页面
    window.open(`/sales/${sale.id}`, '_blank');
  };

  const handleEdit = (sale: Sale) => {
    setEditingSale(sale);
    form.setFieldsValue({
      clientId: sale.client.id,
      beautifySkinItemId: sale.beautifySkinItem.id,
      sellerId: sale.employee.id,
      itemNumber: sale.itemNumber,
      createCardTotalAmount: sale.createCardTotalAmount,
      receivedAmount: sale.receivedAmount,
      receivedEarnedAmount: sale.receivedEarnedAmount,
      createCardDate: sale.createCardDate,
      description: sale.description,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (id: string) => {
    try {
      await api.delete(`/sales/${id}`);
      message.success('删除成功');
      loadSales(searchParams);
    } catch (error) {
      message.error('删除失败');
      console.error('Failed to delete sale:', error);
    }
  };

  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();
      
      if (editingSale) {
        // 更新销售记录
        await api.put(`/sales/${editingSale.id}`, values);
        message.success('更新成功');
      } else {
        // 创建销售记录
        await api.post('/sales', values);
        message.success('创建成功');
      }
      
      setIsModalVisible(false);
      setEditingSale(null);
      form.resetFields();
      loadSales(searchParams);
    } catch (error) {
      message.error(editingSale ? '更新失败' : '创建失败');
      console.error('Failed to save sale:', error);
    }
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    setEditingSale(null);
    form.resetFields();
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <Title level={2}>销售管理</Title>
        <Space>
          <Button
            icon={<ReloadOutlined />}
            onClick={() => loadSales(searchParams)}
          >
            刷新
          </Button>
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              setEditingSale(null);
              form.resetFields();
              setIsModalVisible(true);
            }}
          >
            新增销售
          </Button>
        </Space>
      </div>

      {/* 汇总统计 */}
      <Row gutter={[16, 16]}>
        <Col xs={24} sm={8}>
          <Card>
            <Statistic
              title="销售总额"
              value={summary.totalAmount}
              prefix="¥"
              valueStyle={{ color: '#3f8600' }}
              icon={<DollarOutlined />}
            />
          </Card>
        </Col>
        <Col xs={24} sm={8}>
          <Card>
            <Statistic
              title="实际回款"
              value={summary.receivedAmount}
              prefix="¥"
              valueStyle={{ color: '#1890ff' }}
              icon={<DollarOutlined />}
            />
          </Card>
        </Col>
        <Col xs={24} sm={8}>
          <Card>
            <Statistic
              title="销售笔数"
              value={summary.totalCount}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
      </Row>

      <Card>
        <Form
          layout="inline"
          onFinish={handleSearch}
          className="mb-4"
        >
          <Row gutter={[16, 16]} className="w-full">
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="clientName">
                <Input
                  placeholder="客户姓名"
                  prefix={<SearchOutlined />}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="employeeName">
                <Input
                  placeholder="销售员工"
                  prefix={<SearchOutlined />}
                />
              </Form.Item>
            </Col>
            <Col xs={24} sm={12} md={6}>
              <Form.Item name="dateRange">
                <RangePicker placeholder={['开始日期', '结束日期']} />
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
          dataSource={sales}
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
        title={editingSale ? '编辑销售记录' : '新增销售记录'}
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={handleModalCancel}
        width={800}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="clientId"
                label="客户"
                rules={[{ required: true, message: '请选择客户' }]}
              >
                <Select placeholder="请选择客户">
                  {/* 这里应该从API获取客户列表 */}
                  <Option value="kh001">客户1</Option>
                  <Option value="kh002">客户2</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="beautifySkinItemId"
                label="美容项目"
                rules={[{ required: true, message: '请选择美容项目' }]}
              >
                <Select placeholder="请选择美容项目">
                  {/* 这里应该从API获取项目列表 */}
                  <Option value="xm001">项目1</Option>
                  <Option value="xm002">项目2</Option>
                </Select>
              </Form.Item>
            </Col>
          </Row>
          
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="sellerId"
                label="销售员工"
                rules={[{ required: true, message: '请选择销售员工' }]}
              >
                <Select placeholder="请选择销售员工">
                  {/* 这里应该从API获取员工列表 */}
                  <Option value="yg001">员工1</Option>
                  <Option value="yg002">员工2</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="itemNumber"
                label="项目数量"
                rules={[
                  { required: true, message: '请输入项目数量' },
                  { type: 'number', min: 1, max: 36, message: '数量必须在1-36之间' }
                ]}
              >
                <Input type="number" placeholder="请输入项目数量" />
              </Form.Item>
            </Col>
          </Row>
          
          <Row gutter={16}>
            <Col span={8}>
              <Form.Item
                name="createCardTotalAmount"
                label="开卡总金额"
                rules={[
                  { required: true, message: '请输入开卡总金额' },
                  { type: 'number', min: 0, message: '金额必须大于0' }
                ]}
              >
                <Input type="number" placeholder="请输入开卡总金额" />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item
                name="receivedAmount"
                label="实际回款"
                rules={[
                  { required: true, message: '请输入实际回款' },
                  { type: 'number', min: 0, message: '金额必须大于等于0' }
                ]}
              >
                <Input type="number" placeholder="请输入实际回款" />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item
                name="receivedEarnedAmount"
                label="公司收入"
                rules={[
                  { required: true, message: '请输入公司收入' },
                  { type: 'number', min: 0, message: '金额必须大于等于0' }
                ]}
              >
                <Input type="number" placeholder="请输入公司收入" />
              </Form.Item>
            </Col>
          </Row>
          
          <Form.Item
            name="createCardDate"
            label="开卡日期"
            rules={[{ required: true, message: '请选择开卡日期' }]}
          >
            <DatePicker className="w-full" placeholder="请选择开卡日期" />
          </Form.Item>
          
          <Form.Item
            name="description"
            label="描述"
          >
            <Input.TextArea
              rows={3}
              placeholder="请输入描述"
              maxLength={1000}
              showCount
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default function SalesPage() {
  return (
    <AuthGuard>
      <Navigation>
        <SaleListContent />
      </Navigation>
    </AuthGuard>
  );
}
