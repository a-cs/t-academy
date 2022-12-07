import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { AuthService } from 'src/app/service/auth.service';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ModalConfirmDeleteComponent } from '../../modal-confirm-delete/modal-confirm-delete.component';
import { ModalConfirmComponent } from '../../modal-confirm/modal-confirm.component';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-update-measu-unit',
  templateUrl: './modal-update-measu-unit.component.html',
  styleUrls: ['./modal-update-measu-unit.component.css'],
})
export class ModalUpdateMeasuUnitComponent implements OnInit {
  form: FormGroup;
  measurementUnit: IMeasurementUnit;

  showDeleteButton: boolean;
  showUpdateButton: boolean;
  showButtons: boolean;

  permissions = buttonPermission;

  isReadOnly: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: IMeasurementUnit,
    private formBuilder: FormBuilder,
    private measurementUnitService: MeasurementUnitService,
    private dialog: MatDialog,
    public auth: AuthService,
    public notification: ToastrService,
    private dialogRef: MatDialogRef<ModalUpdateMeasuUnitComponent>
  ) {
    this.measurementUnit = Object.assign({}, this.data);
  }

  ngOnInit(): void {
    this.configureForm();
    this.showDeleteButton = false;
    this.showUpdateButton = this.auth.validateRole(this.permissions.updateUnit);
    this.showButtons = false;

    this.isReadOnly = this.showUpdateButton;
  }

  configureForm() {
    this.form = this.formBuilder.group({
      description: [
        this.measurementUnit.description.charAt(0).toUpperCase() +
          this.measurementUnit.description.slice(1),
        [Validators.required],
      ],
      symbol: [this.measurementUnit.symbol, [Validators.required]],
    });
  }

  //update() {
  //const newMeasurementUnit: IMeasurementUnit = {
  //id: this.measurementUnit.id,
  //description: this.form.value.description.toLowerCase(),
  //symbol: this.form.value.symbol.toLowerCase()
  //}
  //this.measurementUnitService.update(this.measurementUnit.id as number, newMeasurementUnit)
  //.subscribe(response => {
  //window.location.reload()
  //})
  //}

  update() {
    const newMeasurementUnit: IMeasurementUnit = {
      id: this.measurementUnit.id,
      description: this.form.value.description.toLowerCase(),
      symbol: this.form.value.symbol.toLowerCase(),
    };
    this.measurementUnitService
      .update(this.measurementUnit.id as number, newMeasurementUnit)
      .subscribe(
        (response) => {},
        (error) => {
          this.notification.error(error.error.message, 'Error!', {
            progressBar: true,
          });
        },
        () => {
          this.notification.success(
            `Category ${newMeasurementUnit.description.toUpperCase()} [${
              newMeasurementUnit.symbol
            }] was successfuly updated`,
            'Updated!',
            { progressBar: true }
          );
          this.measurementUnitService.measurementUnitChanged.emit();
          this.dialogRef.close();
        }
      );
  }

  //openConfirmDialog() {
  //this.dialog.open(ModalConfirmComponent, {
  //width: '600px',
  //height: '600px',
  //data: {
  //observable: this.measurementUnitService.delete(
  //this.measurementUnit.id as number
  //),
  //object: 'measurement unit',
  //},
  //});
  //}

  openConfirmDialog() {
    const dialogRefConfirmDelete = this.dialog.open(
      ModalConfirmDeleteComponent,
      {
        width: '600px',
        height: '600px',
      }
    );

    dialogRefConfirmDelete.afterClosed().subscribe((deleteConfirmed) => {
      if (deleteConfirmed) {
        this.measurementUnitService
          .delete(this.measurementUnit.id as number)
          .subscribe(
            (response) => {},
            (error) => {
              this.notification.error(error.error.message, 'Error!', {
                progressBar: true,
              });
            },
            () => {
              this.notification.warning(
                `Measurement unit ${this.measurementUnit.description.toUpperCase()} [${
                  this.measurementUnit.symbol
                }] was successfuly deleted`,
                'Deleted!',
                { progressBar: true }
              );
              this.measurementUnitService.measurementUnitChanged.emit();
              this.dialogRef.close();
            }
          );
      }
    });
  }

  clickOnEdit() {
    this.showDeleteButton = true;
    this.showUpdateButton = false;
    this.showButtons = true;
    this.isReadOnly = this.showUpdateButton;
  }
}
