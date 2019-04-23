import 'rxjs/add/operator/map';
import { Util } from '../Util';
import { DataObjects } from '../ObjectGeneric';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Component, OnInit, Input, forwardRef, Inject, Output, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { format } from 'url';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { RestService } from '../../services/rest.service';

@Component({
  selector: 'app-consulta',
  templateUrl: './consultamtto.component.html',
  styleUrls: ['./consultamtto.component.css']
})

export class ConsultamttoComponent implements OnInit {
  @Input() lista: any[];
  @Input() cabeceras: any[];
  @Input() btnEditar: any[];
  @Input() btnEliminar: any[];
  @Output() enviarObjetoEditar: EventEmitter<any> = new EventEmitter();
  @Output() enviarObjetoEliminar: EventEmitter<any> = new EventEmitter();

  p: number = 1;

  msg: any;
  const: any;
  util: any;
  constructor(private http: Http, private router: Router, datasObject: DataObjects, util: Util) {
    this.const = datasObject.getConst();
    this.msg = datasObject.getProperties(this.const.idiomaEn);
    this.util = util;
  }

  ngOnInit() {
  }

  editar(obj) {
    this.enviarObjetoEditar.emit(obj);
    return true;
  }

  eliminar(obj) {
    this.enviarObjetoEliminar.emit(obj);
    return true;
  }
}