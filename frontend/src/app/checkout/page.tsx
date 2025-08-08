"use client";

import { useState } from "react";
import { api } from "@/lib/api";
import { useToast } from "@/components/ui/use-toast";

export default function CheckoutPage() {
  const { toast } = useToast();
  const [form, setForm] = useState({ fullName: "", phone: "", address: "" });

  const submit = async () => {
    try {
      await api.post("/orders/checkout", form);
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
    }
  };

  return (
    <div className="max-w-md space-y-4">
      <input
        className="w-full border p-2"
        placeholder="Full Name"
        value={form.fullName}
        onChange={(e) => setForm({ ...form, fullName: e.target.value })}
      />
      <input
        className="w-full border p-2"
        placeholder="Phone"
        value={form.phone}
        onChange={(e) => setForm({ ...form, phone: e.target.value })}
      />
      <textarea
        className="w-full border p-2"
        placeholder="Address"
        value={form.address}
        onChange={(e) => setForm({ ...form, address: e.target.value })}
      />
      <button className="px-4 py-2 bg-blue-600 text-white" onClick={submit}>
        Checkout
      </button>
    </div>
  );
}
