import { Component, Input } from '@angular/core';
import { User } from 'src/app/model/User';
import { AbstractOverviewPageComponent } from '../../abstract/abstract-overview-page/abstract-overview-page.component';

@Component({
  selector: 'app-users-overview-page',
  templateUrl: './users-overview-page.component.html',
  styleUrls: ['./users-overview-page.component.css']
})
export class UsersOverviewPageComponent extends AbstractOverviewPageComponent<User>{
 
}
