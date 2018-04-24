import {Injectable, state} from '@angular/core';
import {AuthHttp, JwtHelper} from 'angular2-jwt';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import 'rxjs/operator/delay';
import 'rxjs/operator/mergeMap';
import 'rxjs/operator/switchMap';
import {TOKEN_NAME} from './auth.constant';
import {ProfileUser} from '../models/ProfileUser';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {Headers, RequestOptionsArgs} from '@angular/http';
import {Tweet} from '../models/tweet';
import {PrivateUser} from '../models/PrivateUser';


const API_URL = environment.apiUrl;


@Injectable()
export class ProfileDataService {

  constructor(private http: AuthHttp, public router: Router) {
    console.log('testProfileDataServiceCtor');
  }

  private handleError(error: Response | any) {
    console.error('AppDataService::handleError', error);
    if (error === 'No JWT present or has expired') {
      this.router.navigateByUrl('/login');
    }
    return Observable.throw(error);
  }


  public getUserProfile(userid: number): Observable<ProfileUser> {
    return this.http
      .get('/api/users/' + userid)
      .map(response => {
        const user = response.json();
        console.log(user);
        return new ProfileUser(user);
      })
      .catch(error => {
        console.error('AppDataService::handleError', error);
        if (error.message === 'No JWT present or has expired') {
          this.router.navigateByUrl('/login');
        }
        return Observable.throw(error);
        });
  }


  public getUsernameProfile(username: string): Observable<ProfileUser> {
    return this.http
      .get('/api/users/username/' + username)
      .map(response => {
        const user = response.json();
        return new ProfileUser(user);
      })
      .catch(error => {
        console.error('AppDataService::handleError', error);
        if (error.message === 'No JWT present or has expired') {
          this.router.navigateByUrl('/login');
        }
        return Observable.throw(error);
        });
  }
  public getFollowing(userid: number): Observable<ProfileUser[]> {
    return this.http
      .get('/api/users/' + userid + '/following')
      .map(response => {
        const user = response.json();
        return user.map((o) => new ProfileUser(o) );
      })
      .catch(error => {
        console.error('AppDataService::handleError', error);
        if (error === 'No JWT present or has expired') {
          this.router.navigateByUrl('/login');
        }
        return Observable.throw(error);
      });
  }  public getFollowers(userid: number): Observable<ProfileUser[]> {
    return this.http
      .get('/api/users/' + userid + '/followers')
      .map(response => {
        const user = response.json();
        return user.map((o) => new ProfileUser(o) );
      })
      .catch(error => {
        console.error('AppDataService::handleError', error);
        if (error === 'No JWT present or has expired') {
          this.router.navigateByUrl('/login');
        }
        return Observable.throw(error);
      });
  }


  followUser(id: number, loggedinUser: number): Observable<boolean> {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });
    return this.http
      .post('/api/users/' + id + '/followers', loggedinUser, <RequestOptionsArgs>headers)
      .map(response => {
        return  response.json();
      })
      .catch(this.handleError);
  }

  unfollowUser(id: number, loggedinUser: number): Observable<boolean> {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });
    return this.http
      .delete('/api/users/' + id + '/followers', <RequestOptionsArgs>headers)
      .map(response => {
        return  response.json();
      })
      .catch(this.handleError);
  }


  updatePublicUser(profileUser: ProfileUser) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.put('/api/users', profileUser, <RequestOptionsArgs>{headers})
      ;
  }
}
