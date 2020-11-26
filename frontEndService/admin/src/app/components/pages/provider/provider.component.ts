import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ProviderService} from './/provider.service';
import {ActivatedRoute} from '@angular/router';
import {environment} from 'src/environments/environment';

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
    public rules = {};

    public agreement = 0;
    public rulesProcessed = {};

    constructor(
        private providerService: ProviderService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.providerId = this.route.snapshot.params.id;
        if (this.providerId) {
            this.getProvider(this.providerId);
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
                Validators.required
            ]),
        });
    }

    async getProvider(providerId) {
        const provider = await this.providerService.getProvidertById(providerId);
        this.form.controls['nit'].setValue(provider['nit']);
        this.form.controls['name'].setValue(provider['name']);
        this.form.controls['representative'].setValue(provider['representative']);
        this.form.controls['phone'].setValue(provider['phone']);
        this.form.controls['email'].setValue(provider['email']);
        this.form.controls['address'].setValue(provider['address']);
        this.form.controls['searchContract'].setValue(provider['operationEntity']['search']);
        this.form.controls['bookContract'].setValue(provider['operationEntity']['book']);
        this.form.controls['cancelContract'].setValue(provider['operationEntity']['cancelBook']);
        this.form.controls['dataType'].setValue(provider['dataType']);
        this.form.controls['agreement'].setValue(provider['agreement']);
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

    authGoogle() {
        this.providerService.authGoogle();
        setTimeout(() => {
            const popup = window.open(`${environment.googleApi}`,
                '_blank', 'top=0,left=0,width=600,height=600');
        }, 200);
    }

    getRules() {
        this.providerService.getRules().then(result => {
            this.rules = result;
        });
    }

    calculateAgreement(value, condition, valRef, score, id) {
        switch (condition) {
            case '">"':
                if (parseFloat(value) > parseFloat(valRef)) {
                    this.agreement = this.agreement + parseFloat(score);
                    this.rulesProcessed[id] = true;
                    ;
                } else {
                    if (this.rulesProcessed[id]) {
                        this.agreement = this.agreement - parseFloat(score);
                        delete this.rulesProcessed[id];
                    }
                }
                break;

            case '"<"':
                if (parseFloat(value) < parseFloat(valRef)) {
                    this.agreement = this.agreement + parseFloat(score);
                    this.rulesProcessed[id] = true;
                } else {
                    if (this.rulesProcessed[id]) {
                        this.agreement = this.agreement - parseFloat(score);
                        delete this.rulesProcessed[id];
                    }
                }
                break;

            case '"="':
                if (parseFloat(value) === parseFloat(valRef)) {
                    this.agreement = this.agreement + parseFloat(score);
                    this.rulesProcessed[id] = true;
                } else {
                    if (this.rulesProcessed[id]) {
                        this.agreement = this.agreement - parseFloat(score);
                        delete this.rulesProcessed[id];
                    }
                }
                break;

            case '"<>"':
                if (parseFloat(value) !== parseFloat(valRef)) {
                    this.agreement = this.agreement + parseFloat(score);
                    this.rulesProcessed[id] = true;
                } else {
                    if (this.rulesProcessed[id]) {
                        this.agreement = this.agreement - parseFloat(score);
                        delete this.rulesProcessed[id];
                    }
                }
                break;
        }

        this.agreement = parseFloat(this.agreement.toFixed(2));
    }

}
