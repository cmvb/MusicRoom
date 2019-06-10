import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RestService {
  constructor(private http: HttpClient) { }

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
    //const params = new HttpParams().set('id', '1');

    //return this.http.delete(url + '/&#123' + id + '&#125')
    //const params = new HttpParams().set('id', id);

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
}
