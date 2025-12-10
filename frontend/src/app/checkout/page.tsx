"use client";

import { useState } from "react";
import Link from "next/link";
import { CreditCard, MapPin } from "lucide-react";
import { api } from "@/lib/api";

export default function CheckoutPage() {
  const [form, setForm] = useState({ fullName: "", phone: "", address: "" });
  const [order, setOrder] = useState<any>(null);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const data = await api("/orders/checkout", {
        method: "POST",
        body: JSON.stringify(form),
      });
      setOrder(data);
    } catch (err: any) {
      alert(err.message);
    }
  };

  if (order) {
    return (
      <div className="rounded-2xl border bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">Đặt hàng thành công</h2>
        <div className="mt-4 space-y-2 text-sm text-gray-700">
          <div>Mã đơn: {order.orderId || order.id}</div>
          <div>Tổng tiền: {order.total?.toLocaleString("vi-VN")} đ</div>
          <div>Trạng thái: {order.status}</div>
        </div>
        <Link href="/" className="mt-6 inline-block text-blue-700 hover:underline">
          Về trang chủ
        </Link>
      </div>
    );
  }

  return (
    <div className="grid gap-8 lg:grid-cols-2">
      <form className="space-y-4 rounded-2xl border bg-white p-6 shadow-sm" onSubmit={submit}>
        <div className="flex items-center gap-2 text-blue-700">
          <MapPin className="h-5 w-5" /> Thông tin giao hàng
        </div>
        <input
          className="w-full rounded-lg border px-4 py-3 text-sm"
          placeholder="Họ và tên"
          value={form.fullName}
          onChange={(e) => setForm({ ...form, fullName: e.target.value })}
          required
        />
        <input
          className="w-full rounded-lg border px-4 py-3 text-sm"
          placeholder="Số điện thoại"
          value={form.phone}
          onChange={(e) => setForm({ ...form, phone: e.target.value })}
          required
        />
        <textarea
          className="w-full rounded-lg border px-4 py-3 text-sm"
          placeholder="Địa chỉ giao hàng"
          value={form.address}
          onChange={(e) => setForm({ ...form, address: e.target.value })}
          required
        />
        <button
          type="submit"
          className="w-full rounded-xl bg-blue-600 px-4 py-3 font-medium text-white transition hover:bg-blue-700"
        >
          Xác nhận đặt hàng
        </button>
      </form>
      <div className="space-y-4 rounded-2xl border bg-white p-6 shadow-sm">
        <div className="flex items-center gap-2 text-blue-700">
          <CreditCard className="h-5 w-5" /> Phương thức thanh toán
        </div>
        <div className="rounded-xl border p-4 text-sm text-gray-700">
          <div className="font-semibold text-gray-900">Thanh toán khi nhận hàng (COD)</div>
          <p className="mt-2 text-gray-600">Nhân viên giao hàng sẽ liên hệ trước khi giao.</p>
        </div>
        <div className="rounded-xl border p-4 text-sm text-gray-700">
          <div className="font-semibold text-gray-900">Thẻ tín dụng/ghi nợ</div>
          <p className="mt-2 text-gray-600">Tạm thời khóa để bảo trì, vui lòng chọn COD.</p>
        </div>
      </div>
    </div>
  );
}
