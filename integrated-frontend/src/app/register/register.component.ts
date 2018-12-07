import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { FormGroup, FormBuilder, Validators, NgForm, FormControl } from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationform: FormGroup;
  email: string;
  user: User = new User();

  constructor(private formBuilder: FormBuilder, private userBack:UserService,private router: Router) { }

  ngOnInit() {
    this.registrationform = new FormGroup({
      usernameform: new FormControl(),
      passwordform: new FormControl(),
      emailform: new FormControl({'email': [this.user.email, [
        Validators.required, Validators.email
      ]]}),
      phonenumberform: new FormControl(),
   });

  }

  submitted = false;
  public message="Successfully registered";

  onSubmit() { 
    this.submitted = true;    
    alert(this.message); 
    if (this.submitted=true) {
      this.router.navigate([`/auth/login`]);
      }   
  }

  addUsers(user:User){
    var userCast={
    "id":0,
    "userName": this.user.userName,
    "password": this.user.password,
    "email": this.user.email,
    "phonenumber": this.user.phonenumber
    }
    this.userBack.addUser(userCast).subscribe();
    }
}
