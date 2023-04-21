import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { UsersOverviewComponent } from './components/users/users-overview/users-overview.component';
import { UserDetailsComponent } from './components/users/user-details/user-details.component';
import { UserEditComponent } from './components/users/user-edit/user-edit.component';
import { UserCreateComponent } from './components/users/user-create/user-create.component';
import { ProductsScoreStatisticComponent } from './components/products/products-score-statistic/products-score-statistic.component';
import { UserDeleteComponent } from './components/users/user-delete/user-delete.component';
import { ManufacturersOverviewComponent } from './components/manufacturers/manufacturers-overview/manufacturers-overview.component';
import { ManufacturerCreateComponent } from './components/manufacturers/manufacturer-create/manufacturer-create.component';
import { ManufacturerDetailsComponent } from './components/manufacturers/manufacturer-details/manufacturer-details.component';
import { ManufacturerEditComponent } from './components/manufacturers/manufacturer-edit/manufacturer-edit.component';
import { ManufacturerDeleteComponent } from './components/manufacturers/manufacturer-delete/manufacturer-delete.component';
import { ProductsOverviewComponent } from './components/products/products-overview/products-overview.component';
import { ProductDetailsComponent } from './components/products/product-details/product-details.component';
import { ProductCreateComponent } from './components/products/product-create/product-create.component';
import { ProductEditComponent } from './components/products/product-edit/product-edit.component';
import { ProductDeleteComponent } from './components/products/product-delete/product-delete.component';
import { ReviewDetailsComponent } from './components/reviews/review-details/review-details.component';
import { ReviewCreateComponent } from './components/reviews/review-create/review-create.component';
import { ReviewEditComponent } from './components/reviews/review-edit/review-edit.component';
import { ReviewDeleteComponent } from './components/reviews/review-delete/review-delete.component';
import { ManufacturersCountStatisticComponent } from './components/manufacturers/manufacturers-count-statistic/manufacturers-count-statistic.component';

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
    path:"users/create",
    component: UserCreateComponent
  },
  {
    path:"users/:id",
    component: UserDetailsComponent
  },
  {
    path:"users/:id/edit",
    component: UserEditComponent
  },
  {
    path:"users/:id/delete",
    component: UserDeleteComponent
  },
  {
    path:"products/sorted-by-score",
    component: ProductsScoreStatisticComponent
  },
  {
    path:"manufacturers",
    component: ManufacturersOverviewComponent
  }, 
  {
    path:"manufacturers/create",
    component: ManufacturerCreateComponent
  },
  {
    path:"manufacturers/sorted-by-products",
    component: ManufacturersCountStatisticComponent
  },
  {
    path:"manufacturers/:id",
    component: ManufacturerDetailsComponent
  },
  {
    path:"manufacturers/:id/edit",
    component: ManufacturerEditComponent
  },  
  {
    path:"manufacturers/:id/delete",
    component: ManufacturerDeleteComponent
  },
  {
    path:"products",
    component: ProductsOverviewComponent
  },
  {
    path:"products/create",
    component: ProductCreateComponent
  },
  {
    path:"products/:id",
    component: ProductDetailsComponent
  },
  {
    path:"products/:id/edit",
    component: ProductEditComponent
  },
  {
    path:"products/:id/delete",
    component: ProductDeleteComponent
  },
  {
    path: "reviews",
    component: ReviewDetailsComponent
  },
  {
    path: "reviews/create",
    component: ReviewCreateComponent
  },
  {
    path: "reviews/edit",
    component: ReviewEditComponent
  },
  {
    path: "reviews/delete",
    component: ReviewDeleteComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
