import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as $ from 'jquery';
import { MenuItem, MessageService } from 'primeng/api';
import { map } from 'rxjs/operators'
import { DataObjects } from '../.././components/ObjectGeneric';
import { Util } from '../.././components/Util';
import { RestService } from '../.././services/rest.service';


declare var $: any;

@Component({
  selector: 'app-v-code',
  templateUrl: './v-code.component.html',
  styleUrls: ['./v-code.component.scss'],
  providers: [RestService, MessageService]
})
export class VCodeComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, public restService: RestService, datasObject: DataObjects, util: Util, private messageService: MessageService) {
  }

  // Procesos que se ejecutan cuando algo en el DOM cambia
  ngDoCheck() {
  }

  // Procesos que se ejecutan al cargar el componente
  ngOnInit() {
  }

  ngAfterViewInit() {
  }

}