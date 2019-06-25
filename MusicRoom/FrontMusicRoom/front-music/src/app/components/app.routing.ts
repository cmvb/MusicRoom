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



const routes: Routes = [
  { path: 'music-room/error403', component: Error403Component, canActivate: [LoginGuard] },
  { path: 'music-room/error404', component: Error404Component, canActivate: [LoginGuard] },
  { path: 'music-room/error500', component: Error500Component, canActivate: [LoginGuard] },

  { path: 'multi-upload', component: MultiUploadComponent, canActivate: [LoginGuard] },
  { path: 'dual-list-box', component: DualListBoxComponent, canActivate: [LoginGuard] },
  { path: 'modals', component: ModalsComponent, canActivate: [LoginGuard] },
  { path: 'music-room/home', component: HomeComponent, canActivate: [LoginGuard] },
  { path: 'music-room/register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'music-room/dashboard', component: DashboardComponent, canActivate: [LoginGuard] },
  { path: 'music-room/usuarioQuery', component: UsuarioQueryComponent, canActivate: [LoginGuard] },
  { path: 'music-room/usuarioEdit', component: UsuarioEditComponent, canActivate: [LoginGuard] },
  { path: 'music-room/ubicacionQuery', component: UbicacionesQueryComponent, canActivate: [LoginGuard] },
  { path: 'music-room/ubicacionEdit', component: UbicacionesEditComponent, canActivate: [LoginGuard] },
  { path: 'music-room/terceroQuery', component: TercerosQueryComponent, canActivate: [LoginGuard] },
  { path: 'music-room/terceroEdit', component: TercerosEditComponent, canActivate: [LoginGuard] },

  { path: '', redirectTo: 'music-room/home', pathMatch: 'full' },
  { path: '**', redirectTo: 'music-room/error404', pathMatch: 'full' }
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
