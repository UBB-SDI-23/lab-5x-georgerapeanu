import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ManufacturerDTO } from 'src/app/dto/ManufacturerDTO';
import { ManufacturerService } from 'src/app/services/manufacturer.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-manufacturer-details',
  templateUrl: './manufacturer-details.component.html',
  styleUrls: ['./manufacturer-details.component.css']
})
export class ManufacturerDetailsComponent {

  manufacturer: ManufacturerDTO | null = null;

  constructor(
    private route: ActivatedRoute, 
    private manufacturerService: ManufacturerService, 
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    let userIdString: string | null = this.route.snapshot.paramMap.get('id');
    if(userIdString == null) {
      return;
    }
    this.manufacturerService.getManufacturerById(parseInt(userIdString)).subscribe(result => {
      this.manufacturer = result;
    });
  }

  goBack(): void {
    this.location.back();
  }
}
