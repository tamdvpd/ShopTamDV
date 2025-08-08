"use client";

import { useEffect, useState } from "react";
import { api } from "@/lib/api";
import CartItemRow from "@/components/CartItemRow";
import { useToast } from "@/components/ui/use-toast";

export default function CartPage() {
  const { toast } = useToast();
  const [items, setItems] = useState<any[]>([]);

  const load = async () => {
    try {
      const data = await api.get("/cart");
      setItems(data.items || data);
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
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
      </div>
    </div>
  );
}
