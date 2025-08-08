export async function api(path: string, init: RequestInit = {}) {
  const base = process.env.NEXT_PUBLIC_API_URL || "";
  const headers = {
    "Content-Type": "application/json",
    "X-USER-ID": process.env.NEXT_PUBLIC_USER_ID || "demo",
    ...(init.headers || {}),
  };
  const res = await fetch(`${base}${path}`, {
    cache: "no-store",
    ...init,
    headers,
  });
  if (!res.ok) {
    throw new Error(await res.text());
  }
  if (res.status === 204) return null;
  return res.json();
}

export async function apiList<T>(path: string): Promise<T[]> {
  const data = await api(path);
  if (Array.isArray(data)) return data;
  if (Array.isArray(data?.content)) return data.content;
  return [];
}
