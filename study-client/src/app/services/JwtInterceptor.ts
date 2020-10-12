import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

import 'rxjs/add/operator/do';
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(private _router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available

        if (sessionStorage.getItem('username') && sessionStorage.getItem('token')) {
            request = request.clone({
              setHeaders: {
                Authorization: sessionStorage.getItem('token')
              }
            })
          }
      
          return next.handle(request);
    }

    errorHandler(error: Response) {
        console.log(error);
        return Observable.throw(error || 'SERVER ERROR');
    }
}

