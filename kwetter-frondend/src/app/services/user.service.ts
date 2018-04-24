import { Injectable } from '@angular/core';
import {JwtHelper} from 'angular2-jwt';
import {TOKEN_NAME} from './auth.constant';

@Injectable()
export class UserService {
  jwtHelper: JwtHelper = new JwtHelper();
  accessToken: string;
  id: number;

  constructor() {
  }

  login(accessToken: string) {
    const decodedToken = this.jwtHelper.decodeToken(accessToken);
    console.log(decodedToken);
    this.id = decodedToken.userid;
    this.accessToken = accessToken;

    localStorage.setItem(TOKEN_NAME, accessToken);
  }

  logout() {
    this.accessToken = null;
    localStorage.removeItem(TOKEN_NAME);
  }

  isAdminUser(): boolean {
    const decodedToken = this.jwtHelper.decodeToken(localStorage.getItem(TOKEN_NAME));

    return decodedToken.authorities.some(el => el === 'ADMIN_USER');
  }

  isUser(): boolean {
    return this.accessToken && !this.isAdminUser;
  }

  getLoggedInID() {
    const decodedToken = this.jwtHelper.decodeToken(localStorage.getItem(TOKEN_NAME));
    return decodedToken.userid;
  }
}
