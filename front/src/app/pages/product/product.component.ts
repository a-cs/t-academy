import { Component, OnInit } from '@angular/core';
import ISku from 'src/app/interfaces/ISku';
import { AuthService } from 'src/app/service/auth.service';
import { SkuService } from 'src/app/service/sku.service';
import { ModalAddSkuComponent } from "../../components/modal-add-sku/modal-add-sku.component";
import { buttonPermission } from 'src/app/utils/utils';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  component = ModalAddSkuComponent
  skus: ISku[] = []

  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = true;
  showPageSizeOptions = false;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent;

  constructor(private skuService:SkuService,
    public auth: AuthService) {
  }

  btnPermission = buttonPermission;

  ngOnInit(): void {
    this.skuService.getPageable(0,10).subscribe(
      data => {
        this.skus = data.content
        console.log(this.skus)
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      }
    )
  }

  searchText: string = "";

  onSearchTextEntered(searchValue: string){
    this.searchText = searchValue;
    this.skuService.getByLikeName(this.searchText,0,10).subscribe(data => {
      this.skus = data.content
      this.length = data.totalElements
      this.pageSize = data.size
      this.pageIndex = data.number
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    if(this.searchText.length>0){
      this.skuService.getPageable(this.pageIndex, this.pageSize).subscribe((data) => {
        this.skus = data.content;
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      });
    }
    else{
      this.skuService.getByLikeName(this.searchText,this.pageIndex, this.pageSize).subscribe((data) => {
        this.skus = data.content;
        this.length = data.totalElements
        this.pageSize = data.size
        this.pageIndex = data.number
      });
    }
  }
}
