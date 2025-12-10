import Link from "next/link";
import { ShoppingCart } from "lucide-react";
import { Product } from "@/lib/shopApi";

export default function ProductCard({
  product,
  onAdd,
}: {
  product: Product;
  onAdd: () => void;
}) {
  return (
    <div className="group flex flex-col overflow-hidden rounded-2xl border bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-lg">
      <Link href={`/product/${product.id}`} className="block overflow-hidden">
        <div className="aspect-[4/3] w-full bg-gray-100">
          {product.image ? (
            <img
              src={product.image}
              alt={product.name}
              className="h-full w-full object-cover transition duration-700 group-hover:scale-105"
            />
          ) : (
            <div className="flex h-full items-center justify-center text-gray-400">No image</div>
          )}
        </div>
      </Link>
      <div className="flex flex-1 flex-col gap-2 p-4">
        <div className="flex items-start justify-between gap-2">
          <Link href={`/product/${product.id}`} className="font-semibold text-gray-900 line-clamp-2">
            {product.name}
          </Link>
          {product.category && (
            <span className="rounded-full bg-blue-50 px-3 py-1 text-xs text-blue-700">{product.category}</span>
          )}
        </div>
        <p className="text-lg font-semibold text-blue-700">{product.price.toLocaleString("vi-VN")} đ</p>
        <button
          onClick={onAdd}
          className="mt-auto inline-flex items-center justify-center gap-2 rounded-xl bg-blue-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-blue-700"
        >
          <ShoppingCart className="h-4 w-4" />
          Thêm vào giỏ
        </button>
      </div>
    </div>
  );
}
