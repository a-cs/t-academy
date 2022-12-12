import { Component, OnInit } from '@angular/core';
import IClient from 'src/app/interfaces/IClient';
import { ClientService } from 'src/app/service/client.service';
import { PageEvent } from '@angular/material/paginator';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css'],
})
export class ClientComponent implements OnInit {
  isLoading: boolean = false;
  isError: boolean = false;

  clients: IClient[] = [];
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = true;
  showPageSizeOptions = false;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent;

  constructor(private clientService: ClientService, private notification: ToastrService) {
    this.clientService.clientChanged.subscribe(() => this.getData());
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.isLoading = true
    this.isError = false
    this.clientService.getPageable(0, 10).subscribe((data) => {
      this.clients = data.content;
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

  refreshComponent() {
    window.location.reload();
  }

  searchText: string = '';

  onSearchTextEntered(searchValue: string) {
    this.isLoading = true
    this.isError = false
    this.searchText = searchValue;
    console.log(this.searchText);
    this.clientService
      .getByLikeName(this.searchText, 0, 10)
      .subscribe((data) => {
        this.clients = data.content;
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
    this.isLoading = true
    this.isError = false
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;

    if (this.searchText.length > 0) {
      this.clientService
        .getPageable(this.pageIndex, this.pageSize)
        .subscribe((data) => {
          this.clients = data.content;
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
    } else {
      this.clientService
        .getByLikeName(this.searchText, this.pageIndex, this.pageSize)
        .subscribe((data) => {
          this.clients = data.content;
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
}
