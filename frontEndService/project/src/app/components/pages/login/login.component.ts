import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Usuario } from '../../interfaces/usuario.interface';
import { LoginService } from './login.service';
import { Router } from '@angular/router';
import { CONFIG } from '../../../../assets/config';
import { NgbProgressbarConfig } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../Toast/toast-container/toast.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    user: Usuario;
    message: string;
    isLoad = false;
    form = new FormGroup({
        email: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
    });

    constructor(
        private loginService: LoginService,
        private router: Router,
        private toastService: ToastService,
        config: NgbProgressbarConfig
    ) {
        config.max = 1000;
        config.striped = true;
        config.animated = true;
        config.type = "info";
        config.height = "6px";
    }

    ngOnInit(): void {
    }

    onSubmit(): void {

        if (this.form.value.email === '' && this.form.value.password === '') {
            this.showErrorDatos()
        } else if (this.form.value.email !== '' && this.form.value.password !== '') {
            this.isLoad = true;
            this.getUsusario();
        }
    }

    getUsusario() {/*
        this.loginService.login(this.form.value.email, this.form.value.password).subscribe(
            result => {
                this.user = result;
            },
            err => {
                this.showError();
            }
        );

        localStorage.setItem('userAutenticado', JSON.stringify(this.user));
        setTimeout(() => {

            if (this.user.rol['id'] === 1) {
                //window.location.href = CONFIG.userLoggedPath;
                this.router.navigate(["/"]);
            } else {
                window.location.href = CONFIG.adminPath;
            }
        }, 1000);
        return this.user;*/
    }

    showErrorDatos() {
        this.toastService.show("Ingrese los datos para autenticarse", { classname: 'bg-success text-light', delay: 1500 });
        this.router.navigate(["/login"]);
    }

    showError() {
        this.toastService.show("Verifique lo datos. El usuario no existe", { classname: 'bg-danger text-light', delay: 1500 });
        this.router.navigate(["/login"]);
    }

}
