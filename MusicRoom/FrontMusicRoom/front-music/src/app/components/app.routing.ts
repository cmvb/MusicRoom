import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './login.guard';
import { Error403Component } from './error/error403.component';
import { Error404Component } from './error/error404.component';
import { Error500Component } from './error/error500.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UsuarioEditComponent } from './usuarios/usuarioEdit.component';
import { UsuarioQueryComponent } from './usuarios/usuarioQuery.component';
import { UbicacionesEditComponent } from './ubicaciones/ubicacionesEdit.component';
import { UbicacionesQueryComponent } from './ubicaciones/ubicacionesQuery.component';
import { TercerosEditComponent } from './terceros/tercerosEdit.component';
import { TercerosQueryComponent } from './terceros/tercerosQuery.component';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';
import { DualListBoxComponent } from './dual-list-box/dual-list-box.component';
import { ModalsComponent } from './modals/modals.component';
import { VCodeComponent } from './v-code/v-code.component';
import { TokenComponent } from './v-code/token/token.component';


const routes: Routes = [
  { path: 'error403', component: Error403Component, canActivate: [LoginGuard] },
  { path: 'error404', component: Error404Component, canActivate: [LoginGuard] },
  { path: 'error500', component: Error500Component, canActivate: [LoginGuard] },

  { path: 'vCode', component: VCodeComponent, children: [{ path: ':token', component: TokenComponent }] },

  { path: 'multi-upload', component: MultiUploadComponent },
  { path: 'dual-list-box', component: DualListBoxComponent },
  { path: 'modals', component: ModalsComponent },
  { path: 'home', component: HomeComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [LoginGuard] },
  { path: 'usuarioQuery', component: UsuarioQueryComponent, canActivate: [LoginGuard] },
  { path: 'usuarioEdit', component: UsuarioEditComponent, canActivate: [LoginGuard] },
  { path: 'ubicacionQuery', component: UbicacionesQueryComponent, canActivate: [LoginGuard] },
  { path: 'ubicacionEdit', component: UbicacionesEditComponent, canActivate: [LoginGuard] },
  { path: 'terceroQuery', component: TercerosQueryComponent, canActivate: [LoginGuard] },
  { path: 'terceroEdit', component: TercerosEditComponent, canActivate: [LoginGuard] },

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
