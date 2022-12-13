import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import decode from 'jwt-decode';
import ITokenPayload from '../interfaces/ITokenPayload';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
  constructor(public auth: AuthService, public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string[] = route.data['expectedRoles'];
    const token = this.auth.getToken();

    if (token) {
      const tokenPayload: ITokenPayload = decode(token);
      if (!this.auth.isAuthenticated()) {
        this.router.navigate(['/login']);
        return false;
      }
      if (!expectedRoles.includes(tokenPayload.authorities[0])) {
        this.router.navigate(['']);
        return false;
      }
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
