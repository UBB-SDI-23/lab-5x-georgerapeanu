import { Component, Input } from '@angular/core';
import { ReviewDTO } from 'src/app/dto/ReviewDTO';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-reviews-overview-page',
  templateUrl: './reviews-overview-page.component.html',
  styleUrls: ['./reviews-overview-page.component.css']
})
export class ReviewsOverviewPageComponent extends AbstractOverviewPageComponent<ReviewDTO>{

}
