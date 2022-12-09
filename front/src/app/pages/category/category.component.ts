import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import ICategory from 'src/app/interfaces/ICategory';
import { AuthService } from 'src/app/service/auth.service';
import { CategoryService } from 'src/app/service/category.service';
import { buttonPermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  categories: ICategory[] = [];
  
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
    public auth: AuthService
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
    this.categoryService
      .getPageable(this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.categories = data.content;
        this.length = data.totalPages;
      });
  }

  onSearchTextEntered(searchTerm: string) {
    this.categoryService.getByLikeName(searchTerm).subscribe((data) => {
      this.categories = data.content;
      this.length = data.totalPages;
    });
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    this.categoryService.categoryUpdatedOrDeleted.emit();
    this.categoryService
      .getPageable(this.pageIndex, this.pageSize)
      .subscribe((data) => {
        this.categories = data.content;
        this.length = data.totalPages;
        this.pageSize = data.size;
        this.pageIndex = data.number;
      });
  }
}
