import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/model/Product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductScoreDTO } from 'src/app/dto/ProductScoreDTO';

@Component({
  selector: 'app-products-overview',
  templateUrl: './products-overview.component.html',
  styleUrls: ['./products-overview.component.css']
})
export class ProductsOverviewComponent {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  minWeight: number = -1;
  productScores: ProductScoreDTO[] = [];
  constructor(
    private productService: ProductService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .subscribe(
        params => {
          if('pageSize' in params) {
            this.pageSize = parseInt(params['pageSize']);
          }
          if('pageNumber' in params) {
            this.pageNumber = parseInt(params['pageNumber']);
          }
          if('weight' in params) {
            this.minWeight = parseInt(params['weight']);
          }
          if(this.pageSize < 4) {
            this.pageSize = 4;
          }
          if(this.pageSize > 10) {
            this.pageSize = 10;
          }
          this.productService.getProductScorePageWithWeightFilter(this.pageNumber, this.pageSize, this.minWeight).subscribe(result => {
            this.productScores = result.content;
            this.totalPages = result.totalPages;
            this.currentPage = this.pageNumber;
            this.currentSize = this.pageSize;
          });
        }
      );
  }

  setPageNumber(pageNumber: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': pageNumber, 'weight': this.minWeight}
      }
    )
  }
  setWeight(minWeight: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': this.pageNumber, 'weight': minWeight}
      }
    )
  }
}
