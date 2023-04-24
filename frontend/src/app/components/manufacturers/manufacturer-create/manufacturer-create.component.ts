import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { Location } from '@angular/common';
import { ManufacturerCreate } from 'src/app/model/ManufacturerCreate';

@Component({
  selector: 'app-manufacturer-create',
  templateUrl: './manufacturer-create.component.html',
  styleUrls: ['./manufacturer-create.component.css']
})
export class ManufacturerCreateComponent {
  createForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      description: ['', Validators.required],
      registerDate: [new Date(), Validators.required]
    }
  );
  serverResponse: string|null = null;

  constructor(
    private route: ActivatedRoute,
    private manufacturerService: ManufacturerService, 
    private formBuilder: FormBuilder,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let manufacturerIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(manufacturerIdString == null) {
      return;
    }
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.manufacturerService.createManufacturer(this.createForm.value as ManufacturerCreate).subscribe({
        next: response => {
          this.serverResponse="Ok";
        },
        error: error => {
          this.serverResponse="Error";
        }
      });
    }
  }

  goToManufacturers(): void {
    this.router.navigate(["/manufacturers"]);
  }

  goBack(): void {
    this.location.back();
  }
}
