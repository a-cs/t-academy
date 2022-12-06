import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BranchComponent } from './pages/branch/branch.component';
import { ClientInventoryComponent } from './pages/client-inventory/client-inventory.component';
import { CategoryComponent } from './pages/category/category.component';
import { ClientComponent } from './pages/client/client.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { MeasurementUnitComponent } from './pages/measurement-unit/measurement-unit.component';
import { ProductComponent } from './pages/product/product.component';
import { UserComponent } from './pages/user/user.component';
import { AuthGuardService } from './service/auth-guard.service';
import { routePermission } from './utils/utils';
import { ConfirmationComponent } from './pages/confirmation/confirmation.component';

const routes: Routes = [
  {
    path: 'products',
    component: ProductComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.products
    }
  },
  {
    path: 'clients',
    component: ClientComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.clients
    }
  },
  {
    path: 'client-inventory',
    component: ClientInventoryComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.clientInventory
    }
  },
  {
    path: 'measurement-units',
    component: MeasurementUnitComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.measurementUnits
    }
  },
  {
    path: 'categories',
    component: CategoryComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.categories
    }
  },
  {
    path: 'branches',
    component: BranchComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.branches
    }
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.home
    }
  },
  {
    path: 'users',
    component: UserComponent,
    canActivate: [AuthGuardService],
    data: {
      expectedRoles: routePermission.users
    }
  },
  {
    path: 'confirmation',
    component: ConfirmationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
