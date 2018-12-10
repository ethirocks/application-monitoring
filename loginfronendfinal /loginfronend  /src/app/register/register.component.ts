import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { FormGroup, FormBuilder, Validators, NgForm, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();

  constructor(private formBuilder: FormBuilder, private userBack:UserService) { }

  ngOnInit() {
  }

  submitted = false;

  onSubmit() { 
    this.submitted = true;     
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

  registrationform = new FormGroup({
      usernameform: new FormControl(),
      passwordform: new FormControl(),
      emailform: new FormControl(),
      phonenumberform: new FormControl(),
   });

}
