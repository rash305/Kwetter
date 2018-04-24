import { Component, OnInit } from '@angular/core';
import {Tweet} from '../models/tweet';
import {TweetDataService} from '../services/tweet-data.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  tweets$;
  createTweet$: Tweet = new Tweet();


  constructor(private tweetDataService: TweetDataService) {
  }

  ngOnInit() {
    this.tweetDataService.getTimeline().subscribe((tweets) => {
      this.tweets$ = tweets;
    });
  }

  createtweet(): void {
    console.log(this.createTweet$.message);
    this.tweetDataService.createTweet(this.createTweet$).subscribe((returnTweet) => {
      this.tweets$.unshift(returnTweet);
    });
    this.createTweet$ = new Tweet();
  }

}
