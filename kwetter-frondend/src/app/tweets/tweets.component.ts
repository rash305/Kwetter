import { Component, OnInit } from '@angular/core';
import {TweetDataService} from '../services/tweet-data.service';
import {Tweet} from '../models/tweet';
import {TOKEN_NAME} from '../services/auth.constant';
import {JwtHelper} from 'angular2-jwt';
import {WebSocketService} from '../services/websocket.service';
import {Subject} from 'rxjs/Subject';

@Component({
  selector: 'app-tweets',
  templateUrl: './tweets.component.html',
  styleUrls: ['./tweets.component.css']
})
export class TweetsComponent implements OnInit {
  tweets$ = [];
  searchTag = '';
  jwtHelper = new JwtHelper();
  private ioConnection;

  constructor( private tweetDataService: TweetDataService) {
  }

  ngOnInit() {
    const decodedToken = this.jwtHelper.decodeToken(localStorage.getItem(TOKEN_NAME));

    this.tweetDataService.getTweets(decodedToken.userid).subscribe((tweets) => {
      this.tweets$ = tweets;
    });

  }

  searchTweets(): void {
    this.tweetDataService.searchTagTweets(this.searchTag).subscribe((returnTweet) => {
      this.tweets$ = returnTweet;
    });
  }





}
