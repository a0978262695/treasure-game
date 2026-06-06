import { useState } from 'react';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

type Tab = 'signin' | 'signup';

interface AuthFormProps {
  onGuestPlay?: () => void;
}

export default function AuthForm({ onGuestPlay }: AuthFormProps) {
  const { signIn, signUp } = useAuth();
  const [tab, setTab] = useState<Tab>('signin');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const [loginForm, setLoginForm] = useState({ email: '', password: '' });
  const [signupForm, setSignupForm] = useState({ email: '', password: '', displayName: '' });

  const handleSignIn = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await signIn(loginForm.email, loginForm.password);
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : '登入失敗');
    } finally {
      setLoading(false);
    }
  };

  const handleSignUp = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await signUp(signupForm.email, signupForm.password, signupForm.displayName);
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : '註冊失敗');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-amber-50 to-amber-100 flex items-center justify-center p-8">
      <div style={{ width: '100%', maxWidth: '420px' }}>
        <h1 className="text-3xl text-center mb-6 text-amber-900">🏴‍☠️ Treasure Hunt Game</h1>

        {/* Custom Tab Bar */}
        <div className="flex rounded-xl bg-gray-100 p-1 mb-0">
          <button
            type="button"
            onClick={() => { setTab('signin'); setError(''); }}
            className={`flex-1 py-2 rounded-lg text-sm font-medium transition-colors ${
              tab === 'signin'
                ? 'bg-white shadow text-gray-900'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            登入
          </button>
          <button
            type="button"
            onClick={() => { setTab('signup'); setError(''); }}
            className={`flex-1 py-2 rounded-lg text-sm font-medium transition-colors ${
              tab === 'signup'
                ? 'bg-white shadow text-gray-900'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            註冊
          </button>
        </div>

        {onGuestPlay && (
          <button
            type="button"
            onClick={onGuestPlay}
            className="w-full text-sm text-amber-700 hover:text-amber-900 underline underline-offset-2 mb-3 text-center"
          >
            以訪客身分遊玩（不儲存分數）
          </button>
        )}

        <Card className="rounded-tl-none rounded-tr-none border-t-0">
          <CardHeader>
            <CardTitle>{tab === 'signin' ? '登入帳號' : '建立帳號'}</CardTitle>
          </CardHeader>
          <CardContent>
            {tab === 'signin' ? (
              <form onSubmit={handleSignIn} className="space-y-4">
                <div className="space-y-1">
                  <Label htmlFor="signin-email">Email</Label>
                  <Input
                    id="signin-email"
                    type="email"
                    required
                    value={loginForm.email}
                    onChange={e => setLoginForm(f => ({ ...f, email: e.target.value }))}
                  />
                </div>
                <div className="space-y-1">
                  <Label htmlFor="signin-password">密碼</Label>
                  <Input
                    id="signin-password"
                    type="password"
                    required
                    value={loginForm.password}
                    onChange={e => setLoginForm(f => ({ ...f, password: e.target.value }))}
                  />
                </div>
                {error && <p className="text-sm text-red-600">{error}</p>}
                <Button type="submit" className="w-full bg-amber-600 hover:bg-amber-700" disabled={loading}>
                  {loading ? '登入中...' : '登入'}
                </Button>
              </form>
            ) : (
              <form onSubmit={handleSignUp} className="space-y-4">
                <div className="space-y-1">
                  <Label htmlFor="signup-name">顯示名稱</Label>
                  <Input
                    id="signup-name"
                    type="text"
                    required
                    value={signupForm.displayName}
                    onChange={e => setSignupForm(f => ({ ...f, displayName: e.target.value }))}
                  />
                </div>
                <div className="space-y-1">
                  <Label htmlFor="signup-email">Email</Label>
                  <Input
                    id="signup-email"
                    type="email"
                    required
                    value={signupForm.email}
                    onChange={e => setSignupForm(f => ({ ...f, email: e.target.value }))}
                  />
                </div>
                <div className="space-y-1">
                  <Label htmlFor="signup-password">密碼（至少 6 個字元）</Label>
                  <Input
                    id="signup-password"
                    type="password"
                    required
                    minLength={6}
                    value={signupForm.password}
                    onChange={e => setSignupForm(f => ({ ...f, password: e.target.value }))}
                  />
                </div>
                {error && <p className="text-sm text-red-600">{error}</p>}
                <Button type="submit" className="w-full bg-amber-600 hover:bg-amber-700" disabled={loading}>
                  {loading ? '註冊中...' : '註冊'}
                </Button>
              </form>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
