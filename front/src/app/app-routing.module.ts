import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientInventoryComponent } from './pages/client-inventory/client-inventory.component';
import { CategoryComponent } from './pages/category/category.component';
import { HomeComponent } from './pages/home/home.component';
import { MeasurementUnitComponent } from './pages/measurement-unit/measurement-unit.component';
import { ProductComponent } from './pages/product/product.component';

const routes: Routes = [

  {
    path: 'products',
    component: ProductComponent,
  },
  {
  path: 'client-inventory',
  component: ClientInventoryComponent
  },
  {
  path: 'measurement-units',
  component: MeasurementUnitComponent
 },
  {
    path: 'categories',
    component: CategoryComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
