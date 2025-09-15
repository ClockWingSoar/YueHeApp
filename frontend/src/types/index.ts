// 用户相关类型
export interface User {
  id: string;
  username: string;
  role: 'ADMIN' | 'EXPERT' | 'OPERATOR';
  createdAt: string;
  updatedAt: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}

// 客户相关类型
export interface Client {
  id: string;
  name: string;
  age?: number;
  gender?: '男' | '女';
  symptom?: string;
  cosmeticShop: CosmeticShop;
  createdAt: string;
  updatedAt: string;
}

export interface CreateClientRequest {
  name: string;
  age?: number;
  gender?: '男' | '女';
  symptom?: string;
  shopId: string;
}

export interface UpdateClientRequest extends Partial<CreateClientRequest> {
  id: string;
}

// 美容院相关类型
export interface CosmeticShop {
  id: string;
  name: string;
  owner?: string;
  contactMethod?: string;
  location?: string;
  size?: '小型' | '中型' | '大型';
  memberNumber: number;
  discount: number;
  shopPremium: number;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateCosmeticShopRequest {
  name: string;
  owner?: string;
  contactMethod?: string;
  location?: string;
  size?: '小型' | '中型' | '大型';
  memberNumber?: number;
  discount?: number;
  shopPremium?: number;
  description?: string;
}

// 销售相关类型
export interface Sale {
  id: string;
  client: Client;
  beautifySkinItem: BeautifySkinItem;
  employee: Employee;
  itemNumber: number;
  createCardTotalAmount: number;
  receivedAmount: number;
  receivedEarnedAmount: number;
  employeePremium: number;
  shopPremium: number;
  createCardDate: string;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateSaleRequest {
  clientId: string;
  beautifySkinItemId: string;
  sellerId: string;
  itemNumber: number;
  createCardTotalAmount: number;
  receivedAmount: number;
  receivedEarnedAmount: number;
  createCardDate: string;
  description?: string;
}

// 员工相关类型
export interface Employee {
  id: string;
  name: string;
  salary: number;
  birthday?: string;
  description?: string;
  resigned: '在职' | '离职';
  createdAt: string;
  updatedAt: string;
}

export interface CreateEmployeeRequest {
  name: string;
  salary?: number;
  birthday?: string;
  description?: string;
}

// 美容项目相关类型
export interface BeautifySkinItem {
  id: string;
  name: string;
  price: number;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateBeautifySkinItemRequest {
  name: string;
  price: number;
  description?: string;
}

// 操作相关类型
export interface Operation {
  id: string;
  sale: Sale;
  operator: Employee;
  tool: Tool;
  operationDate: string;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateOperationRequest {
  saleId: string;
  operatorId: string;
  toolId: string;
  operationDate: string;
  description?: string;
}

// 仪器相关类型
export interface Tool {
  id: string;
  name: string;
  major?: string;
  price: number;
  buyDate?: string;
  buyFrom?: string;
  operateExpense: number;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateToolRequest {
  name: string;
  major?: string;
  price?: number;
  buyDate?: string;
  buyFrom?: string;
  operateExpense?: number;
  description?: string;
}

// 通用类型
export interface PaginationParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: 'ASC' | 'DESC';
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
  timestamp: string;
}

export interface QueryParams extends PaginationParams {
  search?: string;
  startDate?: string;
  endDate?: string;
  [key: string]: any;
}
