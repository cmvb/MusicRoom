import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../.././services/rest.service';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';
import { SesionService } from 'src/app/services/sesionService/sesion.service';

declare var $: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [RestService]
})
export class HeaderComponent implements OnInit {
  data: any = [];
  msg: any;
  const: any;
  usuarioTb: any;
  usuarioSesion: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, public textProperties: TextProperties, public objectModelInitializer: ObjectModelInitializer, public sesionService: SesionService, public util: Util) {
    this.usuarioSesion = this.sesionService.objServiceSesion.usuarioSesion;
    this.msg = this.textProperties.getProperties(this.sesionService.objServiceSesion.idioma);
    this.const = this.objectModelInitializer.getConst();
  }

  ngOnInit() {
  }

  ngAfterContentInit() {
    if (this.usuarioSesion !== null) {
      this.usuarioTb = this.usuarioSesion.usuarioTb;
    }
  }

  cerrarSesion() {
    try {
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'anular/' + this.usuarioSesion.tokenSesion;

      this.restService.getREST(url)
        .subscribe(resp => {
          localStorage.clear();
          sessionStorage.clear();
          console.clear();
          this.sesionService.inicializar();
        });

      this.router.navigate(['/home']);
    } catch (e) {
      console.log(e);
    }
  }
}
