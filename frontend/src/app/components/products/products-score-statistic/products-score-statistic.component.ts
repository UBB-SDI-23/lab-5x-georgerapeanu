import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductScoreDTO } from 'src/app/dto/PoductScoreDTO';
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
  productScores: ProductScoreDTO[] = [];
  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
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
          this.productService.getProductScoreStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
            this.productScores = result.content;
            this.totalPages = result.totalPages;
          });
        }
      );
  }

  sortByScore(): void {
    this.productScores.reverse();
  }

  setPageNumber(pageNumber: number): void {
    pageNumber = Math.max(pageNumber, 0);
    pageNumber = Math.min(pageNumber, this.totalPages - 1);
    this.pageNumber = pageNumber;
  }
}
