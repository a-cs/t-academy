import { Component, OnInit } from '@angular/core';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  categories: ICategory[] = [];

  constructor(private categoryService: CategoryService) {}

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
