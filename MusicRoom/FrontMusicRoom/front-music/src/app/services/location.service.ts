import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Util } from '.././components/Util';
import { DataObjects } from '.././components/ObjectGeneric';
import { RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const httpFileOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data', 'Accept': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  // Utilidades
  util: any;

  constructor(private http: HttpClient, private router: Router, datasObject: DataObjects, util: Util) {
    this.util = util;
  }

  getLocation(): Observable<any> {
    return Observable.create(observer => {
      if (window.navigator && window.navigator.geolocation) {
        window.navigator.geolocation.getCurrentPosition(
          (position) => {
            observer.next(position);
            observer.complete();
          },
          (error) => observer.error(error)
        );
      } else {
        observer.error('Unsupported Browser');
      }
    });
  }

  /* this.service.getLocation().subscribe(rep => {
     do something with Rep, Rep will have the data you desire.
 });*/

}
