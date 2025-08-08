"use client";

import { api } from "@/lib/api";

export default function CartItemRow({
  item,
  onChanged,
}: {
  item: any;
  onChanged: () => void;
}) {
  const changeQty = async (qty: number) => {
    try {
      await api(`/cart/items/${item.id}`, {
        method: "PUT",
        body: JSON.stringify({ quantity: qty }),
      });
      onChanged();
    } catch (err: any) {
      alert(err.message);
    }
  };

  const remove = async () => {
    try {
      await api(`/cart/items/${item.id}`, { method: "DELETE" });
      onChanged();
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div className="flex items-center justify-between border-b py-2">
      <div>
        <div className="font-semibold">{item.product.name}</div>
        <div>${item.product.price}</div>
      </div>
      <div className="flex items-center space-x-2">
        <button
          className="px-2 border"
          onClick={() => changeQty(Math.max(1, item.quantity - 1))}
        >
          -
        </button>
        <span>{item.quantity}</span>
        <button
          className="px-2 border"
          onClick={() => changeQty(item.quantity + 1)}
        >
          +
        </button>
        <button className="text-red-600" onClick={remove}>
          Remove
        </button>
      </div>
    </div>
  );
}
