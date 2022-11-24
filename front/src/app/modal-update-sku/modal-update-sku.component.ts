import { Component, Inject, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog"
import ICategory from '../interfaces/ICategory';
import IMeasurementUnit from '../interfaces/IMeasurementUnit';
import ISku from '../interfaces/ISku';
import { CategoryService } from '../service/category.service';
import { MeasurementUnitService } from '../service/measurement-unit.service';
import { filter, map, startWith } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { MatAutocomplete } from '@angular/material/autocomplete';
import { SkuService } from '../service/sku.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-modal-update-sku',
  templateUrl: './modal-update-sku.component.html',
  styleUrls: ['./modal-update-sku.component.css']
})
export class ModalUpdateSkuComponent{
  form: FormGroup
  sku: ISku
  categories: ICategory[]
  units: IMeasurementUnit[]
  filteredCategories: Observable<ICategory[]>;
  filteredUnits: Observable<IMeasurementUnit[]>;
  firstCategory: ICategory;
  firstUnit: IMeasurementUnit;

  constructor(@Inject(MAT_DIALOG_DATA)
    public data: ISku,
    private router: Router,
    private categoryService: CategoryService,
    private measurementUnitService: MeasurementUnitService,
    private skuService: SkuService,
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<ModalUpdateSkuComponent>
  ) {

    this.sku = Object.assign({}, this.data)
  }

  ngOnInit(): void {
    this.categoryService.get().subscribe(
      res => {
        this.categories = res
      }
    )
    this.measurementUnitService.get().subscribe(
      res => {
        this.units = res
      }
    )

    this.configureForm()

    this.filteredCategories = this.form.controls["category"].valueChanges.pipe(
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.name;
        return name ? this._filterCategoiries(name as string) : this.categories.slice();
      }),
    );

    this.filteredUnits = this.form.controls["measurementUnit"].valueChanges.pipe(
      map((value: any) => {
        const str = value = typeof value === 'string' ? value : value?.description;
        return str ? this._filterUnits(str as string) : this.units.slice();
      }),
    );

    this.filteredCategories.subscribe(ele => this.firstCategory=ele[0])

    this.filteredUnits.subscribe(ele => this.firstUnit=ele[0])
  }

  private _filterCategoiries(name: string): ICategory[] {
    const filterValue = name.toLowerCase();
    return this.categories.filter(category => category.name?.toLowerCase().includes(filterValue));
  }

  private _filterUnits(description: string): IMeasurementUnit[] {
    const filterValue = description.toLowerCase().replace(" ", "").split("-");
    return this.units.filter(unit => (unit.description?.toLowerCase().includes(filterValue[0]) || unit.symbol?.toLowerCase().includes(filterValue[0])));
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: [this.sku.name, [Validators.required]],
      category: [this.sku.category, [Validators.required]],
      measurementUnit: [this.sku.measurementUnit, [Validators.required]]
    })
  }


  displayCategory(category: ICategory): string {
    return category && category.name ? category.name : '';
  }

  displayUnit(unit: IMeasurementUnit): string {
    return unit && unit.description ? `${unit.description} - ${unit.symbol}` : '';
  }

  categoryFallback() {
    console.log("cat", this.firstCategory)
    this.form.controls['category'].setValue(this.firstCategory)
  }

  unitFallback() {
    console.log("unit", this.firstUnit)
    this.form.controls['measurementUnit'].setValue(this.firstUnit)
  }

  update() {
    console.log(this.form.value)
    console.log("form error", this.form.errors)
    const newSku: ISku = { id: this.sku.id, name: this.form.value.name, category: this.form.value.category, measurementUnit: this.form.value.measurementUnit }
    this.skuService.update(this.sku.id as number, newSku).subscribe(response =>{window.location.reload()}, error =>  {console.log("err!",error)})

    // this.dialogRef.close(newSku)
    // window.location.reload()
  }
}
