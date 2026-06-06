import { useState, useEffect } from 'react';
import { apiFetch } from '@/lib/api';
import { useAuth } from '@/context/AuthContext';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';

interface LeaderboardEntry {
  userId: number;
  displayName: string;
  totalScore: number;
  gamesPlayed: number;
}

export default function Leaderboard() {
  const { user } = useAuth();
  const [entries, setEntries] = useState<LeaderboardEntry[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    apiFetch('/api/leaderboard')
      .then(res => res.json())
      .then(setEntries)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <p className="text-center text-amber-700 py-4">載入中...</p>;
  }

  return (
    <div>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-12 text-center">名次</TableHead>
            <TableHead>玩家</TableHead>
            <TableHead className="text-right">累計分數</TableHead>
            <TableHead className="text-right">局數</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {entries.map((entry, index) => (
            <TableRow
              key={entry.userId}
              className={entry.userId === user?.userId ? 'bg-amber-100 font-semibold' : ''}
            >
              <TableCell className="text-center">
                {index === 0 ? '🥇' : index === 1 ? '🥈' : index === 2 ? '🥉' : index + 1}
              </TableCell>
              <TableCell>{entry.displayName}</TableCell>
              <TableCell className={`text-right ${entry.totalScore >= 0 ? 'text-green-600' : 'text-red-600'}`}>
                ${entry.totalScore}
              </TableCell>
              <TableCell className="text-right text-amber-700">{entry.gamesPlayed}</TableCell>
            </TableRow>
          ))}
          {entries.length === 0 && (
            <TableRow>
              <TableCell colSpan={4} className="text-center text-amber-600 py-6">
                還沒有任何紀錄
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}
