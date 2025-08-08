import Link from "next/link";

export default function ProductCard({
  product,
  onAdd,
}: {
  product: any;
  onAdd: () => void;
}) {
  return (
    <div className="border p-4 rounded space-y-2">
      <Link href={`/product/${product.id}`} className="block space-y-2">
        {product.image && (
          <img
            src={product.image}
            alt={product.name}
            className="w-full h-40 object-cover"
          />
        )}
        <div className="font-semibold">{product.name}</div>
        <div>${product.price}</div>
      </Link>
      <button
        className="px-2 py-1 bg-green-600 text-white w-full"
        onClick={onAdd}
      >
        Add to cart
      </button>
    </div>
  );
}
