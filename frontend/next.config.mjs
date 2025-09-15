/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  output: 'standalone', // 启用standalone输出用于Docker
  eslint: {
    // 在构建时忽略ESLint错误
    ignoreDuringBuilds: true,
  },
  typescript: {
    // 在构建时忽略TypeScript错误
    ignoreBuildErrors: true,
  },
};

export default nextConfig;

