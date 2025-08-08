"use client";

import { useState } from "react";
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
      <div className="space-y-2">
        <div>Order ID: {order.orderId || order.id}</div>
        <div>Total: ${order.total}</div>
        <div>Status: {order.status}</div>
        <a href="/" className="text-blue-600 underline">
          Home
        </a>
      </div>
    );
  }

  return (
    <form className="max-w-md space-y-4" onSubmit={submit}>
      <input
        className="w-full border p-2"
        placeholder="Full Name"
        value={form.fullName}
        onChange={(e) => setForm({ ...form, fullName: e.target.value })}
        required
      />
      <input
        className="w-full border p-2"
        placeholder="Phone"
        value={form.phone}
        onChange={(e) => setForm({ ...form, phone: e.target.value })}
        required
      />
      <textarea
        className="w-full border p-2"
        placeholder="Address"
        value={form.address}
        onChange={(e) => setForm({ ...form, address: e.target.value })}
        required
      />
      <button type="submit" className="px-4 py-2 bg-blue-600 text-white">
        Checkout
      </button>
    </form>
  );
}
