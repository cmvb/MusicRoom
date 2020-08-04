import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { ObjectModelInitializer } from 'src/app/config/ObjectModelInitializer';
import { TextProperties } from 'src/app/config/TextProperties';
import { Util } from 'src/app/config/Util';

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

  constructor(private http: Http, private router: Router, public objectModelInitializer: ObjectModelInitializer, public textProperties: TextProperties, public util: Util, private sanitizer: DomSanitizer) {
    this.const = this.objectModelInitializer.getConst();
    this.msg = this.textProperties.getProperties(this.const.idiomaEs);
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

  cargarImagen(dato: any, tipoArchivo) {
    if (tipoArchivo === 'svg') {
      return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/svg+xml;base64,' + dato);
    } else {
      tipoArchivo = tipoArchivo + ';base64,';
      return 'data:image/' + tipoArchivo + dato;
    }
  }
}