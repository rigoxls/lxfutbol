import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from './user.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
    public form: FormGroup;
    public showMessage = false;
    public message = '';
    public errorMessage = false;
    public userId = null;
    public submitted = false;

    public sites = [];
    public isProvider = false;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.userId = this.route.snapshot.params.id;

        if (this.userId) {
            this.getUserById(this.userId);
        }

        this.form = new FormGroup({
            tipoUsuario: new FormControl('', Validators.required),
            apellidos: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            nombres: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            nombreProveedor: new FormControl(''),
            nombreRepresentante: new FormControl(''),
            telefono: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            identificacion: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            email: new FormControl('', [
                Validators.required,
                Validators.email,
                Validators.minLength(3)
            ]),
            password: new FormControl('', [
                Validators.required,
                Validators.minLength(8)
            ]),
            password2: new FormControl('', [
                Validators.required,
                Validators.minLength(8)
            ])
        });

        this.form.get('nombreProveedor').clearValidators();
        this.form.get('nombreProveedor').updateValueAndValidity();

        this.form.get('nombreRepresentante').clearValidators();
        this.form.get('nombreRepresentante').updateValueAndValidity();
    }

    async getUserById(userId) {
        const user = await this.userService.getUserById(userId);
        this.form.controls['nombres'].setValue(user['nombres']);
        this.form.controls['apellidos'].setValue(user['apellidos']);
        this.form.controls['identificacion'].setValue(user['identificacion']);
        this.form.controls['email'].setValue(user['email']);
        this.form.controls['telefono'].setValue(user['telefono']);
        this.form.controls['nombreProveedor'].setValue(user['nombreProveedor']);
        this.form.controls['nombreRepresentante'].setValue(user['nombreRepresentante']);
        this.form.controls['tipoUsuario'].setValue(user['idRole']);
        setTimeout( () => {
            this.changeUserType(2);
        }, 500);
    }

    changeUserType(userType): void {
        if (userType === 2) {
            this.isProvider = true;
            this.form.get('nombres').clearValidators();
            this.form.get('nombres').updateValueAndValidity();

            this.form.get('apellidos').clearValidators();
            this.form.get('apellidos').updateValueAndValidity();

            this.form.get('nombreProveedor').setValidators([Validators.required, Validators.minLength(3)]);
            this.form.get('nombreProveedor').updateValueAndValidity();

            this.form.get('nombreRepresentante').setValidators([Validators.required, Validators.minLength(3)]);
            this.form.get('nombreRepresentante').updateValueAndValidity();
        } else {
            this.isProvider = false;

            this.form.get('nombres').setValidators([Validators.required, Validators.minLength(3)]);
            this.form.get('nombres').updateValueAndValidity();

            this.form.get('apellidos').setValidators([Validators.required, Validators.minLength(3)]);
            this.form.get('apellidos').updateValueAndValidity();

            this.form.get('nombreProveedor').clearValidators();
            this.form.get('nombreProveedor').updateValueAndValidity();

            this.form.get('nombreRepresentante').clearValidators();
            this.form.get('nombreRepresentante').updateValueAndValidity();
        }
    }

    onSubmit(): void {
        this.submitted = true;
        const password1 = this.form.get('password').value;
        const password2 = this.form.get('password2').value;
        if (this.form.invalid || password1 !== password2) {
            window.scrollTo(0, 0);
            return;
        }

        this.userService.upsertActivity(this.form.value, this.userId)
            .then(result => {
                if (result['status']) {
                    this.showMessage = true;
                    this.message = 'Usuario guardado exitosamente';
                    window.scrollTo(0, 0);
                }
            })
            .catch(error => {
                this.showMessage = true;
                this.errorMessage = true;
                this.message = 'Ha ocurrido un error en la creación del usuario';
                window.scrollTo(0, 0);
            });
    }

}
