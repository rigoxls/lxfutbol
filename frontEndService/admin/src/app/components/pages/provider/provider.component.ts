import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProviderService } from './/provider.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.scss']
})
export class ProviderComponent implements OnInit {

    public form: FormGroup;
    public showMessage = false;
    public message = '';
    public errorMessage = false;
    public providerId = null;
    public submitted = false;

    public images = [];


    constructor(
        private providerService: ProviderService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.providerId = this.route.snapshot.params.id;
        if (this.providerId) {
            this.getTransport(this.providerId);
        }

        this.form = new FormGroup({
            nit: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            name: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            representative: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            phone: new FormControl('', [
                Validators.required,
                Validators.minLength(10)
            ]),
            phone2: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            email: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            address: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            type: new FormControl('1', Validators.required),
            searchContract: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            bookContract: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            cancelContract: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            status: new FormControl('1', Validators.required),
            dataType: new FormControl('1', Validators.required),
            agreement: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
        });
    }

    async getTransport(providerId) {
        const transport = await this.providerService.getTransportById(providerId);
        this.form.controls['transportadora'].setValue(transport['transportadora']);
        this.form.controls['nombreRepresentante'].setValue(transport['nombreRepresentante']);
        this.form.controls['telefono'].setValue(transport['telefono']);
        this.form.controls['actividades'].setValue(transport['idActividades']);
        this.form.controls['descripcion'].setValue(transport['descripcion']);
        this.form.controls['costoPersona'].setValue(transport['costoPersona']);

    }

    onSubmit(): void {
        this.submitted = true;
        if (this.form.invalid) {
            window.scrollTo(0, 0);
            return;
        }
        this.providerService.upsertProvider(this.form.value, this.providerId)
            .then(result => {
                if (result['status']) {
                    this.showMessage = true;
                    this.errorMessage = false;
                    this.message = 'Proveedor guardado exitosamente';
                    window.scrollTo(0, 0);
                }
            })
            .catch(error => {
                this.showMessage = true;
                this.errorMessage = true;
                this.message = 'Ha ocurrido un error en la creación del proveedor';
                window.scrollTo(0, 0);
            });
    }

}
