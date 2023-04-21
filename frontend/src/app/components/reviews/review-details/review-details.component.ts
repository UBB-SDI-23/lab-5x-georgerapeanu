import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { Location } from '@angular/common';
import { ReviewDTO } from 'src/app/dto/ReviewDTO';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-review-details',
  templateUrl: './review-details.component.html',
  styleUrls: ['./review-details.component.css']
})
export class ReviewDetailsComponent {
  review: ReviewDTO | null = null;

  constructor(
    private route: ActivatedRoute, 
    private reviewService: ReviewService, 
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.queryParamMap.get('user_id');
    if(userIdString == null) {
      return;
    }
    let userId = parseInt(userIdString);
    let productIdString: string | null = this.route.snapshot.queryParamMap.get('product_id');
    if(productIdString == null) {
      return;
    }
    let productId = parseInt(productIdString);
    this.reviewService.getReviewById(userId, productId).subscribe(result => {
      this.review = result;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
