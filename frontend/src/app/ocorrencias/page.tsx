'use client';

import AppLayout from '@/components/layout/AppLayout';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';

export default function OcorrenciasPage() {
  const occurrences = [
    { id: 1, description: 'Ocorrência 1', asset: 'Ativo 1' },
    { id: 2, description: 'Ocorrência 2', asset: 'Ativo 2' },
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
    <AppLayout title="Ocorrências">
      <Card>
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold">Ocorrências</h1>
          <Button label="Nova Ocorrência" icon="pi pi-plus" />
        </div>
        <DataTable value={occurrences} paginator rows={10}>
          <Column field="id" header="ID"></Column>
          <Column field="description" header="Descrição"></Column>
          <Column field="asset" header="Ativo"></Column>
          <Column body={actionBodyTemplate} header="Ações"></Column>
        </DataTable>
      </Card>
    </AppLayout>
  );
}
