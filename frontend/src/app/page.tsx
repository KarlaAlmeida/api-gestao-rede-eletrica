'use client';

import AppLayout from '@/components/layout/AppLayout';
import { Card } from 'primereact/card';

export default function HomePage() {
  return (
    <AppLayout title="Dashboard">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <Card title="Ativos" className="md:col-span-1">
          <p className="m-0">150</p>
        </Card>
        <Card title="Técnicos" className="md:col-span-1">
          <p className="m-0">25</p>
        </Card>
        <Card title="Ocorrências Abertas" className="md:col-span-1">
          <p className="m-0">12</p>
        </Card>
        <Card title="Ordens de Serviço Pendentes" className="md:col-span-1">
          <p className="m-0">8</p>
        </Card>
      </div>
    </AppLayout>
  );
}
