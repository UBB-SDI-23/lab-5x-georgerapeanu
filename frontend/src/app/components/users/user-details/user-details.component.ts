import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { UserProfile } from 'src/app/model/UserProfile';
import { Location } from '@angular/common';
import { Review } from 'src/app/model/Review';
import { UserCreatedCountDTO } from 'src/app/dto/UserCreatedCountDTO';
import { AbstractPageContainerComponent } from '../../abstract/abstract-page-container/abstract-page-container.component';
import { UserPreferencesService } from 'src/app/services/user-preferences.service';
import { LoginService } from 'src/app/services/login.service';
import { Role } from 'src/app/model/Role';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent extends AbstractPageContainerComponent{
  user: UserProfile | null = null;
  userCreatedCount: UserCreatedCountDTO | null = null;
  reviews: Review[] = [];
  userHandle: string = "";
  pageSizePreference = 10;
  role: Role | null = null;

  constructor(
    private route: ActivatedRoute, 
    private userService: UserService, 
    private location: Location,
    router: Router,
    activatedRoute: ActivatedRoute,
    private userPreferencesService: UserPreferencesService,
    private loginService: LoginService
  ) {
    super(router, activatedRoute, userPreferencesService);
  }

  override ngOnInit(): void {
    let userHandleString: string | null = this.route.snapshot.paramMap.get('id');
    if(userHandleString == null) {
      return;
    }
    this.userHandle = userHandleString;
    this.userService.getUserCreatedCount(this.userHandle).subscribe(result => {
      this.userCreatedCount = result;
    });
    this.userService.getUserById(this.userHandle).subscribe(result => {
      this.user = result;
    });
    this.userService.getUserRole(this.userHandle).subscribe(result => {
      this.role = result;
    })
    super.ngOnInit();
  }

  override pageUpdate(): void {
    this.userService.getAllReviewsForUser(this.userHandle, this.pageNumber, this.pageSize).subscribe(result => {
      this.reviews = result.content;
      this.totalPages = result.totalPages;
      this.currentPage = this.pageNumber;
      this.currentSize = this.pageSize;
    });
  }

  goBack(): void {
    this.location.back();
  }

  setPageSizePreference(size: number) : void {
    if(this.loginService.getAuthToken() != null) {
      this.userPreferencesService.setPageSizePreference(size);
    } else {
      alert("you are not logged in");
    }
  }
}
