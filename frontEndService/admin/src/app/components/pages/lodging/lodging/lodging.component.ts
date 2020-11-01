import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { LodgingService } from "./lodging.service";
import { environment } from "src/environments/environment";

@Component({
    selector: "app-lodging",
    templateUrl: "./lodging.component.html",
    styleUrls: ["./lodging.component.scss"],
})
export class LodgingComponent implements OnInit {
    public form: FormGroup;
    public showMessage = false;
    public message = "";
    public errorMessage = false;
    public lodgingId = null;
    public submitted = false;

    public selectedFile: File;
    public activities = [];
    public images = [];


    public urlPath = environment.APIEndPoint+"images/";

    constructor(
        private lodgingService: LodgingService,
        private route: ActivatedRoute,
        private formBuilder: FormBuilder,
        private httpClient: HttpClient
    ) {
      this.loadContent()
    }

    loadContent(): void {
        this.form = this.formBuilder.group({
            nombre: [null, [Validators.required]],
            telefono: [null, [Validators.required]],
            estado: ['1', Validators.required],
            direccion: [null, [Validators.required]],
            actividades: [null, [Validators.required]],
            descripcion: [null, [Validators.required]],
            costoPersona: [null, [Validators.required]],
        });
    }

    ngOnInit(): void {
        this.getActivities();
        this.lodgingId = this.route.snapshot.params.id;
        if (this.lodgingId) {
            this.getLodging(this.lodgingId);
        }
    }

    async getLodging(lodgingId: number) {
        const lodging = await this.lodgingService
            .getLodgingById(lodgingId)
            .toPromise();
            this.form.controls['nombre'].setValue(lodging['nombre']);
            this.form.controls['direccion'].setValue(lodging['direccion']);
            this.form.controls['telefono'].setValue(lodging['telefono']);
            this.form.controls['actividades'].setValue(lodging['idActividades']);
            this.form.controls['descripcion'].setValue(lodging['descripcion']);
            this.form.controls['costoPersona'].setValue(lodging['costoPersona']);
            this.form.controls['estado'].setValue(lodging['estado']);
            this.images = lodging['images'];
            setTimeout(() => {
                const filteredSites = this.activities.filter(activity => lodging['idActividades'].includes(parseInt(activity.id, 10)));
                this.form.controls['actividades'].setValue(filteredSites);
            }, 1000);
        }

        async getActivities() {
          let activities: any = await this.lodgingService
              .getActivities()
              .toPromise();
          this.activities = activities;
          this.activities = this.activities.map((activity) => {
              return {
                  id: activity["id"],
                  name: activity["nombreActividad"],
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
        uploadImageData.append(
            "imageFile",
            this.selectedFile,
            this.selectedFile.name
        );

        this.httpClient
            .post(environment.APIEndPoint+"image/upload", uploadImageData, {
                observe: "response",
            })
            .subscribe((response) => {
                if (response.status === 201) {

                    setTimeout(() => {
                        this.images.push(response.body['file']);
                    }, 2500);
                } else {
                    console.info(response);
                }
            });
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
      this.lodgingService.upsertLodging(this.form.value, this.lodgingId, this.images)
          .then(result => {
              if (result['status']) {
                  this.showMessage = true;
                  this.errorMessage = false;
                  this.message = 'Servicio de hospedaje guardado exitosamente';
                  window.scrollTo(0, 0);
              }
          }),
          (error => {
              this.showMessage = true;
              this.errorMessage = true;
              this.message = 'Ha ocurrido un error en la creación del servicio de hospedaje';
              window.scrollTo(0, 0);
          });
  }

}
