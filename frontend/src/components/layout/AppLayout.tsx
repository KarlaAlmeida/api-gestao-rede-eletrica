import React from 'react';
import Sidebar from './Sidebar';

const AppLayout = ({ children, title }: { children: React.ReactNode, title: string }) => {
  return (
    <div className="flex min-h-screen bg-gray-100">
      <Sidebar />
      <div className="flex-1 flex flex-col">
        <header className="bg-white shadow p-4">
          <h1 className="text-xl font-semibold">{title}</h1>
        </header>
        <main className="flex-1 p-4">{children}</main>
      </div>
    </div>
  );
};

export default AppLayout;
