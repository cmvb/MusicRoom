import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { Guardian } from './Guardian';
import { Error403Component } from '../components/error/error403.component';
import { Error404Component } from '../components/error/error404.component';
import { Error500Component } from '../components/error/error500.component';
import { HomeComponent } from '../components/home/home.component';
import { RegisterComponent } from '../components/register/register.component';
import { DashboardComponent } from '../components/dashboard/dashboard.component';
import { UsuarioEditComponent } from '../components/usuarios/usuarioEdit.component';
import { UsuarioQueryComponent } from '../components/usuarios/usuarioQuery.component';
import { UbicacionesEditComponent } from '../components/ubicaciones/ubicacionesEdit.component';
import { UbicacionesQueryComponent } from '../components/ubicaciones/ubicacionesQuery.component';
import { TercerosEditComponent } from '../components/terceros/tercerosEdit.component';
import { TercerosQueryComponent } from '../components/terceros/tercerosQuery.component';
import { SalasEditComponent } from '../components/salas/salasEdit.component';
import { SalasQueryComponent } from '../components/salas/salasQuery.component';
import { MultiUploadComponent } from '../components/multi-upload/multi-upload.component';
import { DualListBoxComponent } from '../components/dual-list-box/dual-list-box.component';
import { VCodeComponent } from '../components/v-code/v-code.component';
import { TokenComponent } from '../components/v-code/token/token.component';
import { BandasIntegrantesQueryComponent } from '../components/bandasIntegrantes/bandasIntegrantesQuery.component';
import { BandasIntegrantesEditComponent } from '../components/bandasIntegrantes/bandasIntegrantesEdit.component';


const routes: Routes = [
  { path: 'error403', component: Error403Component, canActivate: [Guardian] },
  { path: 'error404', component: Error404Component, canActivate: [Guardian] },
  { path: 'error500', component: Error500Component, canActivate: [Guardian] },

  { path: 'vCode', component: VCodeComponent, children: [{ path: ':token', component: TokenComponent }] },

  { path: 'multi-upload', component: MultiUploadComponent },
  { path: 'dual-list-box', component: DualListBoxComponent },
  { path: 'home', component: HomeComponent, canActivate: [Guardian] },
  { path: 'register', component: RegisterComponent, canActivate: [Guardian] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [Guardian] },
  { path: 'usuarioQuery', component: UsuarioQueryComponent, canActivate: [Guardian] },
  { path: 'usuarioEdit', component: UsuarioEditComponent, canActivate: [Guardian] },
  { path: 'ubicacionQuery', component: UbicacionesQueryComponent, canActivate: [Guardian] },
  { path: 'ubicacionEdit', component: UbicacionesEditComponent, canActivate: [Guardian] },
  { path: 'terceroQuery', component: TercerosQueryComponent, canActivate: [Guardian] },
  { path: 'terceroEdit', component: TercerosEditComponent, canActivate: [Guardian] },
  { path: 'salaQuery', component: SalasQueryComponent, canActivate: [Guardian] },
  { path: 'salaEdit', component: SalasEditComponent, canActivate: [Guardian] },
  { path: 'bandaIntegranteQuery', component: BandasIntegrantesQueryComponent, canActivate: [Guardian] },
  { path: 'bandaIntegranteEdit', component: BandasIntegrantesEditComponent, canActivate: [Guardian] },

  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/error404' },
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
