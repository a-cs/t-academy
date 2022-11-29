import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientInventoryComponent } from './pages/client-inventory/client-inventory.component';
import { HomeComponent } from './pages/home/home.component';
import { ProductComponent } from './pages/product/product.component';

const routes: Routes = [
{
  path: 'products',
  component: ProductComponent
},
{
  path: 'client-inventory',
  component: ClientInventoryComponent
},
{
  path: '',
  component: HomeComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
