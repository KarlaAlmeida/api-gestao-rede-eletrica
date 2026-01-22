'use client';

import React from 'react';
import { PanelMenu } from 'primereact/panelmenu';
import { MenuItem } from 'primereact/menuitem';
import Link from 'next/link';

const Sidebar = () => {
  const itemRenderer = (item: any, options: any) => (
    <Link href={item.url} className={options.className}>
      <span className={options.iconClassName}></span>
      <span className={options.labelClassName}>{item.label}</span>
    </Link>
  );

  const model: MenuItem[] = [
    {
      label: 'Dashboard',
      icon: 'pi pi-fw pi-home',
      url: '/',
      template: itemRenderer,
    },
    {
      label: 'Ativos',
      icon: 'pi pi-fw pi-box',
      url: '/ativos',
      template: itemRenderer,
    },
    {
      label: 'Técnicos',
      icon: 'pi pi-fw pi-users',
      url: '/tecnicos',
      template: itemRenderer,
    },
    {
      label: 'Ocorrências',
      icon: 'pi pi-fw pi-exclamation-triangle',
      url: '/ocorrencias',
      template: itemRenderer,
    },
    {
      label: 'Ordens de Serviços',
      icon: 'pi pi-fw pi-file',
      url: '/ordens-de-servicos',
      template: itemRenderer,
    },
    {
      label: 'Login',
      icon: 'pi pi-fw pi-sign-in',
      url: '/login',
      template: itemRenderer,
    },
    {
      label: 'Logout',
      icon: 'pi pi-fw pi-sign-out',
      url: '/logout',
      template: itemRenderer,
    },
  ];

  return (
    <div className="h-screen w-64 bg-gray-800 text-white p-4 flex flex-col">
      <div className="text-2xl font-bold mb-8 text-center">Admin Panel</div>
      <PanelMenu model={model} className="w-full" />
    </div>
  );
};

export default Sidebar;
