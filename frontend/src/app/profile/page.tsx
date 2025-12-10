"use client";

import { useState } from "react";
import { User, PackageSearch } from "lucide-react";
import SectionHeader from "@/components/SectionHeader";

const sampleOrders = [
  { id: "DH-1201", status: "Hoàn tất", total: 890000, date: "12/03/2024" },
  { id: "DH-1202", status: "Đang giao", total: 1290000, date: "25/03/2024" },
];

export default function ProfilePage() {
  const [form, setForm] = useState({ fullName: "Nguyễn Văn A", email: "user@example.com", phone: "0988 222 111" });

  const submit = (e: React.FormEvent) => {
    e.preventDefault();
    alert("Cập nhật thông tin thành công");
  };

  return (
    <div className="space-y-8">
      <SectionHeader title="Tài khoản của bạn" description="Quản lý thông tin cá nhân và đơn hàng" />
      <div className="grid gap-6 lg:grid-cols-3">
        <form className="space-y-3 rounded-2xl border bg-white p-6 shadow-sm lg:col-span-2" onSubmit={submit}>
          <div className="flex items-center gap-2 text-blue-700">
            <User className="h-5 w-5" /> Thông tin cá nhân
          </div>
          <input
            className="w-full rounded-lg border px-4 py-3 text-sm"
            placeholder="Họ và tên"
            value={form.fullName}
            onChange={(e) => setForm({ ...form, fullName: e.target.value })}
          />
          <input
            className="w-full rounded-lg border px-4 py-3 text-sm"
            placeholder="Email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
          />
          <input
            className="w-full rounded-lg border px-4 py-3 text-sm"
            placeholder="Số điện thoại"
            value={form.phone}
            onChange={(e) => setForm({ ...form, phone: e.target.value })}
          />
          <button
            type="submit"
            className="rounded-xl bg-blue-600 px-4 py-3 text-sm font-medium text-white transition hover:bg-blue-700"
          >
            Lưu thay đổi
          </button>
        </form>
        <div className="space-y-3 rounded-2xl border bg-white p-6 shadow-sm">
          <div className="flex items-center gap-2 text-blue-700">
            <PackageSearch className="h-5 w-5" /> Đơn hàng gần đây
          </div>
          <div className="space-y-3 text-sm text-gray-700">
            {sampleOrders.map((order) => (
              <div key={order.id} className="rounded-xl border p-3">
                <div className="flex items-center justify-between">
                  <span className="font-semibold text-gray-900">{order.id}</span>
                  <span className="text-xs rounded-full bg-green-50 px-3 py-1 text-green-700">{order.status}</span>
                </div>
                <div className="mt-1 text-gray-600">Ngày: {order.date}</div>
                <div className="text-gray-600">Tổng: {order.total.toLocaleString("vi-VN")} đ</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
