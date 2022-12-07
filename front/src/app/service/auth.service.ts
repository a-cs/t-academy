import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import ITokenPayload from '../interfaces/ITokenPayload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtHelper = new JwtHelperService();

  constructor() { }

  public getToken(): string | null {
    return localStorage.getItem("T-WMS_token")
  }

  public validateRole(roleList: string[]): boolean {
    let token = this.getToken();
    if (token) {
      let decoded: ITokenPayload = this.jwtHelper.decodeToken(token)
      return roleList.includes(decoded.authorities[0])
    }
    return false
  }

  public getUsername(): string {
    let token = this.getToken();
    if (token) {
      let decoded: ITokenPayload = this.jwtHelper.decodeToken(token)
      return decoded.user_name
    }
    return ""
  }

  public getUserId(): number | null {
    let token = this.getToken();
    if (token) {
      let decoded: ITokenPayload = this.jwtHelper.decodeToken(token)
      return decoded.user_id
    }
    return null
  }

  public getUserBranchId(): number | null {
    let token = this.getToken();
    if (token) {
      let decoded: ITokenPayload = this.jwtHelper.decodeToken(token)
      if(decoded.user_branch_id)
        return decoded.user_branch_id
      else
        return null
    }
    return null
  }


  public buildHeader() {
    return new HttpHeaders().set("Authorization", "Bearer " + this.getToken())
  }

  public isAuthenticated(): boolean {
    const token = this.getToken()
    if (token)
      return !this.jwtHelper.isTokenExpired(token)
    return false
  }
}
