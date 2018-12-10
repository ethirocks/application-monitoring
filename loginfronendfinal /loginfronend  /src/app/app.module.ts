import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LoginModule } from './login/login/login.module';
import { FormsModule, ReactiveFormsModule}   from '@angular/forms';
import {MatTabsModule} from '@angular/material/tabs';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { LogoutComponent } from './logout/logout.component';
import { AppregisterComponent } from './appregister/appregister.component';
import { FooterComponent } from './footer/footer.component';
import {MatCardModule} from '@angular/material';
import {MatButtonModule} from '@angular/material';
import {MatDialogModule} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MyDialogComponent } from './my-dialog/my-dialog.component';
import { MyDialog1Component } from './my-dialog1/my-dialog1.component';
import { MyDialog2Component } from './my-dialog2/my-dialog2.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    DashboardComponent,
    LogoutComponent,
    AppregisterComponent,
    FooterComponent,
    MyDialogComponent,
    MyDialog1Component,
    MyDialog2Component
  ],
  imports: [
    BrowserModule,
    LoginModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    HttpClientModule,
    MatDialogModule,
    MatCardModule,
    MatButtonModule,
    BrowserAnimationsModule,
  ],
  entryComponents: [MyDialogComponent,MyDialog1Component,MyDialog2Component],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
