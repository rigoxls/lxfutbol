import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Registry } from '../../interfaces/registry';
import { ToastService } from '../Toast/toast-container/toast.service';
import { RegisterService } from './register.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {


  registry: Registry = new Registry();

  form = new FormGroup({
    name: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    idUser: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  constructor(
    private registerService: RegisterService,
    private router: Router,
    public toastService: ToastService,
    private modalService: NgbModal) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.registry.name = this.form.value.name;
    this.registry.lastName = this.form.value.lastName;
    this.registry.idUser = this.form.value.idUser;
    this.registry.phone = this.form.value.phone;
    this.registry.email = this.form.value.email;
    this.registry.password = this.form.value.password;

    this.registerService.registryUser(this.registry).subscribe(
      result => {
        this.showSuccess();
      },
      err => {
        this.showError();
      }
    );
  }

  showSuccess() {
    this.toastService.show("Usuario Registrado", { classname: 'bg-success text-light', delay: 15000 });
    this.router.navigate(["/register"]);
  }

  showError() {
    this.toastService.show("El usuario ya existe o se genero un error en el proceso", { classname: 'bg-danger text-light', delay: 15000 });
    this.router.navigate(["/register"]);
}
}
