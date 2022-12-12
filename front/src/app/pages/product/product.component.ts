import { Component, OnInit } from '@angular/core';
import ISku from 'src/app/interfaces/ISku';
import { AuthService } from 'src/app/service/auth.service';
import { SkuService } from 'src/app/service/sku.service';
import { ModalAddSkuComponent } from '../../components/modal-add-sku/modal-add-sku.component';
import { buttonPermission } from 'src/app/utils/utils';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
    isLoading: boolean = false;
    isError: boolean = false;
    component = ModalAddSkuComponent;
    skus: ISku[] = [];

    length = 50;
    pageSize = 10;
    pageIndex = 0;
    pageSizeOptions = [5, 10, 25];

    hidePageSize = true;
    showPageSizeOptions = false;
    showFirstLastButtons = true;
    disabled = false;

    pageEvent: PageEvent;

    constructor(private skuService: SkuService, public auth: AuthService, private notification: ToastrService) { }

    btnPermission = buttonPermission;

    ngOnInit(): void {
        this.skuService.skuChanged.subscribe(() => {
            this.getData();
        });

        this.isError = false
        this.isLoading = true
        this.skuService.getPageable(0, 10).subscribe((data) => {
            this.skus = data.content;
            this.length = data.totalElements;
            this.pageSize = data.size;
            this.pageIndex = data.number;
            this.isLoading = false
        }, error => {
            this.isLoading = false
            this.isError = true
            this.notification.error(error.error.message, 'Error: No serve response', {
              tapToDismiss: true,
              disableTimeOut: true,
              closeButton: true,
            });
          });
    }

    getData() {
        this.isError = false
        this.isLoading = true
        console.log("entrou")
        this.skuService.getPageable(0, 10).subscribe((data) => {
            this.skus = data.content;
            this.length = data.totalElements;
            this.pageSize = data.size;
            this.pageIndex = data.number;
            this.isLoading = false
        }, error => {
            this.isLoading = false
            this.isError = true
            this.notification.error(error.error.message, 'Error: No serve response', {
              tapToDismiss: true,
              disableTimeOut: true,
              closeButton: true,
            });
          });
    }

    searchText: string = '';

    onSearchTextEntered(searchValue: string) {
        this.isError = false
        this.isLoading = true
        this.searchText = searchValue;
        this.skuService.getByLikeName(this.searchText, 0, 10).subscribe((data) => {
            this.skus = data.content;
            this.length = data.totalElements;
            this.pageSize = data.size;
            this.pageIndex = data.number;
            this.isLoading = false
        }, error => {
            this.isLoading = false
            this.isError = true
            this.notification.error(error.error.message, 'Error: No serve response', {
              tapToDismiss: true,
              disableTimeOut: true,
              closeButton: true,
            });
          });
    }

    handlePageEvent(e: PageEvent) {
        this.pageEvent = e;
        this.length = e.length;
        this.pageSize = e.pageSize;
        this.pageIndex = e.pageIndex;

        if (this.searchText.length > 0) {
            this.skuService
                .getPageable(this.pageIndex, this.pageSize)
                .subscribe((data) => {
                    this.skus = data.content;
                    this.length = data.totalElements;
                    this.pageSize = data.size;
                    this.pageIndex = data.number;
                });
        } else {
            this.skuService
                .getByLikeName(this.searchText, this.pageIndex, this.pageSize)
                .subscribe((data) => {
                    this.skus = data.content;
                    this.length = data.totalElements;
                    this.pageSize = data.size;
                    this.pageIndex = data.number;
                });
        }
    }
}
