import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { ProductDTO } from 'src/app/dto/ProductDTO';
import { ActivatedRoute, Router } from '@angular/router';

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
  products: ProductDTO[] = [];
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
          if(this.pageSize < 4) {
            this.pageSize = 4;
          }
          if(this.pageSize > 10) {
            this.pageSize = 10;
          }
          this.productService.getAllProducts(this.pageNumber, this.pageSize).subscribe(result => {
            this.products = result.content;
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
        queryParams: {'pageSize': this.pageSize, 'pageNumber': pageNumber}
      }
    )
  }
}
