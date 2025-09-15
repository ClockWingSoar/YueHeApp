'use client';

import { Provider } from 'react-redux';
import { store } from '../store/store';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useState } from 'react';

export function Providers({ children }: { children: React.ReactNode }) {
  const [queryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: {
          queries: {
            staleTime: 60 * 1000, // 1分钟
            retry: 1,
          },
        },
      })
  );

  return (
    <Provider store={store}>
      <QueryClientProvider client={queryClient}>
        <ConfigProvider
          locale={zhCN}
          theme={{
            token: {
              colorPrimary: '#0ea5e9',
              colorSuccess: '#22c55e',
              colorWarning: '#f59e0b',
              colorError: '#ef4444',
              borderRadius: 6,
            },
          }}
        >
          {children}
        </ConfigProvider>
      </QueryClientProvider>
    </Provider>
  );
}
