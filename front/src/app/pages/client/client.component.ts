import { Component, OnInit } from '@angular/core';
import IClient from 'src/app/interfaces/IClient';
import { ClientService } from 'src/app/service/client.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  clients: IClient[] = []

  constructor(private clientService:ClientService) { }

  ngOnInit(): void {
    this.clientService.get().subscribe((data) => {
      this.clients = data;
    });
    console.log('CLIENTS PAGE!!');
  }

  refreshComponent() {
    window.location.reload();
  }

  searchText: string = "";

  onSearchTextEntered(searchValue: string) {
    this.searchText = searchValue;
    console.log(this.searchText);    
    this.clientService.getByLikeName(this.searchText).subscribe(data => this.clients = data)
  }

}
