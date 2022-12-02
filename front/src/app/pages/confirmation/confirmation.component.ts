import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  msg:string;

  constructor(private userService: UserService,
    private activateRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activateRoute.queryParams
      .subscribe(params => {
        this.userService.confirmEmail(params['token']).subscribe(res=>{
          console.log(res)
          this.msg=res;
        }, err=>{
          this.msg=err.error.message})
      })
  }
  

}
