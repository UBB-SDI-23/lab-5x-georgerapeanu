import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { User } from 'src/app/model/User';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users-overview',
  templateUrl: './users-overview.component.html',
  styleUrls: ['./users-overview.component.css']
})
export class UsersOverviewComponent {
  pageSize: number = 10;
  pageNumber: number = 0;
  totalPages: number = 0;
  currentPage: number = this.pageNumber;
  currentSize: number = this.pageSize;
  users: User[] = [];
  constructor(
    private userService: UserService,
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
          this.userService.getAllUsers(this.pageNumber, this.pageSize).subscribe(result => {
            this.users = result.content;
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
