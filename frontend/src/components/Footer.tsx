import Link from "next/link";
import { Facebook, Instagram, Phone } from "lucide-react";

export default function Footer() {
  return (
    <footer className="mt-12 border-t bg-white py-8">
      <div className="container-section grid gap-6 md:grid-cols-3 text-sm text-gray-600">
        <div className="space-y-2">
          <h3 className="text-base font-semibold text-gray-800">TamDv Shop</h3>
          <p>Shop online với những sản phẩm chất lượng và ưu đãi hấp dẫn.</p>
          <div className="flex items-center gap-2 text-gray-700">
            <Phone className="h-4 w-4" />
            <span>Hotline: 0123 456 789</span>
          </div>
        </div>
        <div>
          <h4 className="font-semibold text-gray-800 mb-2">Liên kết nhanh</h4>
          <ul className="space-y-1">
            <li>
              <Link className="hover:text-blue-600" href="/products">
                Danh sách sản phẩm
              </Link>
            </li>
            <li>
              <Link className="hover:text-blue-600" href="/cart">
                Giỏ hàng
              </Link>
            </li>
            <li>
              <Link className="hover:text-blue-600" href="/checkout">
                Thanh toán
              </Link>
            </li>
          </ul>
        </div>
        <div>
          <h4 className="font-semibold text-gray-800 mb-2">Kết nối</h4>
          <div className="flex items-center gap-3">
            <Link
              href="#"
              className="flex h-9 w-9 items-center justify-center rounded-full border hover:border-blue-500 hover:text-blue-600"
            >
              <Facebook className="h-4 w-4" />
            </Link>
            <Link
              href="#"
              className="flex h-9 w-9 items-center justify-center rounded-full border hover:border-pink-500 hover:text-pink-600"
            >
              <Instagram className="h-4 w-4" />
            </Link>
          </div>
        </div>
      </div>
    </footer>
  );
}
