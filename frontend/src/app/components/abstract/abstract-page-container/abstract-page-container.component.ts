import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';

@Component({
  selector: 'app-abstract-page-container',
  templateUrl: './abstract-page-container.component.html',
  styleUrls: ['./abstract-page-container.component.css']
})
export class AbstractPageContainerComponent implements OnInit {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;

  constructor(
    protected router:Router,
    protected activatedRoute: ActivatedRoute,
    protected userPreferenceService: UserPreferencesService
  ){
    
  }

  ngOnInit(): void {
    let preference = this.userPreferenceService.getPageSizePreferences();
    if(preference != null) {
      this.pageSize = preference;
    }
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
          this.pageUpdate();
        }
      );
  }

  public pageUpdate(): void {
    ;
  }

  public setPageSize(pageSize: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': pageSize, 'pageNumber': this.pageNumber}
      }
    );
  }

  public setPageNumber(pageNumber: number): void {
    this.router.navigate(
      [],
      {
        relativeTo: this.activatedRoute,
        queryParams: {'pageSize': this.pageSize, 'pageNumber': pageNumber}
      }
    )
  }
}
