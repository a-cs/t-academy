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

  onItemUpdate() {}

  ngOnInit(): void {
    this.categoryService.get().subscribe((data) => {
      this.categories = data;
    });
    console.log('CATEGORY PAGE!!');
  }

  refreshComponent() {
    window.location.reload();
  }

  onSearchTextEntered(data: String) {
    console.log(data);
  }
}
