"use client";

import { useEffect, useState } from "react";
import { api } from "@/lib/api";
import { useToast } from "@/components/ui/use-toast";

export default function ProductDetail({
  params,
}: {
  params: { id: string };
}) {
  const { toast } = useToast();
  const [product, setProduct] = useState<any>(null);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await api.get(`/products/${params.id}`);
        setProduct(data);
      } catch (err: any) {
        toast({ title: "Error", description: err.message, variant: "destructive" });
      }
    };
    load();
  }, [params.id]);

  const addToCart = async () => {
    try {
      await api.post(`/cart`, { productId: params.id, quantity: 1 });
    } catch (err: any) {
      toast({ title: "Error", description: err.message, variant: "destructive" });
    }
  };

  if (!product) return <div>Loading...</div>;

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">{product.name}</h1>
      <p>{product.description}</p>
      <p className="font-semibold">${product.price}</p>
      <button className="px-4 py-2 bg-green-600 text-white" onClick={addToCart}>
        Add to Cart
      </button>
    </div>
  );
}
