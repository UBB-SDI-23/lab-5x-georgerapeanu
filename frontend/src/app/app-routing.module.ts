import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UsersOverviewComponent } from './components/users/users-overview/users-overview.component';
import { UserDetailsComponent } from './components/users/user-details/user-details.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path:"users",
    component: UsersOverviewComponent
  },
  {
    path:"users/:id",
    component: UserDetailsComponent
  },
  {
    path:"users/:id/edit",
    component: UserEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
