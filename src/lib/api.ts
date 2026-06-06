const BASE = 'http://localhost:8080';

export async function apiFetch(path: string, options?: RequestInit) {
  const token = localStorage.getItem('token');
  const res = await fetch(`${BASE}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...options?.headers,
    },
  });
  if (!res.ok) {
    const body = await res.json().catch(() => ({ error: '請求失敗' }));
    throw new Error(body.error ?? '請求失敗');
  }
  return res;
}
