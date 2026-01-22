'use client';

import AppLayout from '@/components/layout/AppLayout';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';

export default function AtivosPage() {
  const assets = [
    { id: 1, name: 'Ativo 1', description: 'Descrição do Ativo 1' },
    { id: 2, name: 'Ativo 2', description: 'Descrição do Ativo 2' },
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
    <AppLayout title="Ativos">
      <Card>
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold">Ativos</h1>
          <Button label="Novo Ativo" icon="pi pi-plus" />
        </div>
        <DataTable value={assets} paginator rows={10}>
          <Column field="id" header="ID"></Column>
          <Column field="name" header="Nome"></Column>
          <Column field="description" header="Descrição"></Column>
          <Column body={actionBodyTemplate} header="Ações"></Column>
        </DataTable>
      </Card>
    </AppLayout>
  );
}
