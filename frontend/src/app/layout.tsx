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
          <nav className="flex items-center p-4 border-b gap-4">
            <Link href="/" className="font-bold text-xl">
              Shop
            </Link>
            <input
              type="text"
              placeholder="Search..."
              className="flex-1 border p-2"
            />
            <div className="space-x-4 whitespace-nowrap">
              <Link href="/cart">Cart</Link>
              <Link href="/login">Login</Link>
              <Link href="/register">Register</Link>
            </div>
          </nav>
          <main className="p-4">{children}</main>
          <Toaster />
        </ToastProvider>
      </body>
    </html>
  );
}
