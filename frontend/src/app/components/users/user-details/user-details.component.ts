import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { UserDTO } from 'src/app/dto/UserDTO';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {
  user: UserDTO | null = null;

  constructor(
    private route: ActivatedRoute, 
    private userService: UserService, 
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(userIdString == null) {
      return;
    }
    this.userService.getUserById(parseInt(userIdString)).subscribe(result => {
      this.user = result;
    });
  }

  goBack(): void {
    this.location.back();
  }

  deleteUser(id: number | undefined): void {
    if(id == undefined){
      return;
    }
    this.userService.deleteUser(id).subscribe({
      next: (response) => {
        this.router.navigate(["/users"]);
      },
      error: (error) => {
        this.router.navigate(["/users"]);
      }
    });
  }
}
