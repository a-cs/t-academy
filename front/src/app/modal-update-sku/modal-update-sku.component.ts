import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from "@angular/material/dialog"
import ICategory from '../interfaces/ICategory';
import IMeasurementUnit from '../interfaces/IMeasurementUnit';
import ISku from '../interfaces/ISku';
import { CategoryService } from '../service/category.service';
import { MeasurementUnitService } from '../service/measurement-unit.service';
import {filter, map, startWith} from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { MatAutocomplete } from '@angular/material/autocomplete';
@Component({
  selector: 'app-modal-update-sku',
  templateUrl: './modal-update-sku.component.html',
  styleUrls: ['./modal-update-sku.component.css']
})
export class ModalUpdateSkuComponent {
  form: FormGroup
  sku: ISku
  categories: ICategory[]
  filteredCategories: Observable<ICategory[]>;
  units: IMeasurementUnit[]
  selectedCategory: ICategory
  selectedUnit: IMeasurementUnit
  @ViewChild(MatAutocomplete) matAutocomplete: MatAutocomplete

  constructor(@Inject(MAT_DIALOG_DATA) public data: ISku,
  private categoryService: CategoryService,
  private measurementUnitService: MeasurementUnitService,
  private formBuilder: FormBuilder,
  ) {

    this.sku = Object.assign({}, this.data)
    this.selectedCategory = Object.assign({}, this.sku.category)
    this.selectedUnit = Object.assign({}, this.sku.measurementUnit)
  }

  ngOnInit(): void {
    this.categoryService.get().subscribe(
      res => {
        this.categories = res
        console.log(this.categories)
      }
    )
    this.measurementUnitService.get().subscribe(
      res => {
        this.units = res
        console.log(this.units)
      }
    )

    this.configureForm()

    this.filteredCategories = this.form.controls["category"].valueChanges.pipe(
      map((value: any) => {
        // console.log(value)
        const name = value = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.categories.slice();
      }),
      );

    console.log("selectedCategory", this.selectedCategory)
  }

  private _filter(name: string): ICategory[] {
    const filterValue = name.toLowerCase();

    return this.categories.filter(category => category.name?.toLowerCase().includes(filterValue));
  }

  configureForm() {
    this.form = this.formBuilder.group({
      name: [this.sku.name, null],
      category: [this.sku.category, null],
      measurementUnit: [this.sku.measurementUnit, null]
    })
  }

  categoryFallback() {
    console.log("cat", this.form.value.category)
    // this.matAutocomplete.options.first.select();
    let filtered
    this.filteredCategories.subscribe(category => {
      console.log("fcat",category)
      filtered = category
    })
    console.log("filtered",filtered)
    console.log("cat atualizado", this.form.value.category)
    // this.form.value.category = this.filteredCategories[0]

  }

  validate(){
    // if(typeof this.form.value.category === 'string')
    //   return false
    // return true
  }
  update() {
    console.log(this.form.value)
    console.log("selecedCategoryId", this.selectedCategory)
  }

  displayFn(category: ICategory): string {
    return category && category.name ? category.name : '';
  }

}
