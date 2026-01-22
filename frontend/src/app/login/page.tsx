'use client';

import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Card } from 'primereact/card';

export default function LoginPage() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <Card title="Login" className="w-full max-w-md">
        <div className="p-fluid">
          <div className="p-field mb-4">
            <label htmlFor="email">Email</label>
            <InputText id="email" type="text" />
          </div>
          <div className="p-field mb-4">
            <label htmlFor="password">Password</label>
            <InputText id="password" type="password" />
          </div>
        </div>
        <Button label="Login" className="w-full" />
      </Card>
    </div>
  );
}
