import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators} from '@angular/forms'
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-static',
  templateUrl: './static.component.html',
  styleUrls: ['./static.component.css']
})
export class StaticComponent implements OnInit{

  // exampleForm = new FormGroup ({
  //   from : new FormControl(),
  //   To : new FormControl(),
  // });

  // model : any = {};

  // onSubmit(){
  //   alert('SUCCESS!' + JSON.stringify(this.model))
  // }

  registerForm : FormGroup;
  submitted = false;
  userID;
  appID;
  a;
  constructor(private formBuilder : FormBuilder,
    private route: ActivatedRoute) { 
      this.route.params.subscribe( params => {
        this.userID =params['userID'];
        this.appID = params['appID'];
      } );
    }

  ngOnInit(){

    this.registerForm = this.formBuilder.group({
      firstName: ['',Validators.required],
      lastName : ['',Validators.required],
      email: ['',[Validators.required,Validators.email]],
      password: ['',[Validators.required,Validators.minLength(6)]]
    });
  }

  get f() { return this.registerForm.controls;}

  onSubmit(){
    this.submitted =  true;

    if(this.registerForm.invalid){
      return;
    }

    alert('SUCCESS!')
  }
}
