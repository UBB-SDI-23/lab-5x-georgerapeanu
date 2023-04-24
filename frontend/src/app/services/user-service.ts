import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/User';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GenericPage } from '../model/GenericPage';
import { Review } from '../model/Review';
import { UserCreate } from '../model/UserCreate';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(pageNumber: number, pageSize: number): Observable<GenericPage<User>> {
    return this.http.get<GenericPage<User>>(environment.apiURL + "/users" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllReviewsForUser(userId: number, pageNumber: number, pageSize: number): Observable<GenericPage<Review>> {
    return this.http.get<GenericPage<Review>>(environment.apiURL + "/users" + `/${userId}` + "/reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(environment.apiURL + "/users/" + id.toString());
  }

  editUser(user: User): Observable<any>{
    return this.http.patch(environment.apiURL + "/users/" + user.id.toString(), user);
  }

  createUser(user: UserCreate): Observable<any>{
    return this.http.post(environment.apiURL + "/users", user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/users/" + id.toString());
  }
}
