import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductScoreDTO } from '../models/PoductScoreDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProductScoreStatistic(): Observable<ProductScoreDTO[]> {
    return this.http.get<ProductScoreDTO[]>(environment.apiURL + "/products/sorted-by-reviews");
  }
}
