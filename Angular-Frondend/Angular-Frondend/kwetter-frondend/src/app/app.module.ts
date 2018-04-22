import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { RouterModule, Routes } from '@angular/router';
import {AppRoutingModule} from './app-routing.module';
import {Http, HttpModule} from '@angular/http';
import {AuthConfig, AuthHttp} from 'angular2-jwt/angular2-jwt';
import {TOKEN_NAME} from './services/auth.constant';
import { FormsModule } from '@angular/forms';



import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { TweetsComponent } from './tweets/tweets.component';
import {AuthGuard} from './guards/auth-guard.service';
import {AuthenticationService} from './services/authentication.service';
import {UserService} from './services/user.service';


export function authHttpServiceFactory(http: Http) {
  return new AuthHttp(new AuthConfig({
    headerPrefix: 'Bearer',
    tokenName: TOKEN_NAME,
    globalHeaders: [{'Content-Type': 'application/json'}],
    noJwtError: false,
    noTokenScheme: true,
    tokenGetter: (() => localStorage.getItem(TOKEN_NAME))
  }), http);
}

@NgModule({
  declarations: [AppComponent, LoginComponent, HomeComponent, TweetsComponent,
  ],
  imports: [BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule],
  providers: [
    {provide: AuthHttp, useFactory: authHttpServiceFactory, deps: [Http]},

    AuthenticationService,
    UserService,
    AuthGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
