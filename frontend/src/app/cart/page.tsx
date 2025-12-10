"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { ShoppingBag } from "lucide-react";
import { api } from "@/lib/api";
import CartItemRow from "@/components/CartItemRow";

export default function CartPage() {
  const [items, setItems] = useState<any[]>([]);

  const load = async () => {
    try {
      const data = await api("/cart");
      setItems(data.items || data || []);
    } catch (err: any) {
      setItems([]);
      console.error(err);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const subtotal = items.reduce(
    (sum, i) => sum + i.product.price * i.quantity,
    0
  );

  return (
    <div className="space-y-8">
      <div className="flex items-center gap-3">
        <ShoppingBag className="h-6 w-6 text-blue-600" />
        <div>
          <h1 className="text-2xl font-semibold text-gray-900">Giỏ hàng</h1>
          <p className="text-sm text-gray-500">Kiểm tra lại sản phẩm trước khi thanh toán</p>
        </div>
      </div>

      <div className="grid gap-6 lg:grid-cols-[2fr_1fr]">
        <div className="space-y-4">
          {items.length === 0 && (
            <div className="rounded-2xl border bg-white p-6 text-center text-gray-600">
              Giỏ hàng trống. <Link href="/products" className="text-blue-600 underline">Mua sắm ngay</Link>
            </div>
          )}
          {items.map((item) => (
            <CartItemRow key={item.id} item={item} onChanged={load} />
          ))}
        </div>

        <div className="rounded-2xl border bg-white p-6 shadow-sm">
          <h2 className="text-lg font-semibold text-gray-900">Tóm tắt đơn hàng</h2>
          <div className="mt-4 space-y-2 text-sm">
            <div className="flex items-center justify-between text-gray-600">
              <span>Tạm tính</span>
              <span>{subtotal.toLocaleString("vi-VN")} đ</span>
            </div>
            <div className="flex items-center justify-between text-gray-600">
              <span>Phí vận chuyển</span>
              <span>Miễn phí</span>
            </div>
            <div className="flex items-center justify-between border-t pt-3 font-semibold text-gray-900">
              <span>Tổng cộng</span>
              <span>{subtotal.toLocaleString("vi-VN")} đ</span>
            </div>
          </div>
          <Link
            href="/checkout"
            className="mt-6 inline-flex w-full items-center justify-center rounded-xl bg-blue-600 px-4 py-3 font-medium text-white transition hover:bg-blue-700"
          >
            Tiến hành thanh toán
          </Link>
        </div>
      </div>
    </div>
  );
}
