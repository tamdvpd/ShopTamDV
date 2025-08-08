import type { Metadata } from "next";
import Link from "next/link";
import "./globals.css";
import { Toaster } from "@/components/ui/toaster";
import { ToastProvider } from "@/components/ui/use-toast";

export const metadata: Metadata = {
  title: "Shop",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className="min-h-screen bg-white">
        <ToastProvider>
          <nav className="flex items-center justify-between p-4 border-b">
            <Link href="/" className="font-bold text-xl">
              Shop
            </Link>
            <div className="space-x-4">
              <Link href="/cart">Cart</Link>
              <Link href="/login">Login</Link>
            </div>
          </nav>
          <main className="p-4">{children}</main>
          <Toaster />
        </ToastProvider>
      </body>
    </html>
  );
}
