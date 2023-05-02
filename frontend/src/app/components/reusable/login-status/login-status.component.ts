import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent {
  token: string|null = null;

  constructor(private cookieService: CookieService) {

  }

  ngOnInit(): void {
    this.token = this.cookieService.get("auth-token") || null;
    if(this.token != null) {
      const {exp} = jwt_decode(this.token) as {exp: number};
      if(Date.now() >= exp) {
        this.cookieService.remove("auth-token");
        this.token = null;
      }
    }
  }

  getUserHandleFromToken(): string {
    if(this.token == null) {
      return "";
    }
    const {user_handle} = jwt_decode(this.token) as {user_handle: string};
    return user_handle;
  }
}
