"use client";

import { api } from "@/lib/api";
import { useToast } from "@/components/ui/use-toast";

export default function CartItemRow({
  item,
  onChanged,
}: {
  item: any;
  onChanged: () => void;
}) {
  const { toast } = useToast();

  const updateQty = async (qty: number) => {
    try {
      await api.put(`/cart/${item.id}`, { quantity: qty });
      onChanged();
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
    }
  };

  const remove = async () => {
    try {
      await api.delete(`/cart/${item.id}`);
      onChanged();
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
    }
  };

  return (
    <div className="flex items-center justify-between border-b py-2">
      <div>
        <div className="font-semibold">{item.product.name}</div>
        <div>${item.product.price}</div>
      </div>
      <div className="flex items-center space-x-2">
        <input
          type="number"
          min={1}
          value={item.quantity}
          onChange={(e) => updateQty(parseInt(e.target.value))}
          className="w-16 border p-1"
        />
        <button className="text-red-600" onClick={remove}>
          Remove
        </button>
      </div>
    </div>
  );
}
