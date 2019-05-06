import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DualListBoxComponent } from './dual-list-box/dual-list-box.component';
import { HomeComponent } from './home/home.component';
import { LoginGuard } from './login.guard';
import { ModalsComponent } from './modals/modals.component';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';
import { RegisterComponent } from './register/register.component';
import { TercerosEditComponent } from './terceros/tercerosEdit.component';
import { TercerosQueryComponent } from './terceros/tercerosQuery.component';
import { UbicacionesEditComponent } from './ubicaciones/ubicacionesEdit.component';
import { UbicacionesQueryComponent } from './ubicaciones/ubicacionesQuery.component';
import { UsuarioEditComponent } from './usuarios/usuarioEdit.component';
import { UsuarioQueryComponent } from './usuarios/usuarioQuery.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [LoginGuard] },
  { path: 'multi-upload', component: MultiUploadComponent, canActivate: [LoginGuard] },
  { path: 'dual-list-box', component: DualListBoxComponent, canActivate: [LoginGuard] },
  { path: 'modals', component: ModalsComponent, canActivate: [LoginGuard] },
  { path: 'usuarioQuery', component: UsuarioQueryComponent, canActivate: [LoginGuard] },
  { path: 'usuarioEdit', component: UsuarioEditComponent, canActivate: [LoginGuard] },
  { path: 'ubicacionQuery', component: UbicacionesQueryComponent, canActivate: [LoginGuard] },
  { path: 'ubicacionEdit', component: UbicacionesEditComponent, canActivate: [LoginGuard] },
  { path: 'terceroQuery', component: TercerosQueryComponent, canActivate: [LoginGuard] },
  { path: 'terceroEdit', component: TercerosEditComponent, canActivate: [LoginGuard] },

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
