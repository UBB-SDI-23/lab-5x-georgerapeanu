import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDTO } from '../dto/UserDTO';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { GenericPageDTO } from '../dto/GenericPageDTO';
import { ReviewDTO } from '../dto/ReviewDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(pageNumber: number, pageSize: number): Observable<GenericPageDTO<UserDTO>> {
    return this.http.get<GenericPageDTO<UserDTO>>(environment.apiURL + "/users" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllReviewsForUser(userId: number, pageNumber: number, pageSize: number): Observable<GenericPageDTO<ReviewDTO>> {
    return this.http.get<GenericPageDTO<ReviewDTO>>(environment.apiURL + "/users" + `/${userId}` + "/reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
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
    return this.http.delete(environment.apiURL + "/users/" + id.toString());
  }
}
