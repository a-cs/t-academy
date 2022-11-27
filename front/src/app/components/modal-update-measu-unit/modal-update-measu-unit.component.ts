import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';

@Component({
  selector: 'app-modal-update-measu-unit',
  templateUrl: './modal-update-measu-unit.component.html',
  styleUrls: ['./modal-update-measu-unit.component.css']
})
export class ModalUpdateMeasuUnitComponent implements OnInit {

  form: FormGroup
  measurementUnit: IMeasurementUnit


  constructor(@Inject(MAT_DIALOG_DATA) public data: IMeasurementUnit, 
                                       private formBuilder: FormBuilder,
                                       private measurementUnitService: MeasurementUnitService,
                                       private dialog: MatDialog) {
                this.measurementUnit = Object.assign({}, this.data)
               }

  ngOnInit(): void {
    this.configureForm()
  }

  configureForm() {
    this.form = this.formBuilder.group({
      description: [this.measurementUnit.description.charAt(0).toUpperCase() + this.measurementUnit.description.slice(1), [Validators.required]],
      symbol: [this.measurementUnit.symbol, [Validators.required]]
    })
  }

  update() {
    const newMeasurementUnit: IMeasurementUnit = { 
      id: this.measurementUnit.id,
      description: this.form.value.description.toLowerCase(),
      symbol: this.form.value.symbol
    }
    this.measurementUnitService.update(this.measurementUnit.id as number, newMeasurementUnit)
                               .subscribe(response => {
                                window.location.reload()
                               })
  }

}
