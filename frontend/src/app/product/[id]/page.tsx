"use client";

import { useEffect, useState } from "react";
import { api } from "@/lib/api";

export default function ProductDetail({
  params,
}: {
  params: { id: string };
}) {
  const [product, setProduct] = useState<any>(null);
  const [qty, setQty] = useState(1);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await api(`/products/${params.id}`);
        setProduct(data);
      } catch (err: any) {
        alert(err.message);
      }
    };
    load();
  }, [params.id]);

  const addToCart = async () => {
    try {
      await api("/cart/items", {
        method: "POST",
        body: JSON.stringify({ productId: params.id, quantity: qty }),
      });
      alert("Added to cart");
    } catch (err: any) {
      alert(err.message);
    }
  };

  if (!product) return <div>Loading...</div>;

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">{product.name}</h1>
      <p>{product.description}</p>
      <p className="font-semibold">${product.price}</p>
      <div className="flex items-center space-x-2">
        <input
          type="number"
          min={1}
          value={qty}
          onChange={(e) => setQty(parseInt(e.target.value))}
          className="border p-1 w-20"
        />
        <button
          className="px-4 py-2 bg-green-600 text-white"
          onClick={addToCart}
        >
          Add to cart
        </button>
      </div>
    </div>
  );
}
