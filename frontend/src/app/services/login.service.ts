import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenResponseDTO } from '../dto/TokenResponseDTO';
import { environment } from 'src/environments/environment';
import { User } from '../model/User';
import { RegisterConfirmResponseDTO } from '../dto/RegisterConfirmResponseDTO';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  register(user: User): Observable<TokenResponseDTO> {
    return this.http.post<TokenResponseDTO>(environment.apiURL + "/register", user);
  }

  confirm(token: string): Observable<RegisterConfirmResponseDTO> {
    return this.http.post<RegisterConfirmResponseDTO>(environment.apiURL + "/register/" + token, null);
  }

  login(user: User): Observable<TokenResponseDTO> {
    return this.http.post<TokenResponseDTO>(environment.apiURL + "/login", user);
  }
}
