// Imports de Material
import { MdlModule } from '@angular-mdl/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { InputTextModule } from 'primeng/inputtext';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';

// Imports Esenciales
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './components/app.routing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { NgSelectModule } from '@ng-select/ng-select';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';

// Imports Utilidades
import { DataObjects } from './components/ObjectGeneric';
import { Functions } from './components/Functions';
import { Util } from './components/Util';
import { LoginGuard } from './components/login.guard';
import { NgxPaginationModule } from 'ngx-pagination';

// Imports Componentes
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { HeaderComponent } from './components/header/header.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FooterComponent } from './components/footer/footer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DataTableComponent } from './components/data-table/data-table.component';
import { StaticTableComponent } from './components/static-table/static-table.component';
import { MultiUploadComponent } from './components/multi-upload/multi-upload.component';
import { DualListBoxComponent } from './components/dual-list-box/dual-list-box.component';
import { ModalsComponent } from './components/modals/modals.component';
import { UsuarioQueryComponent } from './components/usuarios/usuarioQuery.component';
import { UsuarioEditComponent } from './components/usuarios/usuarioEdit.component';
import { ConsultaComponent } from './components/consulta/consulta.component';
import { IteradorMttoComponent } from './components/iteradorMtto/iteradormtto.component';

@NgModule({
  declarations: [
    AppComponent,

    HomeComponent,
    RegisterComponent,

    HeaderComponent,
    SidebarComponent,
    FooterComponent,
    DashboardComponent,
    ConsultaComponent,
    IteradorMttoComponent,

    DataTableComponent,
    StaticTableComponent,
    MultiUploadComponent,
    DualListBoxComponent,
    ModalsComponent,
    UsuarioQueryComponent,
    UsuarioEditComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    HttpModule,
    HttpClientModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    MdlModule,
    NgSelectModule,

    MatCardModule,
    MatGridListModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    InputTextModule,
    MessagesModule,
    MessageModule,
    TableModule,
    CalendarModule
  ],
  providers: [DataObjects, LoginGuard, Util, Functions],
  bootstrap: [AppComponent]
})
export class AppModule { }
