"use client";

import { useEffect, useState } from "react";
import { BarChart3, ShoppingBag, Users } from "lucide-react";
import StatCard from "@/components/StatCard";
import SectionHeader from "@/components/SectionHeader";
import { fetchOrders, fetchProducts, Order, Product } from "@/lib/shopApi";

export default function AdminDashboardPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    const load = async () => {
      setProducts(await fetchProducts());
      setOrders(await fetchOrders());
    };
    load();
  }, []);

  const revenue = orders.reduce((sum, o) => sum + o.total, 0);

  return (
    <div className="space-y-8">
      <SectionHeader
        title="Bảng điều khiển"
        description="Tổng quan hiệu suất cửa hàng"
        action={<span className="text-xs text-gray-500">{new Date().toLocaleDateString("vi-VN")}</span>}
      />
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        <StatCard label="Sản phẩm" value={products.length} icon={ShoppingBag} trend="+3 sản phẩm mới" />
        <StatCard label="Đơn hàng" value={orders.length} icon={BarChart3} trend="+12% so với tuần trước" />
        <StatCard label="Khách hàng" value={1280} icon={Users} trend="+5 khách hàng hôm nay" />
      </div>
      <div className="grid gap-6 lg:grid-cols-2">
        <div className="rounded-2xl border bg-white p-6 shadow-sm">
          <h3 className="font-semibold text-gray-900">Đơn hàng gần đây</h3>
          <div className="mt-4 space-y-3 text-sm text-gray-700">
            {orders.map((order) => (
              <div key={order.id} className="flex items-center justify-between rounded-xl border p-3">
                <div>
                  <p className="font-semibold text-gray-900">{order.id}</p>
                  <p className="text-xs text-gray-500">Khách: {order.customer}</p>
                </div>
                <div className="text-right">
                  <div className="font-semibold text-gray-900">{order.total.toLocaleString("vi-VN")} đ</div>
                  <div className="text-xs text-green-700">{order.status}</div>
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className="rounded-2xl border bg-white p-6 shadow-sm">
          <h3 className="font-semibold text-gray-900">Sản phẩm bán chạy</h3>
          <div className="mt-4 space-y-3 text-sm text-gray-700">
            {products.slice(0, 4).map((product) => (
              <div key={product.id} className="flex items-center justify-between rounded-xl border p-3">
                <span className="font-semibold text-gray-900">{product.name}</span>
                <span className="text-blue-700">{product.price.toLocaleString("vi-VN")} đ</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
