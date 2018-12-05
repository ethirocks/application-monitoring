import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login/login/login.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';
import { AppregisterComponent } from './appregister/appregister.component';
import { FooterComponent } from './footer/footer.component';

const routes: Routes = [
  {
      path: 'home',
      component: HomeComponent
  },
  {
    path: 'auth/login',
    component: LoginComponent
},
{
  path: '',
  redirectTo: 'home',
  pathMatch: 'full'
},
{
 path: 'dashboard',
 component: DashboardComponent 
},
{ 
path: 'register',
component: RegisterComponent
 },
{path: 'logout',
component: LogoutComponent
},
{path: 'appregister',
component: AppregisterComponent
},
{path: 'footer',
component: FooterComponent
},
];
@NgModule({
  imports: [
    RouterModule,
    RouterModule.forRoot(routes)
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule { }
