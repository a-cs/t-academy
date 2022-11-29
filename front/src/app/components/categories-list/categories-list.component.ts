import { Component, OnInit } from '@angular/core';
import ICategory from 'src/app/interfaces/ICategory';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css'],
})
export class CategoriesListComponent implements OnInit {
  categories: ICategory[] = [];

  constructor(private categoryService: CategoryService) {}

  onItemUpdate() {}

  ngOnInit(): void {
    this.categoryService.get().subscribe((data) => {
      this.categories = data;
    });
  }

  refreshComponent() {
    window.location.reload();
  }

  onSearchTextEntered(data: String) {
    console.log(data);
  }
}
