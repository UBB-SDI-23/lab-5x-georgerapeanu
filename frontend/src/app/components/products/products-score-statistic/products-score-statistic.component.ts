import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductScoreDTO } from 'src/app/dto/ProductScoreDTO';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-products-score-statistic',
  templateUrl: './products-score-statistic.component.html',
  styleUrls: ['./products-score-statistic.component.css']
})
export class ProductsScoreStatisticComponent {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  desiredPage: number = 0;
  productScores: ProductScoreDTO[] = [];
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {

  }

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
          if(this.pageSize < 4) {
            this.pageSize = 4;
          }
          if(this.pageSize > 10) {
            this.pageSize = 10;
          }
          this.desiredPage = this.pageNumber;
          this.productService.getProductScoreStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
            this.productScores = result.content;
            this.totalPages = result.totalPages;
            this.currentPage = this.pageNumber;
            this.currentSize = this.pageSize;
          });
        }
      );
  }

  setPageNumber(pageNumber: number): void {
    pageNumber = Math.max(pageNumber, 0);
    pageNumber = Math.min(pageNumber, this.totalPages - 1);
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': pageNumber}
      }
    )
  }
}
