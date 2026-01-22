'use client';

import AppLayout from '@/components/layout/AppLayout';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';

export default function TecnicosPage() {
  const technicians = [
    { id: 1, name: 'Técnico 1', specialty: 'Especialidade 1' },
    { id: 2, name: 'Técnico 2', specialty: 'Especialidade 2' },
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
    <AppLayout title="Técnicos">
      <Card>
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold">Técnicos</h1>
          <Button label="Novo Técnico" icon="pi pi-plus" />
        </div>
        <DataTable value={technicians} paginator rows={10}>
          <Column field="id" header="ID"></Column>
          <Column field="name" header="Nome"></Column>
          <Column field="specialty" header="Especialidade"></Column>
          <Column body={actionBodyTemplate} header="Ações"></Column>
        </DataTable>
      </Card>
    </AppLayout>
  );
}
