import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import ISku from 'src/app/interfaces/ISku';
import { capitalize } from 'src/app/utils/functions';
@Component({
  selector: 'app-card-sku',
  templateUrl: './card-sku.component.html',
  styleUrls: ['./card-sku.component.css']
})
export class CardSkuComponent implements OnInit {

 
 @Input() sku: ISku ={
  name: "",
  category: {
    id: 0,
    name: ""
  },
  measurementUnit: {
    id: 0,
    description: "",
    symbol: ""
  }

}
 
  constructor() { }

  ngOnInit(): void {
   
  }

  
   
}
