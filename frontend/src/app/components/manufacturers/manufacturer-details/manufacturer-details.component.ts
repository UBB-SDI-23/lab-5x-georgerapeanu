import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Manufacturer } from 'src/app/model/Manufacturer';
import { Product } from 'src/app/model/Product';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-manufacturer-details',
  templateUrl: './manufacturer-details.component.html',
  styleUrls: ['./manufacturer-details.component.css']
})
export class ManufacturerDetailsComponent {

  manufacturer: Manufacturer | null = null;
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  products: Product[] = [];

  constructor(
    private route: ActivatedRoute, 
    private manufacturerService: ManufacturerService, 
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let manufacturerIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(manufacturerIdString == null) {
      return;
    }
    let manufacturerId = parseInt(manufacturerIdString);

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
        this.manufacturerService.getAllProductsForManufacturer(manufacturerId, this.pageNumber, this.pageSize).subscribe(result => {
          this.products = result.content;
          this.totalPages = result.totalPages;
          this.currentPage = this.pageNumber;
          this.currentSize = this.pageSize;
        });
      }
    );

    this.manufacturerService.getManufacturerById(manufacturerId).subscribe(result => {
      this.manufacturer = result;
    });
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

  setPageSize(pageSize: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': pageSize, 'pageNumber': this.pageNumber}
      }
    )
  }

  goBack(): void {
    this.location.back();
  }
}
