import { Injectable } from '@angular/core';
import { catchError, filter, map, of } from 'rxjs';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpStatusCode,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class UnauthorizedRedirectionInterceptor implements HttpInterceptor {

  constructor(
    private router: Router
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log("entering");
    return next.handle(request).pipe(
      catchError(err => {
        console.log(err);
        if(err instanceof HttpErrorResponse) {
          if(err.status == HttpStatusCode.Unauthorized) {
            console.log(err);
            this.router.navigate(["/unauthorized"]);
          }
        }
        return of(err);
      })
    );
  }
}
