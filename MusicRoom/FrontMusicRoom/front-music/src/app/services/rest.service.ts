import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Util } from '.././components/Util';
import { DataObjects } from '.././components/ObjectGeneric';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RestService {
  // Utilidades
  util: any;
  AUTH: any;

  constructor(private http: HttpClient, private router: Router, datasObject: DataObjects, util: Util, ) {
    this.AUTH = {
      TOKEN_AUTH_USERNAME: datasObject.getConst().tokenUsernameAUTH,
      TOKEN_AUTH_PASSWORD: datasObject.getConst().tokenPasswordAUTH,
      TOKEN_AUTH_NAME: datasObject.getConst().tokenNameAUTH
    };
  }

  // SERVICES WITHOUT SECURITY
  getREST(url) {
    return this.http.get(url);
  }

  postREST(url, data) {
    return this.http.post(url, data);
  }

  putREST(url, data) {
    return this.http.put(url, data);
  }

  deleteREST(url, id) {
    let idParam = '{' + id + '}';

    return this.http.delete(`${url}/${idParam}`);
  }

  getFileREST(url) {
    return this.http.get(url, { responseType: 'blob' });
  }

  postFileREST(url, data: File) {
    let formData: FormData = new FormData();
    formData.append('file', data);

    return this.http.post(url, formData, { responseType: 'text' });
  }
  // END SERVICES WITHOUT SECURITY

  // SERVICES WITH SECURITY
  postOauthREST(url, usuario: string, clave: string) {
    debugger;
    const body = `grant_type=password&username=${encodeURIComponent(usuario)}&password=${encodeURIComponent(clave)}`;

    return this.http.post(url, body, {
      headers: new HttpHeaders().set('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8').set('Authorization', 'Basic ' + btoa(this.AUTH.TOKEN_AUTH_USERNAME + ':' + this.AUTH.TOKEN_AUTH_PASSWORD))
    });
  }

  getSecureREST(url, token) {
    return this.http.get(url, {
      headers: new HttpHeaders().set('Authorization', 'bearer ' + token).set('Content-Type', 'application/json')
    });
  }

  postSecureREST(url, data, token) {
    return this.http.post(url, data, {
      headers: new HttpHeaders().set('Authorization', 'bearer ' + token).set('Content-Type', 'application/json')
    });
  }
  // END SERVICES WITH SECURITY
}
