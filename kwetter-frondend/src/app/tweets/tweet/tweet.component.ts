import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../../models/tweet';
import {TweetDataService} from '../../services/tweet-data.service';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent implements OnInit {

  @Input()
  tweet$: Tweet ;
  likedByLoggedIn: boolean;

  constructor(private tweetDataService: TweetDataService, private userService: UserService) {}

  ngOnInit() {
  }

  like() {
    if (this.likedByLoggedIn) {
      this.tweetDataService.removeTweetLike(this.tweet$.id).subscribe((returnTweet) => {
        this.tweet$ = returnTweet;
    }); } else {
      this.tweetDataService.likeTweet(this.tweet$.id).subscribe((returnTweet) => {
        this.tweet$ = returnTweet;
      }
      );
    }
  }

  likedByLoggedInUser() {
    let returnValue = false;
    this.tweet$.liked.forEach((item, index) => {
      if (item.id === this.userService.getLoggedInID() ) {
        returnValue = true;
      }
    });
    this.likedByLoggedIn = returnValue;
    return returnValue;
  }
}
