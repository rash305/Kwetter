import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {TweetsComponent} from './tweets/tweets.component';
import {AuthGuard} from './auth/auth-guard.service';
import {TimelineComponent} from './timeline/timeline.component';
import {AccountProfileComponent} from './account-profile/account-profile.component';
import {RegisterAccountComponent} from './register-account/register-account.component';
import {UpdateAccountComponent} from './update-account/update-account.component';


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: TimelineComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'account',
    component: AccountProfileComponent
  },  {
    path: 'account/edit',
    component: UpdateAccountComponent
  },
  {
    path: 'account/:username',
    component: AccountProfileComponent
  },   {
    path: 'tweets',
    component: TweetsComponent,
    canActivate: [AuthGuard]
  },  {
    path: 'register',
    component: RegisterAccountComponent
  },
  {
    path: '**',
    redirectTo: '/home'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule {
}
