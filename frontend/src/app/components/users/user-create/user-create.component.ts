import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { UserCreate } from 'src/app/model/UserCreate';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent {
  createForm = this.formBuilder.group(
    {
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
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.userService.createUser(this.createForm.value as UserCreate).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse="Error";
        }
      });
    }
  }

  goToUsers(): void {
    this.router.navigate(["/users"]);
  }

  goBack(): void {
    this.location.back();
  }
}
