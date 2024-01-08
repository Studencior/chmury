import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = this.getAuthToken(); // Implement this method to retrieve your auth token

    if (authToken) {
      // Clone the request and add the authorization header.
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${authToken}` },
      });
      return next.handle(authReq);
    }

    return next.handle(req);
  }

  private getAuthToken(): string | null {
    // Retrieve the JWT token from where it's stored (e.g., localStorage)
    return localStorage.getItem('jwtToken');
  }
}
