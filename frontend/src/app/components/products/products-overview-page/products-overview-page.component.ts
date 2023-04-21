import { Component, Input } from '@angular/core';
import { ProductDTO } from 'src/app/dto/ProductDTO';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-products-overview-page',
  templateUrl: './products-overview-page.component.html',
  styleUrls: ['./products-overview-page.component.css']
})
export class ProductsOverviewPageComponent extends AbstractOverviewPageComponent<ProductDTO> {
  
}
