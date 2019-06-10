// Imports de Material
import { MdlModule } from '@angular-mdl/core';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BrowserModule } from '@angular/platform-browser';
// Imports Esenciales
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxUiLoaderConfig, NgxUiLoaderHttpModule, NgxUiLoaderModule, NgxUiLoaderRouterModule } from 'ngx-ui-loader';
import { CalendarModule } from 'primeng/calendar';
import { DeferModule } from 'primeng/defer';
import { DragDropModule } from 'primeng/dragdrop';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { StepsModule } from 'primeng/steps';
import { TableModule } from 'primeng/table';
import { TabMenuModule } from 'primeng/tabmenu';
import { ToastModule } from 'primeng/toast';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './components/app.routing';
import { ConsultaComponent } from './components/consulta/consulta.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DualListBoxComponent } from './components/dual-list-box/dual-list-box.component';
import { FooterComponent } from './components/footer/footer.component';
import { Functions } from './components/Functions';
import { HeaderComponent } from './components/header/header.component';
// Imports Componentes
import { HomeComponent } from './components/home/home.component';
import { IteradorMttoComponent } from './components/iteradorMtto/iteradormtto.component';
import { LoginGuard } from './components/login.guard';
import { ModalsComponent } from './components/modals/modals.component';
import { MultiUploadComponent } from './components/multi-upload/multi-upload.component';
// Imports Utilidades
import { DataObjects } from './components/ObjectGeneric';
import { PdfViewerthis } from './components/pdf-viewerthis.util/pdf-viewerthis.util.component';
import { RegisterComponent } from './components/register/register.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { TercerosEditComponent } from './components/terceros/tercerosEdit.component';
import { TercerosQueryComponent } from './components/terceros/tercerosQuery.component';
import { UbicacionesEditComponent } from './components/ubicaciones/ubicacionesEdit.component';
import { UbicacionesQueryComponent } from './components/ubicaciones/ubicacionesQuery.component';
import { UsuarioEditComponent } from './components/usuarios/usuarioEdit.component';
import { UsuarioQueryComponent } from './components/usuarios/usuarioQuery.component';
import { Util } from './components/Util';




// Constantes
const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  "bgsColor": "#00ACC1",
  "bgsOpacity": 0.5,
  "bgsPosition": "bottom-right",
  "bgsSize": 60,
  "bgsType": "ball-spin-clockwise",
  "blur": 5,
  "fgsColor": "#00ACC1",
  "fgsPosition": "center-center",
  "fgsSize": 180,
  "fgsType": "three-strings",
  "gap": 24,
  "logoPosition": "center-center",
  "logoSize": 40,
  "masterLoaderId": "master",
  "overlayBorderRadius": "0",
  "overlayColor": "rgba(40, 40, 40, 0.8)",
  "pbColor": "#00ACC1",
  "pbDirection": "ltr",
  "pbThickness": 3,
  "hasProgressBar": true,
  "text": "",
  "textColor": "#FFFFFF",
  "textPosition": "center-center",
  "threshold": 500
};


// Componentes
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

    MultiUploadComponent,
    DualListBoxComponent,
    ModalsComponent,
    UsuarioQueryComponent,
    UsuarioEditComponent,
    UbicacionesQueryComponent,
    UbicacionesEditComponent,
    TercerosQueryComponent,
    TercerosEditComponent,
    PdfViewerthis,
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
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    NgxUiLoaderRouterModule,
    NgxUiLoaderHttpModule.forRoot({ showForeground: true }),

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
    CalendarModule,
    ToastModule,
    TabMenuModule,
    InputTextareaModule,
    DragDropModule,
    DeferModule,
    ScrollPanelModule,
    StepsModule,
    PdfViewerModule
  ],
  providers: [DataObjects, LoginGuard, Util, Functions],
  bootstrap: [AppComponent]
})
export class AppModule { }
