import { Component } from '@angular/core';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { Manufacturer } from 'src/app/model/Manufacturer';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users-overview',
  templateUrl: './manufacturers-overview.component.html',
  styleUrls: ['./manufacturers-overview.component.css']
})
export class ManufacturersOverviewComponent {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  manufacturers: Manufacturer[] = [];
  constructor(
    private manufacturerService: ManufacturerService,
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
          this.manufacturerService.getAllManufacturers(this.pageNumber, this.pageSize).subscribe(result => {
            this.manufacturers = result.content;
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
