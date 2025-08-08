"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { signup } from "@/lib/auth";
import { useToast } from "@/components/ui/use-toast";

export default function RegisterPage() {
  const [form, setForm] = useState({ fullName: "", email: "", password: "" });
  const router = useRouter();
  const { toast } = useToast();

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await signup(form);
      toast({ title: "Registered" });
      router.push("/");
    } catch (err: any) {
      toast({ title: "Register failed", description: err.message, variant: "destructive" });
    }
  };

  return (
    <form className="max-w-sm space-y-4" onSubmit={submit}>
      <input
        className="w-full border p-2"
        placeholder="Full Name"
        value={form.fullName}
        onChange={(e) => setForm({ ...form, fullName: e.target.value })}
        required
      />
      <input
        type="email"
        className="w-full border p-2"
        placeholder="Email"
        value={form.email}
        onChange={(e) => setForm({ ...form, email: e.target.value })}
        required
      />
      <input
        type="password"
        className="w-full border p-2"
        placeholder="Password"
        value={form.password}
        onChange={(e) => setForm({ ...form, password: e.target.value })}
        required
      />
      <button type="submit" className="px-4 py-2 bg-blue-600 text-white w-full">
        Register
      </button>
    </form>
  );
}
