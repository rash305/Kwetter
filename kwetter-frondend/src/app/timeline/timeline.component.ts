import { Component, OnInit } from '@angular/core';
import {Tweet} from '../models/tweet';
import {TweetDataService} from '../services/tweet-data.service';
import {WebSocketService} from '../services/websocket.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  tweets$;
  createTweet$: Tweet = new Tweet();


  constructor(private tweetSocketService: WebSocketService, private tweetDataService: TweetDataService) {
  }

  ngOnInit() {
    this.tweetDataService.getTimeline().subscribe((tweets) => {
      this.tweets$ = tweets;
      this.initIoConnection();
    });
  }

  createtweet(): void {
    console.log(this.createTweet$.message);
    this.tweetDataService.createTweet(this.createTweet$).subscribe((returnTweet) => {
      this.tweets$.unshift(returnTweet);
    });
    this.createTweet$ = new Tweet();
  }

  private initIoConnection(): void {
    const websocket = this.tweetSocketService.connect('localhost:8080/kwetter-1.0-SNAPSHOT/websocket')
      .map((response: MessageEvent): Tweet => {
        const data: Tweet = new Tweet(JSON.parse(response.data));
        return data;
      });

    websocket.subscribe(tweet => {
      this.tweets$.unshift(tweet);
    });
  }

}
