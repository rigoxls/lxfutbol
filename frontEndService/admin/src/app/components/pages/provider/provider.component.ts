import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProviderService } from './/provider.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
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
    public transportId = null;
    public submitted = false;

    public selectedFile: File;
    public activities = [];
    public images = [];

    public urlPath = environment.APIEndPoint+'images/';

    constructor(
        private transportService: ProviderService,
        private route: ActivatedRoute,
        private httpClient: HttpClient) {
        this.getActivities();
    }

    ngOnInit(): void {
        this.transportId = this.route.snapshot.params.id;
        if (this.transportId) {
            this.getTransport(this.transportId);
        }

        this.form = new FormGroup({
            transportadora: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            nombreRepresentante: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            telefono: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            actividades: new FormControl('', Validators.required),
            descripcion: new FormControl('', [
                Validators.required,
                Validators.minLength(10)
            ]),
            costoPersona: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ])
        });
    }

    async getTransport(transportId) {
        const transport = await this.transportService.getTransportById(transportId);
        this.form.controls['transportadora'].setValue(transport['transportadora']);
        this.form.controls['nombreRepresentante'].setValue(transport['nombreRepresentante']);
        this.form.controls['telefono'].setValue(transport['telefono']);
        this.form.controls['actividades'].setValue(transport['idActividades']);
        this.form.controls['descripcion'].setValue(transport['descripcion']);
        this.form.controls['costoPersona'].setValue(transport['costoPersona']);

        this.images = transport['images'];
        setTimeout(() => {
            const filteredSites = this.activities.filter(activity => transport['idActividades'].includes(parseInt(activity.id, 10)));
            this.form.controls['actividades'].setValue(filteredSites);
        }, 1000);
    }

    async getActivities() {
        this.activities = await this.transportService.getActivities();
        this.activities = this.activities.map(activity => {
            return {
                id: activity['id'],
                name: activity['nombreActividad']
            };
        });
    }

    public onFileChanged(event) {
        // Select File
        this.selectedFile = event.target.files[0];
        this.onUpload();
    }

    onUpload() {
        const uploadImageData = new FormData();
        uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

        this.httpClient.post(environment.APIEndPoint+'image/upload', uploadImageData, {observe: 'response'})
            .subscribe((response) => {
                    if (response.status === 201) {
                        setTimeout(() => {
                            this.images.push(response.body['file']);
                        }, 2500);
                    } else {
                        console.info(response);
                    }
                }
            );
    }

    removeImage(image) {
        const index = this.images.indexOf(image);
        if (index > -1) {
            this.images.splice(index, 1);
        }
    }

    onSubmit(): void {
        this.submitted = true;
        if (this.form.invalid) {
            window.scrollTo(0, 0);
            return;
        }
        this.transportService.upsertTransport(this.form.value, this.transportId, this.images)
            .then(result => {
                if (result['status']) {
                    this.showMessage = true;
                    this.errorMessage = false;
                    this.message = 'Servicio de transporte guardado exitosamente';
                    window.scrollTo(0, 0);
                }
            })
            .catch(error => {
                this.showMessage = true;
                this.errorMessage = true;
                this.message = 'Ha ocurrido un error en la creación del servicio de transporte';
                window.scrollTo(0, 0);
            });
    }

}
