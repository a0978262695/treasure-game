import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { apiFetch } from '@/lib/api';

interface AuthUser {
  userId: number;
  email: string;
  displayName: string;
}

interface AuthContextType {
  user: AuthUser | null;
  loading: boolean;
  signUp: (email: string, password: string, displayName: string) => Promise<void>;
  signIn: (email: string, password: string) => Promise<void>;
  signOut: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = localStorage.getItem('authUser');
    if (stored) {
      try {
        setUser(JSON.parse(stored));
      } catch {
        localStorage.removeItem('authUser');
        localStorage.removeItem('token');
      }
    }
    setLoading(false);
  }, []);

  const handleAuthResponse = (data: { token: string; userId: number; displayName: string; email: string }) => {
    localStorage.setItem('token', data.token);
    const authUser: AuthUser = { userId: data.userId, email: data.email, displayName: data.displayName };
    localStorage.setItem('authUser', JSON.stringify(authUser));
    setUser(authUser);
  };

  const signUp = async (email: string, password: string, displayName: string) => {
    const res = await apiFetch('/api/auth/signup', {
      method: 'POST',
      body: JSON.stringify({ email, password, displayName }),
    });
    handleAuthResponse(await res.json());
  };

  const signIn = async (email: string, password: string) => {
    const res = await apiFetch('/api/auth/signin', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
    handleAuthResponse(await res.json());
  };

  const signOut = async () => {
    try {
      await apiFetch('/api/auth/signout', { method: 'POST' });
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('authUser');
      setUser(null);
    }
  };

  return (
    <AuthContext.Provider value={{ user, loading, signUp, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}
