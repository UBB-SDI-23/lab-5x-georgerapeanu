import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { UsersOverviewComponent } from './components/users/users-overview/users-overview.component';
import {HttpClientModule} from '@angular/common/http';
import { UserDetailsComponent } from './components/users/user-details/user-details.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserCreateComponent } from './components/users/user-create/user-create.component';
import { ProductsScoreStatisticComponent } from './components/products/products-score-statistic/products-score-statistic.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UsersOverviewComponent,
    UserDetailsComponent,
    UserEditComponent,
    UserCreateComponent,
    ProductsScoreStatisticComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
