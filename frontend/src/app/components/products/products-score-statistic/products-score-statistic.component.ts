import { Component } from '@angular/core';
import { ProductScoreDTO } from 'src/app/models/PoductScoreDTO';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-products-score-statistic',
  templateUrl: './products-score-statistic.component.html',
  styleUrls: ['./products-score-statistic.component.css']
})
export class ProductsScoreStatisticComponent {
  productScores: ProductScoreDTO[] = [];
  constructor(private productService: ProductService) {

  }

  ngOnInit(): void {
    this.productService.getProductScoreStatistic().subscribe((result) => {
      this.productScores = result;
    });
  }
}
