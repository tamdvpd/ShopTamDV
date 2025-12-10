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
    <div className="flex flex-col gap-3 rounded-xl border bg-white p-4 shadow-sm sm:flex-row sm:items-center sm:justify-between">
      <div className="flex items-center gap-3">
        {item.product.image && (
          <img
            src={item.product.image}
            alt={item.product.name}
            className="h-16 w-16 rounded-lg object-cover"
          />
        )}
        <div>
          <div className="font-semibold text-gray-900">{item.product.name}</div>
          <div className="text-sm text-gray-500">{item.product.price.toLocaleString("vi-VN")} đ</div>
        </div>
      </div>
      <div className="flex items-center gap-3">
        <div className="flex items-center rounded-lg border bg-gray-50 text-sm">
          <button
            className="px-3 py-2 text-gray-600"
            onClick={() => changeQty(Math.max(1, item.quantity - 1))}
          >
            -
          </button>
          <span className="min-w-[40px] text-center font-semibold">{item.quantity}</span>
          <button className="px-3 py-2 text-gray-600" onClick={() => changeQty(item.quantity + 1)}>
            +
          </button>
        </div>
        <button
          className="text-sm font-medium text-red-600 hover:underline"
          onClick={remove}
        >
          Xóa
        </button>
      </div>
    </div>
  );
}
