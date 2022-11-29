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
import { CategoryItemCardComponent } from './components/CategoryComponents/category-item-card/category-item-card.component';
import { CategoryUpdateFormComponent } from './components/CategoryComponents/catogory-update-form/catogory-update-form.component';
import { CategoryCreateFormComponent } from './components/CategoryComponents/category-create-form/category-create-form.component';
import { CategoryCreateCardComponent } from './components/CategoryComponents/category-create-card/category-create-card.component';
import { CategoryComponent } from './pages/category/category.component';

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
    CategoryItemCardComponent,
    CategoryUpdateFormComponent,
    CategoryCreateFormComponent,
    CategoryCreateCardComponent,
    CategoryComponent,
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
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
