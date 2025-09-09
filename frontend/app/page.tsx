"use client";
import { Provider } from 'react-redux';
import { store } from '../store/store';

export default function HomePage() {
  return (
    <Provider store={store}>
      <main style={{ padding: 24 }}>
        <h1>YueHe Frontend</h1>
        <p>Next.js + Redux scaffold is ready.</p>
      </main>
    </Provider>
  );
}

