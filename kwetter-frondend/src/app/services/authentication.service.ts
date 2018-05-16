import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import 'rxjs/add/operator/map';

import {TOKEN_AUTH_PASSWORD, TOKEN_AUTH_USERNAME} from '../services/auth.constant';
import {environment} from '../../environments/environment';
import {PrivateUser} from '../models/PrivateUser';

@Injectable()
export class AuthenticationService {


  constructor(private http: Http) {
  }

  login(username: string, password: string) {
    const body =         {
      'username': encodeURIComponent(username),
      'password': encodeURIComponent(password)
    };

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post('/api/auth', body, {headers})
      .map((res: any) => {
          return res.headers.get('Authorization');

      });
  }

  createUser(privateUser: PrivateUser) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post('/api/users', privateUser, {headers})
      .map((res: any) => {
        return new PrivateUser(res);

      });
  }
}
