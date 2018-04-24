import {ChangeDetectorRef, Component} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private router: Router, private userService: UserService, private cdRef: ChangeDetectorRef) {
  }

  ngAfterViewChecked() {
    // Avoid the error: ExpressionChangedAfterItHasBeenCheckedError: Expression has changed after it was checked
    this.cdRef.detectChanges();
  }
  logout() {

    this.userService.logout();
    this.router.navigate(['/']);
    window.location.reload();

  }

}
