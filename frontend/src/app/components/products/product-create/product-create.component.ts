import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ProductCreate } from 'src/app/model/ProductCreate';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent {
  product: ProductCreate = {
    name: "",
    description: "",
    publishDate: new Date(),
    price: 0,
    weight: 0,
    manufacturerId: 0,
    color: ""
  };
  createForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      description: ['', Validators.required],
      publishDate: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      weight: ['', [Validators.required, Validators.min(0)]],
      manufacturerId: ['', Validators.required],
      color: ['', Validators.required]
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let productIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(productIdString == null) {
      return;
    }
    this.productService.getProductById(parseInt(productIdString)).subscribe(result => {
      this.product = result;
    });
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.productService.createProduct(this.product).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse= error.error.error;
        }
      });
    }
  }

  goToProducts(): void {
    this.router.navigate(["/products"]);
  }

  goBack(): void {
    this.location.back();
  }
}
