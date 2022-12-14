import { Component, Inject, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import ICategory from '../../interfaces/ICategory';
import IMeasurementUnit from '../../interfaces/IMeasurementUnit';
import ISku from '../../interfaces/ISku';
import { CategoryService } from '../../service/category.service';
import { MeasurementUnitService } from '../../service/measurement-unit.service';
import { filter, map, startWith } from 'rxjs/operators';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { MatAutocomplete } from '@angular/material/autocomplete';
import { SkuService } from '../../service/sku.service';
import { Router } from '@angular/router';
import { ModalConfirmComponent } from '../modal-confirm/modal-confirm.component';
import { buttonPermission, capitalize } from 'src/app/utils/utils';
import { AuthService } from 'src/app/service/auth.service';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ModalConfirmDeleteComponent } from '../modal-confirm-delete/modal-confirm-delete.component';
@Component({
  selector: 'app-modal-update-sku',
  templateUrl: './modal-update-sku.component.html',
  styleUrls: ['./modal-update-sku.component.css'],
})
export class ModalUpdateSkuComponent {
  form: FormGroup;
  sku: ISku;
  categories: ICategory[];
  units: IMeasurementUnit[];
  filteredCategories: Observable<ICategory[]>;
  filteredUnits: Observable<IMeasurementUnit[]>;
  firstCategory: ICategory;
  firstUnit: IMeasurementUnit;

  permissions = buttonPermission;

  showDeleteButton: boolean;
  showUpdateButton: boolean;
  showButtons: boolean;

  isReadOnly: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: ISku,
    private router: Router,
    private categoryService: CategoryService,
    private measurementUnitService: MeasurementUnitService,
    private skuService: SkuService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<ModalUpdateSkuComponent>,
    public auth: AuthService,
    private notification: ToastrService
  ) {
    this.sku = Object.assign({}, this.data);
  }

  ngOnInit(): void {
    this.categoryService.get().subscribe((res) => {
      this.categories = res;
    });
    this.measurementUnitService.get().subscribe((res) => {
      this.units = res;
    });

    this.configureForm();

    this.showDeleteButton = false;
    this.showUpdateButton = this.auth.validateRole(
      this.permissions.updateProduct
    );
    this.showButtons = false;

    this.isReadOnly = this.showUpdateButton;

    this.filteredCategories = this.form.controls['category'].valueChanges.pipe(
      map((value: any) => {
        const name = (value = typeof value === 'string' ? value : value?.name);
        return name
          ? this._filterCategoiries(name as string)
          : this.categories.slice();
      })
    );

    this.filteredUnits = this.form.controls[
      'measurementUnit'
    ].valueChanges.pipe(
      map((value: any) => {
        const str = (value =
          typeof value === 'string' ? value : value?.description);
        return str ? this._filterUnits(str as string) : this.units.slice();
      })
    );

    this.firstCategory = this.sku.category;

    this.firstUnit = this.sku.measurementUnit;

    this.filteredCategories.subscribe((ele) => (this.firstCategory = ele[0]));

    this.filteredUnits.subscribe((ele) => (this.firstUnit = ele[0]));
  }

  private _filterCategoiries(name: string): ICategory[] {
    const filterValue = name.toLowerCase();
    return this.categories.filter((category) =>
      category.name?.toLowerCase().includes(filterValue)
    );
  }

  private _filterUnits(description: string): IMeasurementUnit[] {
    const filterValue = description.toLowerCase().replace(' ', '').split('-');
    return this.units.filter(
      (unit) =>
        unit.description?.toLowerCase().includes(filterValue[0]) ||
        unit.symbol?.toLowerCase().includes(filterValue[0])
    );
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: [this.sku.name, [Validators.required]],
      category: [this.sku.category, [Validators.required]],
      measurementUnit: [this.sku.measurementUnit, [Validators.required]],
    });
  }

  displayCategory(category: ICategory): string {
    return category && category.name ? category.name : '';
  }

  displayUnit(unit: IMeasurementUnit): string {
    return unit && unit.description
      ? `${capitalize(unit.description)} - ${unit.symbol}`
      : '';
  }

  categoryFallback() {
    this.form.controls['category'].setValue(this.firstCategory);
  }

  unitFallback() {
    this.form.controls['measurementUnit'].setValue(this.firstUnit);
  }

  update() {
    const newSku: ISku = {
      id: this.sku.id,
      name: this.form.value.name,
      category: this.form.value.category,
      measurementUnit: this.form.value.measurementUnit,
    };
    this.skuService.update(this.sku.id as number, newSku).subscribe(
      (response) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error!', {
          progressBar: true,
        });
      },
      () => {
        this.notification.success(
          `Product ${this.sku.name.toUpperCase()} was successfully updated`,
          'Updated!',
          { progressBar: true }
        );
        this.skuService.skuChanged.emit();
        this.dialogRef.close();
      }
    );

  }

  openConfirmDialog() {
    const confirmDialogRef = this.dialog.open(ModalConfirmDeleteComponent, {
      width: '600px',
      height: '600px',
    });

    confirmDialogRef.afterClosed().subscribe((deletionConfirmed) => {
      if (deletionConfirmed) {
        this.skuService.delete(this.sku.id as number).subscribe(
          (response) => {},
          (error) => {
            this.notification.error(error.error.message, 'Error!', {
              progressBar: true,
            });
          },
          () => {
            this.notification.warning(
              `Product ${this.sku.name.toUpperCase()} was successfully deleted`,
              'Deleted!',
              { progressBar: true }
            );
            this.skuService.skuChanged.emit();
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
