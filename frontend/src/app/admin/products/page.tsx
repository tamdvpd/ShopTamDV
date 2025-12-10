"use client";

import { useEffect, useState } from "react";
import { Edit, Plus, Trash } from "lucide-react";
import SectionHeader from "@/components/SectionHeader";
import { api } from "@/lib/api";
import { fetchProducts, Product } from "@/lib/shopApi";

export default function AdminProductManager() {
  const [products, setProducts] = useState<Product[]>([]);

  const load = async () => {
    setProducts(await fetchProducts());
  };

  useEffect(() => {
    load();
  }, []);

  const remove = async (id: string) => {
    try {
      await api(`/products/${id}`, { method: "DELETE" });
      load();
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div className="space-y-6">
      <SectionHeader
        title="Quản lý sản phẩm"
        description="Thêm, sửa, xóa sản phẩm trong cửa hàng"
        action={
          <button className="inline-flex items-center gap-2 rounded-xl bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700">
            <Plus className="h-4 w-4" /> Thêm sản phẩm
          </button>
        }
      />
      <div className="overflow-hidden rounded-2xl border bg-white shadow-sm">
        <table className="min-w-full text-left text-sm text-gray-700">
          <thead className="bg-gray-50 text-xs uppercase text-gray-500">
            <tr>
              <th className="px-4 py-3">Tên sản phẩm</th>
              <th className="px-4 py-3">Danh mục</th>
              <th className="px-4 py-3">Giá</th>
              <th className="px-4 py-3 text-right">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr key={product.id} className="border-t">
                <td className="px-4 py-3 font-semibold text-gray-900">{product.name}</td>
                <td className="px-4 py-3">{product.category || "N/A"}</td>
                <td className="px-4 py-3">{product.price.toLocaleString("vi-VN")} đ</td>
                <td className="px-4 py-3 text-right">
                  <button className="mr-3 inline-flex items-center gap-1 rounded-lg border px-3 py-1 text-xs text-gray-700 hover:border-blue-400">
                    <Edit className="h-4 w-4" /> Sửa
                  </button>
                  <button
                    className="inline-flex items-center gap-1 rounded-lg border px-3 py-1 text-xs text-red-600 hover:border-red-400"
                    onClick={() => remove(product.id)}
                  >
                    <Trash className="h-4 w-4" /> Xóa
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
