"use client";

import { useEffect, useMemo, useState } from "react";
import { Filter, SortAsc } from "lucide-react";
import ProductCard from "@/components/ProductCard";
import SectionHeader from "@/components/SectionHeader";
import { Product, fetchProducts } from "@/lib/shopApi";
import { api } from "@/lib/api";

export default function ProductListPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [search, setSearch] = useState("");
  const [sort, setSort] = useState("name");

  useEffect(() => {
    const load = async () => {
      setProducts(await fetchProducts());
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

  const filtered = useMemo(() => {
    let result = products;
    if (search) {
      result = result.filter((p) =>
        p.name.toLowerCase().includes(search.toLowerCase())
      );
    }
    if (sort === "price") {
      result = [...result].sort((a, b) => a.price - b.price);
    } else {
      result = [...result].sort((a, b) => a.name.localeCompare(b.name));
    }
    return result;
  }, [products, search, sort]);

  return (
    <div className="space-y-8">
      <SectionHeader
        title="Danh sách sản phẩm"
        description="Bộ sưu tập đa dạng, cập nhật mỗi ngày"
        action={<span className="text-xs text-gray-500">{filtered.length} sản phẩm</span>}
      />
      <div className="flex flex-wrap items-center gap-3 rounded-2xl bg-white p-4 shadow-sm">
        <div className="flex items-center gap-2 rounded-xl border px-3 py-2">
          <Filter className="h-4 w-4 text-blue-600" />
          <input
            type="text"
            placeholder="Tìm kiếm sản phẩm"
            className="w-48 text-sm focus:outline-none"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>
        <div className="flex items-center gap-2 text-sm text-gray-600">
          <SortAsc className="h-4 w-4 text-blue-600" /> Sắp xếp:
          <select
            className="rounded-lg border px-3 py-2 text-sm"
            value={sort}
            onChange={(e) => setSort(e.target.value)}
          >
            <option value="name">Tên sản phẩm</option>
            <option value="price">Giá thấp đến cao</option>
          </select>
        </div>
      </div>
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {filtered.map((product) => (
          <ProductCard key={product.id} product={product} onAdd={() => addToCart(product.id)} />
        ))}
      </div>
    </div>
  );
}
