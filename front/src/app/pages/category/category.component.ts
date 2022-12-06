import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Route, Router } from '@angular/router';
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
  pageSize = 10;
  pageIndex = 0;
  length = 50;

  constructor(
    private categoryService: CategoryService,
    public auth: AuthService
  ) {
    this.categoryService.categoryUpdatedOrDeleted.subscribe(() => {
      console.log('Updated or deleted');
      this.categoryService.get().subscribe((sucessData) => {
        this.categories = sucessData.content;
      });
    });
  }

  btnPermission = buttonPermission;

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.get().subscribe((categories) => {
      this.categories = categories.content;
    });
  }

  onSearchTextEntered(searchTerm: string) {
    this.categoryService
      .getByLikeName(searchTerm)
      .subscribe((data) => (this.categories = data));
  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log('pageSize: ' + this.pageSize);
    console.log('pageIndex: ' + this.pageIndex);
  }
}
