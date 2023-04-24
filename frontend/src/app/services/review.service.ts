import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review } from '../model/Review';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { ReviewCreate } from '../model/ReviewCreate';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }
  
  getReviewById(userId: number, productId: number): Observable<Review> {
    return this.http.get<Review>(environment.apiURL + "/reviews", {
      params: {
        user_id: userId,
        product_id: productId
      },
    });
  }

  createReview(review: ReviewCreate): Observable<any>{
    return this.http.post(environment.apiURL + "/reviews", review);
  }

  editReview(review: Review): Observable<any>{
    return this.http.patch(environment.apiURL + "/reviews", review, {
      params: {
        user_id: review.userId,
        product_id: review.productId
      },
    });
  }

  deleteReview(userId: number, productId: number): Observable<any> {
    return this.http.delete(environment.apiURL + "/reviews", {
      params: {
        user_id: userId,
        product_id: productId
      },
    });
  }
}
