import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { RestService } from '../.././services/rest.service';


declare var $: any;

@Component({
  selector: 'app-v-code',
  templateUrl: './v-code.component.html',
  styleUrls: ['./v-code.component.scss'],
  providers: [RestService, MessageService]
})
export class VCodeComponent implements OnInit {

  constructor() {
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