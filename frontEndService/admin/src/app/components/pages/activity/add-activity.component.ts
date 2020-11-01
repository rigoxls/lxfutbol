import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivityService} from './activity-service.service';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {environment} from "src/environments/environment";

@Component({
    selector: 'app-add-listing',
    templateUrl: './add-activity.component.html',
    styleUrls: ['./add-activity.component.scss']
})
export class AddActivityComponent implements OnInit {
    public form: FormGroup;
    public showMessage = false;
    public message = '';
    public errorMessage = false;
    public activityId = null;
    public submitted = false;

    public selectedFile: File;
    public sites = [];
    public images = [];

    public urlPath = environment.APIEndPoint + 'images/';

    constructor(
        private activityService: ActivityService,
        private route: ActivatedRoute,
        private httpClient: HttpClient) {
        this.getSites();
    }

    ngOnInit(): void {
        this.activityId = this.route.snapshot.params.id;
        if (this.activityId) {
            this.getActivity(this.activityId);
        }

        this.form = new FormGroup({
            nombreActividad: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            categoria: new FormControl('', Validators.required),
            descripcion: new FormControl('', [
                Validators.required,
                Validators.minLength(10)
            ]),
            estado: new FormControl('1', Validators.required),
            precioBase: new FormControl('', [
                Validators.required,
                Validators.minLength(3)
            ]),
            idSitio: new FormControl('', Validators.required)
        });
    }

    async getActivity(activityId) {
        const activity = await this.activityService.getActivityById(activityId);
        this.form.controls['nombreActividad'].setValue(activity['nombreActividad']);
        this.form.controls['categoria'].setValue(activity['categoria']);
        this.form.controls['descripcion'].setValue(activity['descripcion']);
        this.form.controls['precioBase'].setValue(activity['precioBase']);
        this.form.controls['estado'].setValue(activity['estado']);

        this.images = activity['images'];

        const siteId = activity['sitioTuristico']['id_sitio'];
        setTimeout(() => {
            const filteredSites = this.sites.filter(site => site.id === siteId);
            const index = this.sites.indexOf(filteredSites[0]);
            this.form.controls['idSitio'].setValue(this.sites[index]);
        }, 1000);
    }

    async getSites() {
        this.sites = await this.activityService.getSites();
        this.sites = this.sites.map(site => {
            return {
                id: site['id_sitio'],
                name: site['nombreSitio']
            };
        });
    }

    public onFileChanged(event) {
        // Select File
        this.selectedFile = event.target.files[0];
        this.onUpload();
    }

    onUpload() {
        // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
        const uploadImageData = new FormData();
        uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

        this.httpClient.post(environment.APIEndPoint + 'image/upload', uploadImageData, {observe: 'response'})
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
        this.activityService.upsertActivity(this.form.value, this.activityId, this.images)
            .then(result => {
                if (result['status']) {
                    this.showMessage = true;
                    this.errorMessage = false;
                    this.message = 'Actividad guardada exitosamente';
                    window.scrollTo(0, 0);
                }
            })
            .catch(error => {
                this.showMessage = true;
                this.errorMessage = true;
                this.message = 'Ha ocurrido un error en la creación de la actividad';
                window.scrollTo(0, 0);
            });
    }

}
