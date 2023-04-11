import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductScoreDTO } from '../dto/PoductScoreDTO';
import { environment } from 'src/environments/environment';
import { GenericPageDTO } from '../dto/GenericPageDTO';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProductScoreStatistic(pageNumber: number, pageSize: number): Observable<GenericPageDTO<ProductScoreDTO>> {
    return this.http.get<GenericPageDTO<ProductScoreDTO>>(environment.apiURL + "/products/sorted-by-reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }
}
