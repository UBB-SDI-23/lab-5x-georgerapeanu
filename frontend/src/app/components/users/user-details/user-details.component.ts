import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { User } from 'src/app/model/User';
import { Location } from '@angular/common';
import { Review } from 'src/app/model/Review';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {
  user: User | null = null;
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  reviews: Review[] = [];


  constructor(
    private route: ActivatedRoute, 
    private userService: UserService, 
    private location: Location,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(userIdString == null) {
      return;
    }
    let userId = parseInt(userIdString);
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
        this.userService.getAllReviewsForUser(userId, this.pageNumber, this.pageSize).subscribe(result => {
          this.reviews = result.content;
          this.totalPages = result.totalPages;
          this.currentPage = this.pageNumber;
          this.currentSize = this.pageSize;
        });
      }
    );
    this.userService.getUserById(userId).subscribe(result => {
      this.user = result;
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
