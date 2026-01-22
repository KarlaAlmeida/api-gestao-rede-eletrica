'use client';

import AppLayout from '@/components/layout/AppLayout';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';

export default function OrdensDeServicosPage() {
  const serviceOrders = [
    { id: 1, description: 'Ordem de Serviço 1', status: 'Aberta' },
    { id: 2, description: 'Ordem de Serviço 2', status: 'Fechada' },
  ];

  const actionBodyTemplate = () => {
    return (
      <div className="flex gap-2">
        <Button icon="pi pi-pencil" className="p-button-rounded p-button-success" />
        <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" />
      </div>
    );
  };

  return (
    <AppLayout title="Ordens de Serviços">
      <Card>
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold">Ordens de Serviços</h1>
          <Button label="Nova Ordem de Serviço" icon="pi pi-plus" />
        </div>
        <DataTable value={serviceOrders} paginator rows={10}>
          <Column field="id" header="ID"></Column>
          <Column field="description" header="Descrição"></Column>
          <Column field="status" header="Status"></Column>
          <Column body={actionBodyTemplate} header="Ações"></Column>
        </DataTable>
      </Card>
    </AppLayout>
  );
}
