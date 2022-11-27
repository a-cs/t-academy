import { Component, OnInit, Input } from '@angular/core';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';

@Component({
  selector: 'app-card-measu-unit',
  templateUrl: './card-measu-unit.component.html',
  styleUrls: ['./card-measu-unit.component.css']
})
export class CardMeasuUnitComponent implements OnInit {

  @Input() measurementUnit: IMeasurementUnit

  constructor() { }

  ngOnInit(): void {
  }

}
