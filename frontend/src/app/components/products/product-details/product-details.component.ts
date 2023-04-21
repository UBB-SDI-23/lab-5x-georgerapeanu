import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDTO } from 'src/app/dto/ProductDTO';
import { ProductService } from 'src/app/services/product.service';
import { Location } from '@angular/common';
import { ReviewDTO } from 'src/app/dto/ReviewDTO';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  product: ProductDTO | null = null;
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  reviews: ReviewDTO[] = [];

  constructor(
    private route: ActivatedRoute, 
    private productService: ProductService, 
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let productIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(productIdString == null) {
      return;
    }
    let productId = parseInt(productIdString);

    this.activatedRoute.queryParams
    .subscribe(
      params => {
        if('pageSize' in params) {
          this.pageSize = parseInt(params['pageSize']);
        }
        if('pageNumber' in params) {
          this.pageNumber = parseInt(params['pageNumber']);
        }
        if(this.pageSize < 4) {
          this.pageSize = 4;
        }
        if(this.pageSize > 10) {
          this.pageSize = 10;
        }
        this.productService.getAllReviewsForProduct(productId, this.pageNumber, this.pageSize).subscribe(result => {
          this.reviews = result.content;
          this.totalPages = result.totalPages;
          this.currentPage = this.pageNumber;
          this.currentSize = this.pageSize;
        });
      }
    );

    this.productService.getProductById(productId).subscribe(result => {
      this.product = result;
    });
  }

  setPageNumber(pageNumber: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': pageNumber}
      }
    )
  }

  goBack(): void {
    this.location.back();
  }
}
