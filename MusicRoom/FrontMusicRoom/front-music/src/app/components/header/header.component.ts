import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';

declare var $: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  providers: [RestService]
})
export class HeaderComponent implements OnInit {
  util: any;
  data: any = [];
  msg: any;
  const: any;
  usuarioTb: any;
  usuarioSesion: any;
  ACCESS_TOKEN: any;

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util) {
    this.util = util;
    this.usuarioSesion = localStorage.getItem('usuarioSesion') === null ? null : JSON.parse(localStorage.getItem('usuarioSesion').toString());
    this.msg = datasObject.getProperties(datasObject.getConst().idiomaEs);
    this.const = datasObject.getConst();
    this.ACCESS_TOKEN = JSON.parse(sessionStorage.getItem(this.const.tokenNameAUTH)).access_token;
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
      let url = this.const.urlRestService + this.const.urlControllerUsuario + 'anular/' + this.ACCESS_TOKEN;
      this.restService.getREST(url)
        .subscribe(resp => {
          localStorage.clear();
          sessionStorage.clear();
          location.reload();
        });
    } catch (e) {
      console.log(e);
    }
  }
}
