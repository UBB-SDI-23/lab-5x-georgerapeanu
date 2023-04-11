import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { UserDTO } from 'src/app/dto/UserDTO';
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
  desiredPage: number = 0;
  users: UserDTO[] = [];
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
          this.desiredPage = this.pageNumber;
          this.userService.getAllUsers(this.pageNumber, this.pageSize).subscribe(result => {
            this.users = result.content;
            this.totalPages = result.totalPages;
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
