import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientInventoryComponent } from './pages/client-inventory/client-inventory.component';
import { CategoryComponent } from './pages/category/category.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { MeasurementUnitComponent } from './pages/measurement-unit/measurement-unit.component';
import { ProductComponent } from './pages/product/product.component';
import { UserComponent } from './pages/user/user.component';
import { AuthGuardService } from './service/auth-guard.service';

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
  {
  path: 'login',
  component: LoginComponent
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'users',
    component: UserComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: ["ROLE_ADMIN","ROLE_MANAGER"]
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
