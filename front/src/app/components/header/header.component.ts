import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { fadeInOnEnterAnimation, fadeOutOnLeaveAnimation } from 'angular-animations';
import { AuthService } from 'src/app/service/auth.service';
import { routePermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  animations: [
    fadeInOnEnterAnimation(),
  ]
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router, public auth: AuthService) { }

  permissions = routePermission

  ngOnInit(): void {
  }

  isMenuOpen: boolean = false;

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  navigate(path:string){
    this.router.navigate([path])
  }

  signout(){
    localStorage.removeItem("T-WMS_token")
    this.router.navigate(["/login"])
  }

}
