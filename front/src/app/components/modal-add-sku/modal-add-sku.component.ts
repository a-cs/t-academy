import { outputAst } from '@angular/compiler';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable } from 'rxjs';
import ICategory from '../../interfaces/ICategory';
import IMeasurementUnit from '../../interfaces/IMeasurementUnit';
import ISku from '../../interfaces/ISku';
import { CategoryService } from '../../service/category.service';
import { MeasurementUnitService } from '../../service/measurement-unit.service';
import { SkuService } from '../../service/sku.service';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { capitalize } from 'src/app/utils/utils';

@Component({
  selector: 'app-modal-add-sku',
  templateUrl: './modal-add-sku.component.html',
  styleUrls: ['./modal-add-sku.component.css'],
})
export class ModalAddSkuComponent implements OnInit {
  form: FormGroup;
  categories: ICategory[];
  units: IMeasurementUnit[];
  filteredCategories: Observable<ICategory[]>;
  filteredUnits: Observable<IMeasurementUnit[]>;
  firstCategory: ICategory;
  firstUnit: IMeasurementUnit;
  isWait: boolean = false;

  capitalize = capitalize

  constructor(
    private categoryService: CategoryService,
    private measurementUnitService: MeasurementUnitService,
    private skuService: SkuService,
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<ModalAddSkuComponent>,
    private notification: ToastrService
  ) {}

  ngOnInit(): void {
    this.categoryService.get().subscribe((res) => {
      this.categories = res;
      this.firstCategory = res[0];
      this.form.controls['category'].setValue(this.firstCategory);
    });
    this.measurementUnitService.get().subscribe((res) => {
      this.units = res;
      this.firstUnit = res[0];
      this.form.controls['measurementUnit'].setValue(this.firstUnit);
    });

    this.configureForm();

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
      name: ['', [Validators.required]],
      category: [null, [Validators.required]],
      measurementUnit: [null, [Validators.required]],
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
    console.log('cat', this.firstCategory);
    this.form.controls['category'].setValue(this.firstCategory);
  }

  unitFallback() {
    console.log('unit', this.firstUnit);
    this.form.controls['measurementUnit'].setValue(this.firstUnit);
  }

  create() {
    const newSku: ISku = {
      name: this.form.value.name,
      category: this.form.value.category,
      measurementUnit: this.form.value.measurementUnit,
    };
    this.isWait = true;
    this.skuService.create(newSku).subscribe(
      (respose) => {},
      (error) => {
        this.notification.error(error.error.message, 'Error', {
          progressBar: true,
        });
        this.isWait = false;
      },
      () => {
        this.notification.success(
          `Product ${newSku.name.toUpperCase()} was successfully created`,
          'Created!',
          {
            progressBar: true,
          }
        );
        this.isWait = false;
        this.skuService.skuChanged.emit();
        this.dialogRef.close();
      }
    );
  }
}
