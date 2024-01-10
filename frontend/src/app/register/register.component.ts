import {
  Component,
  ElementRef,
  Injector,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { RouterLink } from "@angular/router";
import { DialogService, OFormComponent, OntimizeService } from "ontimize-web-ngx";
import { Router } from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  protected userService: OntimizeService;
  @ViewChild("signUpForm", { static: false }) signUpForm: OFormComponent;
  constructor(
    protected injector: Injector,
    protected dialogService: DialogService
  ) {
    this.userService = this.injector.get(OntimizeService);
  }

  ngOnInit() {
    this.configureService();
    this.signUpForm.setInsertMode();
  }

  protected configureService() {
    const conf = this.userService.getDefaultServiceConfiguration("users");
    this.userService.configureService(conf);
  }

  public async signUpUser(){
    this.signUpForm.insert();
}
}
