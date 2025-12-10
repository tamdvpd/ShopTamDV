import type { Metadata } from "next";
import "./globals.css";
import NavBar from "@/components/NavBar";
import Footer from "@/components/Footer";
import { Toaster } from "@/components/ui/toaster";
import { ToastProvider } from "@/components/ui/use-toast";

export const metadata: Metadata = {
  title: "TamDv Shop",
  description: "Mua sắm online cùng TamDv Shop",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <ToastProvider>
          <NavBar />
          <main className="container-section py-8">{children}</main>
          <Footer />
          <Toaster />
        </ToastProvider>
      </body>
    </html>
  );
}
