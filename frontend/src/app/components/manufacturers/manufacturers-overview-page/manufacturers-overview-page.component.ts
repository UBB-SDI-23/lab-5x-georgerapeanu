import { Component, Input } from '@angular/core';
import { ManufacturerDTO } from 'src/app/dto/ManufacturerDTO';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-manufacturers-overview-page',
  templateUrl: './manufacturers-overview-page.component.html',
  styleUrls: ['./manufacturers-overview-page.component.css']
})
export class ManufacturersOverviewPageComponent extends AbstractOverviewPageComponent<ManufacturerDTO> {
  
}
