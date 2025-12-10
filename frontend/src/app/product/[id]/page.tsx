"use client";

import { useEffect, useState } from "react";
import { api } from "@/lib/api";
import { fetchProductDetail, Product } from "@/lib/shopApi";
import { CheckCircle, ShoppingCart } from "lucide-react";

export default function ProductDetail({ params }: { params: { id: string } }) {
  const [product, setProduct] = useState<Product | null>(null);
  const [qty, setQty] = useState(1);

  useEffect(() => {
    const load = async () => {
      setProduct(await fetchProductDetail(params.id));
    };
    load();
  }, [params.id]);

  const addToCart = async () => {
    try {
      await api("/cart/items", {
        method: "POST",
        body: JSON.stringify({ productId: params.id, quantity: qty }),
      });
      alert("Đã thêm vào giỏ hàng");
    } catch (err: any) {
      alert(err.message);
    }
  };

  if (!product) return <div>Đang tải...</div>;

  return (
    <div className="grid gap-10 lg:grid-cols-2">
      <div className="rounded-3xl border bg-white p-6 shadow-sm">
        <div className="aspect-square overflow-hidden rounded-2xl bg-gray-100">
          {product.image ? (
            <img src={product.image} alt={product.name} className="h-full w-full object-cover" />
          ) : (
            <div className="flex h-full items-center justify-center text-gray-400">No image</div>
          )}
        </div>
      </div>
      <div className="space-y-6">
        <div className="space-y-2">
          <h1 className="text-3xl font-bold text-gray-900">{product.name}</h1>
          <p className="text-lg font-semibold text-blue-700">{product.price.toLocaleString("vi-VN")} đ</p>
        </div>
        <p className="text-gray-600">{product.description || "Mô tả đang được cập nhật."}</p>
        <div className="flex items-center gap-3 text-sm text-green-700">
          <CheckCircle className="h-4 w-4" /> Hàng sẵn kho - giao nhanh
        </div>
        <div className="flex items-center gap-4">
          <div className="flex items-center rounded-lg border bg-gray-50">
            <button
              className="px-3 py-2 text-gray-600"
              onClick={() => setQty((q) => Math.max(1, q - 1))}
            >
              -
            </button>
            <span className="min-w-[40px] text-center font-semibold">{qty}</span>
            <button className="px-3 py-2 text-gray-600" onClick={() => setQty((q) => q + 1)}>
              +
            </button>
          </div>
          <button
            className="inline-flex items-center gap-2 rounded-xl bg-blue-600 px-6 py-3 font-medium text-white transition hover:bg-blue-700"
            onClick={addToCart}
          >
            <ShoppingCart className="h-5 w-5" /> Thêm vào giỏ
          </button>
        </div>
        <div className="rounded-2xl border bg-white p-4 shadow-sm">
          <h3 className="font-semibold text-gray-900">Chính sách</h3>
          <ul className="mt-2 list-disc space-y-1 pl-5 text-sm text-gray-600">
            <li>Đổi trả trong 7 ngày nếu sản phẩm lỗi.</li>
            <li>Bảo hành chính hãng toàn quốc.</li>
            <li>Hỗ trợ trả góp qua thẻ tín dụng.</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
