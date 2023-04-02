import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { UserDTO } from 'src/app/models/UserDTO';

@Component({
  selector: 'app-users-overview',
  templateUrl: './users-overview.component.html',
  styleUrls: ['./users-overview.component.css']
})
export class UsersOverviewComponent {
  users: UserDTO[] = [];
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(result => {
      this.users = result;
    })
  }
}
