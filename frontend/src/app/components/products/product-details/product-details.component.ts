import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDTO } from 'src/app/dto/ProductDTO';
import { ProductService } from 'src/app/services/product.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  product: ProductDTO | null = null;

  constructor(
    private route: ActivatedRoute, 
    private productService: ProductService, 
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(userIdString == null) {
      return;
    }
    this.productService.getProductById(parseInt(userIdString)).subscribe(result => {
      this.product = result;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
