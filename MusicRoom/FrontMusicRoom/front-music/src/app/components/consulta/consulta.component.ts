import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import { DataObjects } from '../ObjectGeneric';
import { Util } from '../Util';

@Component({
  selector: 'app-consulta',
  templateUrl: './consulta.component.html',
  styleUrls: ['./consulta.component.scss']
})

export class ConsultaComponent implements OnInit {
  @Input() lista: any[];
  @Input() cabeceras: any[];
  @Input() btnEditar: any[];
  @Input() btnEliminar: any[];
  @Output() enviarObjetoEditar: EventEmitter<any> = new EventEmitter();
  @Output() enviarObjetoEliminar: EventEmitter<any> = new EventEmitter();

  cols: any[];
  p: number = 1;
  rows: any;
  enumRows: any;

  msg: any;
  const: any;
  util: any;

  constructor(private http: Http, private router: Router, datasObject: DataObjects, util: Util) {
    this.const = datasObject.getConst();
    this.msg = datasObject.getProperties(this.const.idiomaEs);
    this.util = util;
    this.enumRows = [5, 10, 15, 20, 50, 100];
    this.rows = this.enumRows[0];
  }

  ngOnInit() {
  }

  ngAfterContentChecked() {
    this.cols = this.util.armarTabla(this.cabeceras, this.lista);
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