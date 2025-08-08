import Link from "next/link";

export default function ProductCard({
  product,
}: {
  product: any;
}) {
  return (
    <Link
      href={`/product/${product.id}`}
      className="border p-4 rounded block space-y-2"
    >
      <div className="font-semibold">{product.name}</div>
      <div>${product.price}</div>
    </Link>
  );
}
