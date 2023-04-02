import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { UserDTO } from 'src/app/models/UserDTO';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {
  user: UserDTO | null = null;

  constructor(private route: ActivatedRoute, private userService: UserService, private location: Location) {}

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
}
