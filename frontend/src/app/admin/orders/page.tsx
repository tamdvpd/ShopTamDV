"use client";

import { useEffect, useState } from "react";
import { CheckCircle, Truck } from "lucide-react";
import SectionHeader from "@/components/SectionHeader";
import { api } from "@/lib/api";
import { fetchOrders, Order } from "@/lib/shopApi";

export default function AdminOrderManager() {
  const [orders, setOrders] = useState<Order[]>([]);

  const load = async () => {
    setOrders(await fetchOrders());
  };

  useEffect(() => {
    load();
  }, []);

  const updateStatus = async (id: string, status: string) => {
    try {
      await api(`/orders/${id}`, {
        method: "PUT",
        body: JSON.stringify({ status }),
      });
      load();
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div className="space-y-6">
      <SectionHeader
        title="Quản lý đơn hàng"
        description="Theo dõi và cập nhật trạng thái đơn"
      />
      <div className="overflow-hidden rounded-2xl border bg-white shadow-sm">
        <table className="min-w-full text-left text-sm text-gray-700">
          <thead className="bg-gray-50 text-xs uppercase text-gray-500">
            <tr>
              <th className="px-4 py-3">Mã đơn</th>
              <th className="px-4 py-3">Khách hàng</th>
              <th className="px-4 py-3">Tổng tiền</th>
              <th className="px-4 py-3">Trạng thái</th>
              <th className="px-4 py-3 text-right">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id} className="border-t">
                <td className="px-4 py-3 font-semibold text-gray-900">{order.id}</td>
                <td className="px-4 py-3">{order.customer}</td>
                <td className="px-4 py-3">{order.total.toLocaleString("vi-VN")} đ</td>
                <td className="px-4 py-3">
                  <span className="rounded-full bg-green-50 px-3 py-1 text-xs text-green-700">{order.status}</span>
                </td>
                <td className="px-4 py-3 text-right space-x-2">
                  <button
                    className="inline-flex items-center gap-1 rounded-lg border px-3 py-1 text-xs text-blue-700 hover:border-blue-400"
                    onClick={() => updateStatus(order.id, "Đang giao")}
                  >
                    <Truck className="h-4 w-4" /> Giao hàng
                  </button>
                  <button
                    className="inline-flex items-center gap-1 rounded-lg border px-3 py-1 text-xs text-green-700 hover:border-green-400"
                    onClick={() => updateStatus(order.id, "Hoàn tất")}
                  >
                    <CheckCircle className="h-4 w-4" /> Hoàn tất
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
