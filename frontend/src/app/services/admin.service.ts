import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(
    private http: HttpClient, 
  ) { 

  }

  dropDatabase() : Observable<any> {
    return this.http.post(environment.apiURL + "/admin/drop-all", null);
  }

  recreateDatabase() : Observable<any> {
    return this.http.post(environment.apiURL + "/admin/recreate", null);
  }
}
