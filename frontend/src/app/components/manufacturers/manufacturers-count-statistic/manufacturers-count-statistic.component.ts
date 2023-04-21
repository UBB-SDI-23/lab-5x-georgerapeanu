import { Component } from '@angular/core';
import { ManufacturerProductCountDTO } from 'src/app/dto/ManufacturerProductCountDTO';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manufacturers-count-statistic',
  templateUrl: './manufacturers-count-statistic.component.html',
  styleUrls: ['./manufacturers-count-statistic.component.css']
})
export class ManufacturersCountStatisticComponent {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  desiredPage: number = 0;
  manufacturerCounts: ManufacturerProductCountDTO[] = [];
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  constructor(
    private manufacturerService: ManufacturerService,
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
          this.manufacturerService.getManufacturerCountStatistic(this.pageNumber, this.pageSize).subscribe((result) => {
            this.manufacturerCounts = result.content;
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
