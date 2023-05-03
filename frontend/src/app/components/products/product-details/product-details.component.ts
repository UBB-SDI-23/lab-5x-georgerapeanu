import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/model/Product';
import { ProductService } from 'src/app/services/product.service';
import { Location } from '@angular/common';
import { Review } from 'src/app/model/Review';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent extends AbstractPageContainerComponent {
  product: Product | null = null;
  reviews: Review[] = [];
  productId: number = 0;

  constructor(
    private route: ActivatedRoute, 
    private productService: ProductService, 
    private location: Location,
    router: Router,
    activatedRoute: ActivatedRoute
  ) {
    super(router, activatedRoute);
  }

  override ngOnInit(): void {
    let productIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(productIdString == null) {
      return;
    }
    this.productId = parseInt(productIdString);

    this.productService.getProductById(this.productId).subscribe(result => {
      this.product = result;
    });

    super.ngOnInit();
  }

  pageUpdate() {
    this.productService.getAllReviewsForProduct(this.productId, this.pageNumber, this.pageSize).subscribe(result => {
      this.reviews = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
