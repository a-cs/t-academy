import { Component, OnInit } from '@angular/core';
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

  constructor(
    private categoryService: CategoryService,
    public auth: AuthService,
    private route: Router
  ) {
    this.categoryService.categoryUpdatedOrDeleted.subscribe(() => {
      console.log('Updated or deleted');
      this.categoryService.get().subscribe((sucessData) => {
        this.categories = [];
        this.categories = Object.assign([], sucessData);
      });
    });
  }

  btnPermission = buttonPermission;

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryService.get().subscribe((categories) => {
      this.categories = categories;
    });
  }

  onSearchTextEntered(searchTerm: string) {
    this.categoryService
      .getByLikeName(searchTerm)
      .subscribe((data) => (this.categories = data));
  }
}
