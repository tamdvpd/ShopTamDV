"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { api } from "@/lib/api";
import CartItemRow from "@/components/CartItemRow";

export default function CartPage() {
  const [items, setItems] = useState<any[]>([]);

  const load = async () => {
    try {
      const data = await api("/cart");
      setItems(data.items || data);
    } catch (err: any) {
      alert(err.message);
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
    <div className="space-y-4">
      {items.map((item) => (
        <CartItemRow key={item.id} item={item} onChanged={load} />
      ))}
      <div className="text-right space-y-1">
        <div>Subtotal: ${subtotal.toFixed(2)}</div>
        <div>Total: ${subtotal.toFixed(2)}</div>
        <Link
          href="/checkout"
          className="inline-block mt-2 px-4 py-2 bg-blue-600 text-white"
        >
          Checkout
        </Link>
      </div>
    </div>
  );
}
