import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { UserDTO } from 'src/app/models/UserDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-overview',
  templateUrl: './users-overview.component.html',
  styleUrls: ['./users-overview.component.css']
})
export class UsersOverviewComponent {
  users: UserDTO[] = [];
  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(result => {
      this.users = result;
    });
  }

  deleteUser(id: number | undefined): void {
    if(id == undefined){
      return;
    }
    this.userService.deleteUser(id).subscribe({
      next: (response) => {
        this.userService.getAllUsers().subscribe(result => {
          this.users = result;
        });
      },
      error: (error) => {
        this.userService.getAllUsers().subscribe(result => {
          this.users = result;
        });
      }
    });
  }
}
