import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { RootState } from './store';

export const api = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api',
    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).auth.token;
      if (token) {
        headers.set('authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: [
    'User',
    'Client',
    'CosmeticShop',
    'Sale',
    'Employee',
    'BeautifySkinItem',
    'Operation',
    'Tool',
  ],
  endpoints: (builder) => ({
    // 认证相关
    login: builder.mutation<
      { token: string; user: any },
      { username: string; password: string }
    >({
      query: (credentials) => ({
        url: '/auth/login',
        method: 'POST',
        body: credentials,
      }),
      invalidatesTags: ['User'],
    }),

    logout: builder.mutation<void, void>({
      query: () => ({
        url: '/auth/logout',
        method: 'POST',
      }),
      invalidatesTags: ['User'],
    }),

    // 客户相关
    getClients: builder.query<any, any>({
      query: (params) => ({
        url: '/clients',
        params,
      }),
      providesTags: ['Client'],
    }),

    getClient: builder.query<any, string>({
      query: (id) => `/clients/${id}`,
      providesTags: (result, error, id) => [{ type: 'Client', id }],
    }),

    createClient: builder.mutation<any, any>({
      query: (client) => ({
        url: '/clients',
        method: 'POST',
        body: client,
      }),
      invalidatesTags: ['Client'],
    }),

    updateClient: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/clients/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Client', id },
        'Client',
      ],
    }),

    deleteClient: builder.mutation<void, string>({
      query: (id) => ({
        url: `/clients/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Client'],
    }),

    // 销售相关
    getSales: builder.query<any, any>({
      query: (params) => ({
        url: '/sales',
        params,
      }),
      providesTags: ['Sale'],
    }),

    getSale: builder.query<any, string>({
      query: (id) => `/sales/${id}`,
      providesTags: (result, error, id) => [{ type: 'Sale', id }],
    }),

    createSale: builder.mutation<any, any>({
      query: (sale) => ({
        url: '/sales',
        method: 'POST',
        body: sale,
      }),
      invalidatesTags: ['Sale'],
    }),

    updateSale: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/sales/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Sale', id },
        'Sale',
      ],
    }),

    deleteSale: builder.mutation<void, string>({
      query: (id) => ({
        url: `/sales/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Sale'],
    }),

    // 员工相关
    getEmployees: builder.query<any, any>({
      query: (params) => ({
        url: '/employees',
        params,
      }),
      providesTags: ['Employee'],
    }),

    getEmployee: builder.query<any, string>({
      query: (id) => `/employees/${id}`,
      providesTags: (result, error, id) => [{ type: 'Employee', id }],
    }),

    createEmployee: builder.mutation<any, any>({
      query: (employee) => ({
        url: '/employees',
        method: 'POST',
        body: employee,
      }),
      invalidatesTags: ['Employee'],
    }),

    updateEmployee: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/employees/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Employee', id },
        'Employee',
      ],
    }),

    deleteEmployee: builder.mutation<void, string>({
      query: (id) => ({
        url: `/employees/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Employee'],
    }),

    // 美容院相关
    getCosmeticShops: builder.query<any, any>({
      query: (params) => ({
        url: '/cosmetic-shops',
        params,
      }),
      providesTags: ['CosmeticShop'],
    }),

    getCosmeticShop: builder.query<any, string>({
      query: (id) => `/cosmetic-shops/${id}`,
      providesTags: (result, error, id) => [{ type: 'CosmeticShop', id }],
    }),

    createCosmeticShop: builder.mutation<any, any>({
      query: (shop) => ({
        url: '/cosmetic-shops',
        method: 'POST',
        body: shop,
      }),
      invalidatesTags: ['CosmeticShop'],
    }),

    updateCosmeticShop: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/cosmetic-shops/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'CosmeticShop', id },
        'CosmeticShop',
      ],
    }),

    deleteCosmeticShop: builder.mutation<void, string>({
      query: (id) => ({
        url: `/cosmetic-shops/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['CosmeticShop'],
    }),

    // 美容项目相关
    getBeautifySkinItems: builder.query<any, any>({
      query: (params) => ({
        url: '/beautify-skin-items',
        params,
      }),
      providesTags: ['BeautifySkinItem'],
    }),

    getBeautifySkinItem: builder.query<any, string>({
      query: (id) => `/beautify-skin-items/${id}`,
      providesTags: (result, error, id) => [{ type: 'BeautifySkinItem', id }],
    }),

    createBeautifySkinItem: builder.mutation<any, any>({
      query: (item) => ({
        url: '/beautify-skin-items',
        method: 'POST',
        body: item,
      }),
      invalidatesTags: ['BeautifySkinItem'],
    }),

    updateBeautifySkinItem: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/beautify-skin-items/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'BeautifySkinItem', id },
        'BeautifySkinItem',
      ],
    }),

    deleteBeautifySkinItem: builder.mutation<void, string>({
      query: (id) => ({
        url: `/beautify-skin-items/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['BeautifySkinItem'],
    }),

    // 操作相关
    getOperations: builder.query<any, any>({
      query: (params) => ({
        url: '/operations',
        params,
      }),
      providesTags: ['Operation'],
    }),

    getOperation: builder.query<any, string>({
      query: (id) => `/operations/${id}`,
      providesTags: (result, error, id) => [{ type: 'Operation', id }],
    }),

    createOperation: builder.mutation<any, any>({
      query: (operation) => ({
        url: '/operations',
        method: 'POST',
        body: operation,
      }),
      invalidatesTags: ['Operation'],
    }),

    updateOperation: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/operations/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Operation', id },
        'Operation',
      ],
    }),

    deleteOperation: builder.mutation<void, string>({
      query: (id) => ({
        url: `/operations/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Operation'],
    }),

    // 仪器相关
    getTools: builder.query<any, any>({
      query: (params) => ({
        url: '/tools',
        params,
      }),
      providesTags: ['Tool'],
    }),

    getTool: builder.query<any, string>({
      query: (id) => `/tools/${id}`,
      providesTags: (result, error, id) => [{ type: 'Tool', id }],
    }),

    createTool: builder.mutation<any, any>({
      query: (tool) => ({
        url: '/tools',
        method: 'POST',
        body: tool,
      }),
      invalidatesTags: ['Tool'],
    }),

    updateTool: builder.mutation<any, { id: string; data: any }>({
      query: ({ id, data }) => ({
        url: `/tools/${id}`,
        method: 'PUT',
        body: data,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Tool', id },
        'Tool',
      ],
    }),

    deleteTool: builder.mutation<void, string>({
      query: (id) => ({
        url: `/tools/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Tool'],
    }),

    // 报表相关
    getSaleReport: builder.query<any, any>({
      query: (params) => ({
        url: '/reports/sales',
        params,
      }),
    }),

    getClientReport: builder.query<any, any>({
      query: (params) => ({
        url: '/reports/clients',
        params,
      }),
    }),

    getEmployeeReport: builder.query<any, any>({
      query: (params) => ({
        url: '/reports/employees',
        params,
      }),
    }),
  }),
});

export const {
  useLoginMutation,
  useLogoutMutation,
  useGetClientsQuery,
  useGetClientQuery,
  useCreateClientMutation,
  useUpdateClientMutation,
  useDeleteClientMutation,
  useGetSalesQuery,
  useGetSaleQuery,
  useCreateSaleMutation,
  useUpdateSaleMutation,
  useDeleteSaleMutation,
  useGetEmployeesQuery,
  useGetEmployeeQuery,
  useCreateEmployeeMutation,
  useUpdateEmployeeMutation,
  useDeleteEmployeeMutation,
  useGetCosmeticShopsQuery,
  useGetCosmeticShopQuery,
  useCreateCosmeticShopMutation,
  useUpdateCosmeticShopMutation,
  useDeleteCosmeticShopMutation,
  useGetBeautifySkinItemsQuery,
  useGetBeautifySkinItemQuery,
  useCreateBeautifySkinItemMutation,
  useUpdateBeautifySkinItemMutation,
  useDeleteBeautifySkinItemMutation,
  useGetOperationsQuery,
  useGetOperationQuery,
  useCreateOperationMutation,
  useUpdateOperationMutation,
  useDeleteOperationMutation,
  useGetToolsQuery,
  useGetToolQuery,
  useCreateToolMutation,
  useUpdateToolMutation,
  useDeleteToolMutation,
  useGetSaleReportQuery,
  useGetClientReportQuery,
  useGetEmployeeReportQuery,
} = api;
