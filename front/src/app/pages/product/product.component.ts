import { Component, OnInit } from '@angular/core';
import ISku from 'src/app/interfaces/ISku';
import { SkuService } from 'src/app/service/sku.service';
import { ModalAddSkuComponent } from "../../components/modal-add-sku/modal-add-sku.component";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  component = ModalAddSkuComponent
  skus: ISku[] = []
  constructor(private skuService:SkuService) {

  }

  ngOnInit(): void {
    this.skuService.get().subscribe(
      data => {
        this.skus = data
        console.log(this.skus)
      }
    )
  }

  searchText: string = "";

  onSearchTextEntered(searchValue: string){
    this.searchText = searchValue;
    this.skuService.getByLikeName(this.searchText).subscribe(data => this.skus = data)
  }
}
