'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function LogoutPage() {
  const router = useRouter();

  useEffect(() => {
    // Here you would typically clear the user's session and token.
    // For now, we'll just redirect to the login page.
    setTimeout(() => {
      router.push('/login');
    }, 2000);
  }, [router]);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="text-2xl font-bold">Logging out...</div>
    </div>
  );
}
