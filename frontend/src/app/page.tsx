"use client";

import { useEffect, useState } from "react";
import { api, apiList } from "@/lib/api";
import ProductCard from "@/components/ProductCard";

interface Product {
  id: string;
  name: string;
  price: number;
  image?: string;
}

export default function HomePage() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await apiList<Product>(
          `/products?page=0&size=12&sort=id`
        );
        setProducts(data);
      } catch (err: any) {
        alert(err.message);
      }
    };
    load();
  }, []);

  const addToCart = async (id: string) => {
    try {
      await api("/cart/items", {
        method: "POST",
        body: JSON.stringify({ productId: id, quantity: 1 }),
      });
      alert("Added to cart");
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
      {products.map((p) => (
        <ProductCard key={p.id} product={p} onAdd={() => addToCart(p.id)} />
      ))}
    </div>
  );
}
