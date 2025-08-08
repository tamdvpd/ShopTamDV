const API_URL = process.env.NEXT_PUBLIC_API_URL || "";

async function request(path: string, options: RequestInit = {}) {
  const res = await fetch(`${API_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
      "X-USER-ID": "demo",
      ...(options.headers || {}),
    },
    ...options,
  });

  if (!res.ok) {
    let message = "Request failed";
    try {
      const data = await res.json();
      message = data.message || JSON.stringify(data);
    } catch {}
    throw new Error(message);
  }

  if (res.status === 204) return null;
  return res.json();
}

export const api = {
  get: (url: string) => request(url),
  post: (url: string, body: any) =>
    request(url, { method: "POST", body: JSON.stringify(body) }),
  put: (url: string, body: any) =>
    request(url, { method: "PUT", body: JSON.stringify(body) }),
  delete: (url: string) => request(url, { method: "DELETE" }),
};
