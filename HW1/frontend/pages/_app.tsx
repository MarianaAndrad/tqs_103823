import '@/styles/globals.css';
import type { AppProps } from 'next/app';
import Navbar from '@/components/navbar';

export default function App({ Component, pageProps }: AppProps) {
  return <div data-theme="cupcake">
    <Navbar />
    <div className="container mx-auto">
      <Component {...pageProps} />
    </div>
  </div>
}
