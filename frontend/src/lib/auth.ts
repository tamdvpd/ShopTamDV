import { api } from "./api";

const TOKEN_KEY = "accessToken";

export function getToken(): string | null {
  if (typeof window === "undefined") return null;
  return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token: string) {
  if (typeof window === "undefined") return;
  localStorage.setItem(TOKEN_KEY, token);
  document.cookie = `${TOKEN_KEY}=${token}; path=/`;
}

export function clearToken() {
  if (typeof window === "undefined") return;
  localStorage.removeItem(TOKEN_KEY);
  document.cookie = `${TOKEN_KEY}=; Max-Age=0; path=/`;
}

export async function login(email: string, password: string) {
  const data = await api("/auth/login", {
    method: "POST",
    body: JSON.stringify({ email, password }),
  });
  if (data?.accessToken) setToken(data.accessToken);
  return data;
}

export async function signup(payload: {
  fullName: string;
  email: string;
  password: string;
}) {
  const data = await api("/auth/signup", {
    method: "POST",
    body: JSON.stringify(payload),
  });
  if (data?.accessToken) setToken(data.accessToken);
  return data;
}

export function authHeader(): Record<string, string> {
  const token = getToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
}
