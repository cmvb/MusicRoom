import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DataTableComponent } from './data-table/data-table.component';
import { StaticTableComponent } from './static-table/static-table.component';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';
import { DualListBoxComponent } from './dual-list-box/dual-list-box.component';
import { ModalsComponent } from './modals/modals.component';

import { LoginGuard } from './login.guard';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [LoginGuard] },
  { path: 'static-table', component: StaticTableComponent, canActivate: [LoginGuard] },
  { path: 'data-table', component: DataTableComponent, canActivate: [LoginGuard] },
  { path: 'multi-upload', component: MultiUploadComponent, canActivate: [LoginGuard] },
  { path: 'dual-list-box', component: DualListBoxComponent, canActivate: [LoginGuard] },
  { path: 'modals', component: ModalsComponent, canActivate: [LoginGuard] },

  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
  ],
})
export class AppRoutingModule { }
