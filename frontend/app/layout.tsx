export const metadata = {
  title: 'YueHe App',
  description: 'Next.js + Redux frontend',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}

