import { map } from 'rxjs/operators'
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
  selector: 'app-iterador',
  templateUrl: './iteradormtto.component.html',
  styleUrls: ['./iteradormtto.component.scss']
})

export class IteradorMttoComponent implements OnInit {
  enumSiNo = [];
  enumModulo = [];
  
  util:any;
  const: any;
  enums:any;
  msg:any;
  data:any;
  objeto:any;
  listaRemover = Array();
  headers = new Headers({'Content-Type': 'application/json'});
  options = new RequestOptions({headers: this.headers});

  @Input() lista: any[];
  @Input() cabeceras: any[];
  @Input() btnRemover: any[];
  @Input() btnAgregar: any[];
  @Input() check: any[];
  @Output() enviarLista: EventEmitter<any> = new EventEmitter();
  //@Output() enviarObjetoEliminar: EventEmitter<any> = new EventEmitter();

  constructor(private http: Http, private router: Router, datasObject: DataObjects, util: Util) {
    this.enums = datasObject.getEnumerados();
    this.const = datasObject.getConst();
    this.msg = datasObject.getProperties(this.const.idiomaEs);
    this.objeto = datasObject.getDataUsuario();
    this.util = util;
    this.listaRemover = [];
  }


  ngOnInit() {
    this.enviarListas();
    //cargar enums
    this.getEnum(this.enums.sino.cod);
    this.getEnum(this.enums.modulo.cod);
    //this.getEnumService(this.objeto, [this.enums.unidad.cod]);
  }

  enviarListas(){
    this.enviarLista.emit(this.lista);
  }

  remover() {
    this.lista = this.util.actualizarLista(this.listaRemover, this.lista);
    this.listaRemover = [];
    this.enviarListas();
    return true;
  }

  agregar() {
    this.lista.push(this.util.getNuevoObjeto());
    return true;
  }

  llenarListaRemover(indiceLista){
    let p = this.listaRemover.indexOf(indiceLista)
    if(p < 0){
      this.listaRemover.push(indiceLista);  
    }else{
      delete this.listaRemover[p];  
    }
  }

  getEnum(enumerado) {
    if(enumerado == this.enums.sino.cod){
      this.enumSiNo = this.enums.sino.valores;
      return true;
    }else if(enumerado == this.enums.modulo.cod){
      this.enumModulo = this.enums.modulo.valores;
      return true;
    }
    else if(enumerado == null){
      return false;
    }
  }


  getDataEnum(url, objeto): Observable<Response> {
    return this.http.post(url, objeto, this.options)
  }

  ind = 0;
  getEnumService(objeto, tipos) {
    let tipo = tipos[this.ind];
    objeto.tipo = tipo;
    this.getDataEnum(this.const.apiUrlGetEnums, objeto).toPromise().then(data => {
      let obj = data.json();
     /* if(objeto.tipo == this.enums.unidad.cod){
        this.enumUnidad = data.json();
      }*/
      this.ind++;
      if(this.ind >= tipos.length){
        return true;
      }else{
        this.getEnumService(objeto, tipos);  
      }
      return true;
    }).catch(data => {
      return false;
    });
  }
}