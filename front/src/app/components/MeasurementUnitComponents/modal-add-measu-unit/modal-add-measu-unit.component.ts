import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';

@Component({
  selector: 'app-modal-add-measu-unit',
  templateUrl: './modal-add-measu-unit.component.html',
  styleUrls: ['./modal-add-measu-unit.component.css'],
})
export class ModalAddMeasuUnitComponent implements OnInit {
  form: FormGroup;
  measurementUnit: IMeasurementUnit;

  constructor(
    private formBuilder: FormBuilder,
    private measurementUnitService: MeasurementUnitService,
    private notification: ToastrService,
    private dialogRef: MatDialogRef<ModalAddMeasuUnitComponent>
  ) {}

  ngOnInit(): void {
    this.configureForm();
  }

  configureForm() {
    this.form = this.formBuilder.group({
      description: ['', [Validators.required]],
      symbol: ['', [Validators.required]],
    });
  }

  //create() {
  //const newMeasurementUnit: IMeasurementUnit = {
  //description: this.form.value.description.toLowerCase(),
  //symbol: this.form.value.symbol.toLowerCase()
  //}
  //this.measurementUnitService.create(newMeasurementUnit)
  //.subscribe(response => {
  //window.location.reload()
  //})
  //}

  create() {
    const newMeasurementUnit: IMeasurementUnit = {
      description: this.form.value.description.toLowerCase(),
      symbol: this.form.value.symbol.toLowerCase(),
    };
    this.measurementUnitService.create(newMeasurementUnit).subscribe(
      (respose) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error', {
          progressBar: true,
        });
      },
      () => {
        this.notification.success(
          `Measurement ${newMeasurementUnit.description.toUpperCase()} [${
            newMeasurementUnit.symbol
          }] successfuly created`,
          'Created!',
          {
            progressBar: true,
          }
        );
        this.measurementUnitService.measurementUnitChanged.emit();
        this.dialogRef.close();
      }
    );
  }
}
