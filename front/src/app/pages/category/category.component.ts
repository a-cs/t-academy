import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import ICategory from 'src/app/interfaces/ICategory';
import { AuthService } from 'src/app/service/auth.service';
import { CategoryService } from 'src/app/service/category.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  categories: ICategory[] = [];

  isLoading: boolean = false;
  isError: boolean = false;

  length = 50;
  pageSize = 6;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = true;
  showPageSizeOptions = false;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent;

  constructor(
    private categoryService: CategoryService,
    public auth: AuthService,
    private notification: ToastrService
  ) {
    this.categoryService.categoryUpdatedOrDeleted.subscribe(() => {
      this.getCategories();
    });
  }

  btnPermission = buttonPermission;

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    this.isLoading = true
    this.isError = false
    this.categoryService
      .getPageable(this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.categories = data.content;
        this.length = data.totalElements;
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

  onSearchTextEntered(searchTerm: string) {
    this.isLoading = true
    this.isError = false
    this.categoryService.getByLikeName(searchTerm).subscribe((data) => {
      this.categories = data.content;
      this.length = data.totalElements;
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

    this.categoryService.categoryUpdatedOrDeleted.emit();
    this.isLoading = true
    this.isError = false
    this.categoryService
      .getPageable(this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.categories = data.content;
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
}
