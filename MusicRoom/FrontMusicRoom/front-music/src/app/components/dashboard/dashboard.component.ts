import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { Util } from '../.././components/Util';

declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  util: any;
  availableCars: any[];
  selectedCars: any[];
  draggedCar: any;
  srcImg: any;

  constructor(util: Util) {
    this.util = util;
    this.srcImg = 'assets/images/icons/';
  }

  ngOnInit() {
    this.selectedCars = [];
    this.availableCars = [
      { id: 1, label: 'obj1', srcImg: this.srcImg + 'About.gif' },
      { id: 2, label: 'obj2', srcImg: this.srcImg + '200-add.gif' },
      { id: 3, label: 'obj3', srcImg: this.srcImg + '0001-monitor.gif' },
      { id: 4, label: 'obj4', srcImg: this.srcImg + '0025-search.gif' },
      { id: 5, label: 'obj5', srcImg: this.srcImg + 'books_016.gif' },
    ];
  }

  dragStart(event, car: any) {
    this.draggedCar = car;
  }

  drop(event) {
    if (this.draggedCar) {
      let draggedCarIndex = this.findIndex(this.draggedCar);
      this.selectedCars = [...this.selectedCars, this.draggedCar];
      this.availableCars = this.availableCars.filter((val, i) => i != draggedCarIndex);
      this.draggedCar = null;
    }
  }

  dragEnd(event) {
    this.draggedCar = null;
  }

  findIndex(car: any) {
    let index = -1;
    for (let i = 0; i < this.availableCars.length; i++) {
      if (car.label === this.availableCars[i].label) {
        index = i;
        break;
      }
    }
    return index;
  }
}
