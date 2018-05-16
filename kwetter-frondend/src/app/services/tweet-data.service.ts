import {Injectable} from '@angular/core';
import {AuthHttp, JwtHelper} from 'angular2-jwt';
import {environment} from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {TweetsComponent} from '../tweets/tweets.component';
import {Tweet} from '../models/tweet';
import {RequestOptionsArgs} from '@angular/http';
import {UserService} from './user.service';
import {TOKEN_NAME} from './auth.constant';


const API_URL = environment.apiUrl;


@Injectable()
export class TweetDataService {
  jwtHelper: JwtHelper = new JwtHelper();
  headers: Headers = new Headers({
    'Content-Type': 'application/json'
  });
  constructor(private http: AuthHttp) {

  }

  private handleError(error: Response | any) {
    console.error('AppDataService::handleError', error);
    return Observable.throw(error);
  }


  public getTweets(userid: number): Observable<Tweet[]> {

    return this.http
      .get('/api/users/' + userid + '/tweets')
      .map(response => {
        const tweet = response.json();
        console.log(tweet);
        return tweet.map((o) => new Tweet(o));
      })
      .catch(this.handleError);
  }

  public getTimeline(): Observable<Tweet[]> {
    const decodedToken = this.jwtHelper.decodeToken(localStorage.getItem(TOKEN_NAME));


    return this.http
      .get('/api/users/' + decodedToken.userid + '/timeline')
      .map(response => {
        const tweet = response.json();
        console.log(tweet);
        return tweet.map((o) => new Tweet(o));
      })
      .catch(this.handleError);
  }

  public createTweet(tweet: Tweet): Observable<Tweet[]> {

    return this.http
      .post('/api/tweets/', tweet, <RequestOptionsArgs>this.headers)
      .map(response => {
        const tweetresponse = response.json();
        console.log(tweetresponse);
        return  new Tweet(tweetresponse);
      })
      .catch(this.handleError);
  }



  public likeTweet(tweetid: number): Observable<Tweet> {

    return this.http
      .post('/api/tweets/' + tweetid + '/like', <RequestOptionsArgs>this.headers)
      .map(response => {
        const tweetresponse = response.json();
        console.log(tweetresponse);
        return  new Tweet(tweetresponse);
      })
      .catch(this.handleError);
  }



  getUsers() {
    return this.http.get('/springjwt/users').map(res => res.json());
  }

  removeTweetLike(tweetid: number): Observable<Tweet> {
    return this.http
      .delete('/api/tweets/' + tweetid + '/like', <RequestOptionsArgs>this.headers)
      .map(response => {
        const tweetresponse = response.json();
        console.log(tweetresponse);
        return  new Tweet(tweetresponse);
      })
      .catch(this.handleError);
  }


  public searchTagTweets(tag: string): Observable<Tweet[]> {


    return this.http
      .post('/api/tweets/tag/', tag, <RequestOptionsArgs> this.headers )
      .map(response => {
        const tweet = response.json();
        console.log(tweet);
        return tweet.map((o) => new Tweet(o));
      })
      .catch(this.handleError);
  }
}
