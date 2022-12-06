import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './pages/product/product.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { CardSkuComponent } from './components/card-sku/card-sku.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalUpdateSkuComponent } from './components/modal-update-sku/modal-update-sku.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { ModalConfirmComponent } from './components/modal-confirm/modal-confirm.component';
import { CardCreateSkuComponent } from './components/card-create-sku/card-create-sku.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { ModalAddSkuComponent } from './components/modal-add-sku/modal-add-sku.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { UserComponent } from './pages/user/user.component';
import { CardUserComponent } from './components/UsersComponents/card-user/card-user.component';
import { ModalUpdateUserComponent } from './components/modal-update-user/modal-update-user.component';
import { ClientInventoryComponent } from './pages/client-inventory/client-inventory.component';
import { CardClientSkuComponent } from './components/card-client-sku/card-client-sku.component';
import { MatSelectModule } from '@angular/material/select';
import { CategoryItemCardComponent } from './components/CategoryComponents/category-item-card/category-item-card.component';
import { CategoryUpdateFormComponent } from './components/CategoryComponents/catogory-update-form/catogory-update-form.component';
import { CategoryCreateFormComponent } from './components/CategoryComponents/category-create-form/category-create-form.component';
import { CategoryCreateCardComponent } from './components/CategoryComponents/category-create-card/category-create-card.component';
import { CategoryComponent } from './pages/category/category.component';
import { MeasurementUnitComponent } from './pages/measurement-unit/measurement-unit.component';

import { CardClientComponent } from './components/ClientComponents/card-client/card-client.component';
import { ModalUpdateClientComponent } from './components/ClientComponents/modal-update-client/modal-update-client.component';
import { ClientComponent } from './pages/client/client.component';
import { ModalAddClientComponent } from './components/ClientComponents/modal-add-client/modal-add-client.component';
import { ModalCreateClientComponent } from './components/ClientComponents/modal-create-client/modal-create-client.component';

import { CardMeasuUnitComponent } from './components/MeasurementUnitComponents/card-measu-unit/card-measu-unit.component';
import { ModalUpdateMeasuUnitComponent } from './components/MeasurementUnitComponents/modal-update-measu-unit/modal-update-measu-unit.component';
import { ModalAddMeasuUnitComponent } from './components/MeasurementUnitComponents/modal-add-measu-unit/modal-add-measu-unit.component';
import { CardBranchComponent } from './components/BranchComponents/card-branch/card-branch.component';
import { BranchComponent } from './pages/branch/branch.component';
import { ModalShowMoreComponent } from './components/BranchComponents/modal-show-more/modal-show-more.component';
import { ModalAddBranchComponent } from './components/BranchComponents/modal-add-branch/modal-add-branch.component';
import { ModalShowClientInventoryComponent } from './components/modal-show-client-inventory/modal-show-client-inventory.component';
import { ConfirmationComponent } from './pages/confirmation/confirmation.component';

import { ToastrModule } from 'ngx-toastr';
import { ModalConfirmDeleteComponent } from './components/modal-confirm-delete/modal-confirm-delete.component';
import { NotFoundComponent } from './components/404/not-found/not-found.component';
import { NotFoundIconsBgComponent } from './components/404/not-found-icons-bg/not-found-icons-bg.component';
import { CreateTransactionComponent } from './pages/create-transaction/create-transaction.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
    HeaderComponent,
    FooterComponent,
    CardSkuComponent,
    ModalUpdateSkuComponent,
    ModalConfirmComponent,
    CardCreateSkuComponent,
    SearchBarComponent,
    ModalAddSkuComponent,
    HomeComponent,
    LoginComponent,
    UserComponent,
    CardUserComponent,
    ModalUpdateUserComponent,
    ClientInventoryComponent,
    CardClientSkuComponent,
    MeasurementUnitComponent,
    CardMeasuUnitComponent,
    ModalUpdateMeasuUnitComponent,
    ModalAddMeasuUnitComponent,
    CategoryItemCardComponent,
    CategoryUpdateFormComponent,
    CategoryCreateFormComponent,
    CategoryCreateCardComponent,
    CardClientComponent,
    ModalUpdateClientComponent,
    ClientComponent,
    ModalAddClientComponent,
    ModalCreateClientComponent,
    CardBranchComponent,
    BranchComponent,
    ModalShowMoreComponent,
    CategoryComponent,
    ModalAddBranchComponent,
    ModalShowClientInventoryComponent,
    CategoryComponent,
    ConfirmationComponent,
    ModalConfirmDeleteComponent,
    NotFoundComponent,
    NotFoundIconsBgComponent,
    CreateTransactionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatIconModule,
    MatAutocompleteModule,
    MatButtonModule,
    FormsModule,
    MatSelectModule,
    ToastrModule.forRoot(),
  ],
  providers: [ModalConfirmDeleteComponent],
  bootstrap: [AppComponent],
})
export class AppModule {}
