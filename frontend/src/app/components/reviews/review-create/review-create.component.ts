import { Component } from '@angular/core';
import { ReviewDTO } from 'src/app/dto/ReviewDTO';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-review-create',
  templateUrl: './review-create.component.html',
  styleUrls: ['./review-create.component.css']
})
export class ReviewCreateComponent {
  review: ReviewDTO = {
    userId: 0,
    productId: 0,
    score: 0,
    comment: "",
    postedDate: new Date()
  };
  createForm = this.formBuilder.group(
    {
      userId: [0, Validators.required],
      productId: [0, Validators.required],
      score: [0, Validators.required],
      comment: ['', Validators.required],
      postedDate: ['', Validators.required],
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.queryParamMap.get('user_id');
    if(userIdString != null) {
      this.review.userId = parseInt(userIdString);
    }
    let productIdString: string | null = this.route.snapshot.queryParamMap.get('product_id');
    if(productIdString != null) {
      this.review.productId = parseInt(productIdString);
    }
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.reviewService.createReview(this.review).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse= error.error.error;
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}
