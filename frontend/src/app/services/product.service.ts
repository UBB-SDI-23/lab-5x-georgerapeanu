import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductScoreDTO } from '../dto/PoductScoreDTO';
import { environment } from 'src/environments/environment';
import { GenericPageDTO } from '../dto/GenericPageDTO';
import { ProductDTO } from '../dto/ProductDTO';
import { ReviewDTO } from '../dto/ReviewDTO';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProductScoreStatistic(pageNumber: number, pageSize: number): Observable<GenericPageDTO<ProductScoreDTO>> {
    return this.http.get<GenericPageDTO<ProductScoreDTO>>(environment.apiURL + "/products/sorted-by-reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllProducts(pageNumber: number, pageSize: number): Observable<GenericPageDTO<ProductDTO>> {
    return this.http.get<GenericPageDTO<ProductDTO>>(environment.apiURL + "/products" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getAllReviewsForProduct(productId: number, pageNumber: number, pageSize: number): Observable<GenericPageDTO<ReviewDTO>> {
    return this.http.get<GenericPageDTO<ReviewDTO>>(environment.apiURL + "/products" + `/${productId}` + "/reviews" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getProductById(id: number): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(environment.apiURL + "/products/" + id.toString());
  }

  editProduct(product: ProductDTO): Observable<any>{
    return this.http.patch(environment.apiURL + "/products/" + product.id.toString(), product);
  }

  createProduct(product: ProductDTO): Observable<any>{
    return this.http.post(environment.apiURL + "/products", product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/products/" + id.toString());
  }
}
