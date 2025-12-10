"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { ShoppingBag, ShoppingCart, User } from "lucide-react";
import { useState } from "react";
import { cn } from "@/lib/utils";

const links = [
  { href: "/", label: "Trang chủ" },
  { href: "/products", label: "Sản phẩm" },
  { href: "/cart", label: "Giỏ hàng" },
  { href: "/profile", label: "Tài khoản" },
];

export default function NavBar() {
  const pathname = usePathname();
  const [search, setSearch] = useState("");

  return (
    <header className="bg-white border-b sticky top-0 z-40 shadow-sm">
      <div className="container-section flex items-center gap-4 py-3">
        <Link href="/" className="flex items-center gap-2 font-semibold text-lg">
          <ShoppingBag className="h-6 w-6 text-blue-600" />
          <span>TamDv Shop</span>
        </Link>
        <div className="hidden md:flex items-center gap-1 text-sm text-gray-600">
          {links.map((link) => (
            <Link
              key={link.href}
              href={link.href}
              className={cn(
                "px-3 py-2 rounded-full transition-colors hover:text-blue-600",
                pathname === link.href && "bg-blue-50 text-blue-700"
              )}
            >
              {link.label}
            </Link>
          ))}
        </div>
        <div className="flex-1" />
        <div className="hidden md:flex items-center gap-3">
          <div className="relative">
            <input
              type="text"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Tìm sản phẩm..."
              className="w-64 rounded-full border px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            />
          </div>
          <Link
            href="/cart"
            className="flex items-center gap-2 rounded-full border px-3 py-2 hover:border-blue-200 hover:text-blue-700"
          >
            <ShoppingCart className="h-4 w-4" />
            <span className="text-sm">Giỏ hàng</span>
          </Link>
          <Link
            href="/login"
            className="flex items-center gap-2 rounded-full border px-3 py-2 hover:border-blue-200 hover:text-blue-700"
          >
            <User className="h-4 w-4" />
            <span className="text-sm">Đăng nhập</span>
          </Link>
        </div>
      </div>
      <div className="md:hidden border-t bg-gray-50 py-2">
        <div className="container-section flex gap-2 overflow-x-auto text-sm">
          {links.map((link) => (
            <Link
              key={link.href}
              href={link.href}
              className={cn(
                "rounded-full border px-3 py-2",
                pathname === link.href ? "border-blue-500 text-blue-600" : "border-gray-200"
              )}
            >
              {link.label}
            </Link>
          ))}
        </div>
      </div>
    </header>
  );
}
