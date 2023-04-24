import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent {
  editForm = this.formBuilder.group(
    {
      id: [0],
      name: ['', Validators.required],
      handle: ['', Validators.required],
      email: ['', Validators.required],
      birthday: [new Date(), Validators.required],
      registeredAt: [new Date(), Validators.required]
    }
  );

  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(userIdString == null) {
      return;
    }
    this.userService.getUserById(parseInt(userIdString)).subscribe(result => {
      this.editForm.setValue(result);
    });
  }

  onSubmit(): void {
    if(this.editForm.valid) {
      this.userService.editUser(this.editForm.value as User).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse="Error";
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  goToUsers(): void {
    this.router.navigate(["/users"]);
  }

}
