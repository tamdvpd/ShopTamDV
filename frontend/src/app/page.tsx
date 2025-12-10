"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { ArrowRight, ShieldCheck, Truck } from "lucide-react";
import ProductCard from "@/components/ProductCard";
import SectionHeader from "@/components/SectionHeader";
import CategoryCard from "@/components/CategoryCard";
import { Product, Category, fetchCategories, fetchFeaturedProducts } from "@/lib/shopApi";
import { api } from "@/lib/api";

export default function HomePage() {
  const [featured, setFeatured] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const load = async () => {
      setFeatured(await fetchFeaturedProducts());
      setCategories(await fetchCategories());
    };
    load();
  }, []);

  const addToCart = async (id: string) => {
    try {
      await api("/cart/items", {
        method: "POST",
        body: JSON.stringify({ productId: id, quantity: 1 }),
      });
      alert("Đã thêm vào giỏ hàng");
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div className="space-y-12">
      <section className="grid gap-10 rounded-3xl bg-gradient-to-r from-blue-50 via-white to-blue-50 px-6 py-12 shadow-sm lg:grid-cols-2 lg:items-center">
        <div className="space-y-5">
          <span className="inline-flex items-center gap-2 rounded-full bg-blue-100 px-4 py-2 text-xs font-semibold text-blue-700">
            Mua sắm dễ dàng cùng TamDv Shop
          </span>
          <h1 className="text-3xl font-bold leading-tight text-gray-900 sm:text-4xl">
            Sản phẩm công nghệ & phụ kiện hot nhất tuần này
          </h1>
          <p className="max-w-xl text-gray-600">
            Cập nhật nhanh các ưu đãi mới, giao hàng toàn quốc và hỗ trợ đổi trả trong 7 ngày.
          </p>
          <div className="flex flex-wrap gap-3">
            <Link
              href="/products"
              className="inline-flex items-center gap-2 rounded-xl bg-blue-600 px-5 py-3 text-sm font-medium text-white shadow hover:bg-blue-700"
            >
              Mua ngay <ArrowRight className="h-4 w-4" />
            </Link>
            <Link
              href="/cart"
              className="inline-flex items-center gap-2 rounded-xl border border-blue-100 px-5 py-3 text-sm font-medium text-blue-700 hover:border-blue-300"
            >
              Xem giỏ hàng
            </Link>
          </div>
          <div className="flex flex-wrap gap-4 text-sm text-gray-700">
            <div className="flex items-center gap-2">
              <Truck className="h-4 w-4 text-blue-600" />
              Giao nhanh 2h nội thành
            </div>
            <div className="flex items-center gap-2">
              <ShieldCheck className="h-4 w-4 text-blue-600" />
              Bảo hành chính hãng
            </div>
          </div>
        </div>
        <div className="grid grid-cols-2 gap-4">
          {featured.slice(0, 4).map((product) => (
            <div key={product.id} className="rounded-2xl bg-white p-4 shadow-md">
              <div className="aspect-square overflow-hidden rounded-xl bg-gray-50">
                {product.image ? (
                  <img src={product.image} alt={product.name} className="h-full w-full object-cover" />
                ) : (
                  <div className="flex h-full items-center justify-center text-gray-400">No image</div>
                )}
              </div>
              <p className="mt-3 line-clamp-1 text-sm font-semibold text-gray-900">{product.name}</p>
              <p className="text-sm text-blue-700">{product.price.toLocaleString("vi-VN")} đ</p>
            </div>
          ))}
        </div>
      </section>

      <section className="space-y-6">
        <SectionHeader
          title="Danh mục nổi bật"
          description="Khám phá sản phẩm theo nhu cầu của bạn"
          action={
            <Link href="/products" className="text-sm font-medium text-blue-700 hover:underline">
              Xem tất cả
            </Link>
          }
        />
        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
          {categories.map((cat) => (
            <CategoryCard key={cat.id} name={cat.name} description={cat.description} />
          ))}
        </div>
      </section>

      <section className="space-y-6">
        <SectionHeader
          title="Sản phẩm nổi bật"
          description="Chọn lựa sản phẩm chất lượng với giá tốt"
          action={
            <Link href="/products" className="text-sm font-medium text-blue-700 hover:underline">
              Xem tất cả sản phẩm
            </Link>
          }
        />
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {featured.map((product) => (
            <ProductCard key={product.id} product={product} onAdd={() => addToCart(product.id)} />
          ))}
        </div>
      </section>
    </div>
  );
}
