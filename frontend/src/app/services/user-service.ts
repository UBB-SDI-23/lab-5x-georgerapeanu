import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDTO } from '../models/UserDTO';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(environment.apiURL + "/users");
  }

  getUserById(id: number): Observable<UserDTO> {
    return this.http.get<UserDTO>(environment.apiURL + "/users/" + id.toString());
  }

  editUser(user: UserDTO): Observable<any>{
    return this.http.patch(environment.apiURL + "/users/" + user.id.toString(), user);
  }

  createUser(user: UserDTO): Observable<any>{
    return this.http.post(environment.apiURL + "/users", user);
  }

  deleteUser(id: number): Observable<any> {
    console.log(environment.apiURL + "/users/" + id.toString());
    return this.http.delete(environment.apiURL + "/users/" + id.toString());
  }
}
