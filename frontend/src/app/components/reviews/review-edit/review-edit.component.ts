import { Component } from '@angular/core';
import { Review } from 'src/app/model/Review';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-review-edit',
  templateUrl: './review-edit.component.html',
  styleUrls: ['./review-edit.component.css']
})
export class ReviewEditComponent {
  editForm = this.formBuilder.group(
    {
      userId: [0],
      productId: [0],
      score: [0, Validators.required],
      comment: ['', Validators.required],
      postedDate: [new Date(), Validators.required],
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
    if(userIdString == null) {
      return ;
    }
    this.editForm.controls.userId.setValue(parseInt(userIdString));
    let productIdString: string | null = this.route.snapshot.queryParamMap.get('product_id');
    if(productIdString == null) {
      return ;
    }
    this.editForm.controls.productId.setValue(parseInt(productIdString));
    this.reviewService.getReviewById(parseInt(userIdString), parseInt(productIdString)).subscribe(result => {
      this.editForm.setValue(result);
    });
  }

  onSubmit(): void {
    if(this.editForm.valid) {
      this.reviewService.editReview(this.editForm.value as Review).subscribe({
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
