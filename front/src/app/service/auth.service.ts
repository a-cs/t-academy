import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper = new JwtHelperService();

  constructor() {}

  public getToken():string | null {
    return localStorage.getItem("T-WMS_token")
  }

  public buildHeader(){
    return new HttpHeaders().set("Authorization", "Bearer " + this.getToken())
  }

  public isAuthenticated():boolean {
    const token = this.getToken()
    if(token)
      return !this.jwtHelper.isTokenExpired(token)
    return false
  }
}
