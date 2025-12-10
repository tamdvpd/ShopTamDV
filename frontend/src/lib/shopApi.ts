import { api, apiList } from "./api";

export interface Product {
  id: string;
  name: string;
  price: number;
  image?: string;
  description?: string;
  category?: string;
}

export interface Category {
  id: string;
  name: string;
  description?: string;
}

export interface Order {
  id: string;
  status: string;
  total: number;
  customer: string;
  createdAt?: string;
}

export const fallbackProducts: Product[] = [
  {
    id: "1",
    name: "Tai nghe Bluetooth",
    price: 59.9,
    category: "Âm thanh",
    image:
      "https://images.unsplash.com/photo-1583394838336-acd977736f90?auto=format&fit=crop&w=600&q=80",
    description: "Tai nghe không dây với khả năng chống ồn chủ động.",
  },
  {
    id: "2",
    name: "Bàn phím cơ",
    price: 120,
    category: "Phụ kiện",
    image:
      "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=600&q=80",
    description: "Bàn phím switch brown mang lại cảm giác gõ êm ái.",
  },
  {
    id: "3",
    name: "Máy tính bảng",
    price: 350,
    category: "Công nghệ",
    image:
      "https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?auto=format&fit=crop&w=600&q=80",
    description: "Màn hình 10 inch, pin 12 giờ dành cho học tập và giải trí.",
  },
  {
    id: "4",
    name: "Smartwatch",
    price: 199,
    category: "Thiết bị đeo",
    image:
      "https://images.unsplash.com/photo-1520607162513-77705c0f0d4a?auto=format&fit=crop&w=600&q=80",
    description: "Theo dõi sức khỏe, thông báo và nghe gọi tiện lợi.",
  },
];

export const fallbackCategories: Category[] = [
  { id: "tech", name: "Công nghệ", description: "Điện thoại, máy tính bảng" },
  { id: "sound", name: "Âm thanh", description: "Loa, tai nghe" },
  { id: "home", name: "Gia dụng", description: "Thiết bị tiện ích cho gia đình" },
  { id: "fashion", name: "Thời trang", description: "Phụ kiện, quần áo" },
];

export const fallbackOrders: Order[] = [
  { id: "A123", status: "Đang xử lý", total: 250, customer: "Nguyễn Văn A" },
  { id: "B888", status: "Đang giao", total: 489, customer: "Trần Thị B" },
  { id: "C999", status: "Hoàn tất", total: 129, customer: "Lê Văn C" },
];

export async function fetchProducts() {
  try {
    return await apiList<Product>("/products");
  } catch (err) {
    console.warn("Using fallback products", err);
    return fallbackProducts;
  }
}

export async function fetchFeaturedProducts() {
  try {
    const data = await apiList<Product>("/products?page=0&size=8&sort=id");
    return data.length ? data : fallbackProducts;
  } catch (err) {
    console.warn("Using fallback featured products", err);
    return fallbackProducts;
  }
}

export async function fetchProductDetail(id: string) {
  try {
    return await api(`/products/${id}`);
  } catch (err) {
    const found = fallbackProducts.find((p) => p.id === id);
    if (found) return found;
    throw err;
  }
}

export async function fetchCategories() {
  try {
    const data = await apiList<Category>("/categories");
    return data.length ? data : fallbackCategories;
  } catch (err) {
    console.warn("Using fallback categories", err);
    return fallbackCategories;
  }
}

export async function fetchOrders() {
  try {
    const data = await apiList<Order>("/orders");
    return data.length ? data : fallbackOrders;
  } catch (err) {
    console.warn("Using fallback orders", err);
    return fallbackOrders;
  }
}
