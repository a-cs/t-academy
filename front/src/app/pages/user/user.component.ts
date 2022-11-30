import { Component, OnInit } from '@angular/core';
import IUser from 'src/app/interfaces/IUser';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  userList: IUser[]
  searchText: string = "";

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.get().subscribe(data=>{
      this.userList = data
      console.log(data);
    })
  }


  onSearchTextEntered(searchValue: string){
    this.searchText = searchValue;
    this.userService.getByUsernameLike(this.searchText).subscribe(data => {
      this.userList = data
      console.log(data)})
  }
}
