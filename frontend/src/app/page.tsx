"use client";

import { useEffect, useState } from "react";
import { api } from "@/lib/api";
import ProductCard from "@/components/ProductCard";
import { useToast } from "@/components/ui/use-toast";

export default function HomePage() {
  const { toast } = useToast();
  const [keyword, setKeyword] = useState("");
  const [products, setProducts] = useState<any[]>([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    fetchProducts();
  }, [page]);

  const fetchProducts = async () => {
    try {
      const data = await api.get(
        `/products?keyword=${encodeURIComponent(keyword)}&page=${page}&size=12`
      );
      setProducts(data.items || data);
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
    }
  };

  const onSearch = () => {
    setPage(0);
    fetchProducts();
  };

  return (
    <div className="space-y-4">
      <div className="flex space-x-2">
        <input
          className="border p-2 flex-1"
          placeholder="Search products"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <button className="px-4 py-2 bg-blue-600 text-white" onClick={onSearch}>
          Search
        </button>
      </div>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {products.map((p) => (
          <ProductCard key={p.id} product={p} />
        ))}
      </div>
    </div>
  );
}
