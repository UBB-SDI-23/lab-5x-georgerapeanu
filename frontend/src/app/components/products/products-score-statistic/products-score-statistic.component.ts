import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductScoreDTO } from 'src/app/dto/ProductScoreDTO';
import { ProductService } from 'src/app/services/product.service';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';

@Component({
  selector: 'app-products-score-statistic',
  templateUrl: './products-score-statistic.component.html',
  styleUrls: ['./products-score-statistic.component.css']
})
export class ProductsScoreStatisticComponent extends AbstractPageContainerComponent{
  
  productScores: ProductScoreDTO[] = [];
  
  constructor(
    private productService: ProductService,
    activatedRoute: ActivatedRoute,
    router: Router,
    userPreferencesService: UserPreferencesService
  ) {
    super(router, activatedRoute, userPreferencesService);
  }

  override ngOnInit(): void {
    super.ngOnInit();
  }

  override pageUpdate(): void {
    this.productService.getProductScoreStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
      this.productScores = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }
}
