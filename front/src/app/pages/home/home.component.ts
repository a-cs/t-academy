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
    public auth: AuthService) { }

  ngOnInit(): void { }

  permissions = routePermission

  navigateTo(path: string) {
    this.router.navigate([path])
  }
}
