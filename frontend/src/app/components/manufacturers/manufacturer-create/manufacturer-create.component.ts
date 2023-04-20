import { Component } from '@angular/core';
import { ManufacturerDTO } from 'src/app/dto/ManufacturerDTO';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-manufacturer-create',
  templateUrl: './manufacturer-create.component.html',
  styleUrls: ['./manufacturer-create.component.css']
})
export class ManufacturerCreateComponent {
  manufacturer: ManufacturerDTO = {
    id: 0,
    name: "",
    description: "",
    registerDate: new Date(),
  };
  createForm = this.formBuilder.group(
    {
      name: ['', Validators.required],
      description: ['', Validators.required],
      registerDate: ['', Validators.required]
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
    this.manufacturerService.getManufacturerById(parseInt(manufacturerIdString)).subscribe(result => {
      this.manufacturer = result;
    });
  }

  onSubmit(): void {
    if(this.createForm.valid) {
      this.manufacturerService.createManufacturer(this.manufacturer).subscribe({
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
