import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { AuthService } from 'src/app/service/auth.service';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';

@Component({
  selector: 'app-modal-update-measu-unit',
  templateUrl: './modal-update-measu-unit.component.html',
  styleUrls: ['./modal-update-measu-unit.component.css']
})
export class ModalUpdateMeasuUnitComponent implements OnInit {

  form: FormGroup
  measurementUnit: IMeasurementUnit

  showDeleteButton: boolean
  showUpdateButton:boolean
  showButtons: boolean
  
  permissions = buttonPermission

  isReadOnly: boolean


  constructor(@Inject(MAT_DIALOG_DATA) public data: IMeasurementUnit, 
                                       private formBuilder: FormBuilder,
                                       private measurementUnitService: MeasurementUnitService,
                                       private dialog: MatDialog,
                                       public auth: AuthService) {
                this.measurementUnit = Object.assign({}, this.data)
               }

  ngOnInit(): void {
    this.configureForm()
    this.showDeleteButton = false
    this.showUpdateButton = true
    this.showButtons = false

    this.isReadOnly = !this.auth.validateRole(this.permissions.updateUnit)
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
      symbol: this.form.value.symbol.toLowerCase()
    }
    this.measurementUnitService.update(this.measurementUnit.id as number, newMeasurementUnit)
                               .subscribe(response => {
                                window.location.reload()
                               })
  }

  openConfirmDialog() {
    this.dialog.open(ModalConfirmComponent, {
      width: "600px",
      height: "600px",
      data: {
        observable: this.measurementUnitService.delete(this.measurementUnit.id as number),
        object: "measurement unit"
      }
    })
  }

  clickOnEdit() {
    this.showDeleteButton = true
    this.showUpdateButton = false
    this.showButtons = true
  }

}
