import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { ModalUpdateMeasuUnitComponent } from '../modal-update-measu-unit/modal-update-measu-unit.component';

@Component({
  selector: 'app-card-measu-unit',
  templateUrl: './card-measu-unit.component.html',
  styleUrls: ['./card-measu-unit.component.css']
})
export class CardMeasuUnitComponent implements OnInit {

  @Input() measurementUnit: IMeasurementUnit

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openUpdateDialog(measurementUnit: IMeasurementUnit) {
    this.dialog.open(ModalUpdateMeasuUnitComponent, {
      autoFocus: "input",
      width: "600px",
      height: "600px",
      data: measurementUnit
    })
  }
}
