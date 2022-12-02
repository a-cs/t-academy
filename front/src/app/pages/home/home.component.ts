import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

import { routePermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor(private router: Router,
    public auth: AuthService) {}

  ngOnInit(): void {}

  permissions = routePermission

  navigate() {
    this.router.navigate(['/products']);
  }

  navigateToCategories() {
    this.router.navigate(['/categories']);
  }

  navigateClientInventory(){
    this.router.navigate(["client-inventory"])
  }

  navigateToMeasurementUnits() {
    this.router.navigate(['/measurement-units']);
  }

  navigateToBranches() {
    this.router.navigate(['/branches'])
}
  navigateUser(){
    this.router.navigate(["/users"])
  }
  navigateToClients(){
    this.router.navigate(["/clients"])
  }
}
