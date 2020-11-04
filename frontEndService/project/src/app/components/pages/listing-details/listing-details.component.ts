import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { ListingDetailsService } from "./listing-details.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ProviderModalComponent } from "./provider-modal/provider-modal.component";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { QuotationDetails } from "../../interfaces/ctemporal.interface";
import { DomSanitizer } from '@angular/platform-browser';
import { ProviderSelection } from '../../interfaces/providerSelection..interface'
import { ToastService } from '../Toast/toast-container/toast.service';
import { CONFIG } from 'dist/louise-ng/assets/config';

@Component({
    selector: "app-listing-details",
    templateUrl: "./listing-details.component.html",
    styleUrls: ["./listing-details.component.scss"],
})
export class ListingDetailsComponent implements OnInit {
    @ViewChild("box") numPersons: ElementRef;
    details:any;
    hotelSelection: any;
    arryPrices: Array<ProviderSelection>= new Array<ProviderSelection>(3);
    totalPrice: number;
    closeResult = "";
    frmGroup: FormGroup;
    submitted: boolean;
    quotation: QuotationDetails = new QuotationDetails();
    src:string;
    url: any;
    base:boolean;
    showToast: boolean;
    imagePath = CONFIG.imagePath;
    constructor(
        private dom: DomSanitizer,
        private listingDetailsService: ListingDetailsService,
        private modalService: NgbModal,
        private formBuilder: FormBuilder,
        private router: Router,
        private route: ActivatedRoute,
        public toastService: ToastService

    ) {
        this.loadContent();
    }

    ngOnInit(): void {
        this.route.queryParams.subscribe((params) => {
            this.getDetails(params.id);
        });
        this.setInfoProfile();
    }

    private async getDetails(id: number) {
        const details = await this.listingDetailsService.getActivities(id).toPromise();
        this.details = details;
        this.src='https://www.google.com/maps/embed/v1/place?q='+this.details.latitud+','+this.details.longitud+'&key=AIzaSyBCO2sM4U_hk39ps6YmJs5CTXUBPhkvkU8';
        this.url=this.dom.bypassSecurityTrustResourceUrl(this.src); 
        this.arryPrices[0]={id:this.details.idActividad,costoPersona:this.details.precioBase,nombre:this.details.nombreActividad};
    }


    loadContent(): void {
        this.frmGroup = this.formBuilder.group({
            txtName: [null, [Validators.required]],
            txtEmail: [null, [Validators.required]],
            txtDate: ['05/12/2020', [Validators.required]],
            txtMessage: [null],
        });
    }

    

    calculateTotalPrice(item: any, type: string) {
        this.base=true;
        if (type == "t") {
            this.arryPrices[1]={id:item.idTransportadora,costoPersona:item.costoPersona,nombre:item.transportadora};
        } else {
            this.arryPrices[2]={id:item.idHospedaje,costoPersona:item.costoPersona,nombre:item.nombre};
        }
        
        this.totalPrice = this.arryPrices
        .map(c => c.costoPersona)
        .reduce(
            (sum: number, values) => sum + values,
            0
        );
    }

    public submit(dangerTpl): void {
      this.submitted = true;  
      this.frmGroup.updateValueAndValidity();
      if (this.frmGroup.invalid) {
        this.toastService.show(dangerTpl, { classname: 'bg-danger text-light', delay: 15000 });
        return;
      }
      this.getQuotation();
    }

    setInfoProfile(){
        if (localStorage.getItem("userAutenticado")) {
            let user = JSON.parse(localStorage.getItem("userAutenticado"));
            this.frmGroup.get("txtName").setValue(user.nombreUsuario + " " + user.apellidosUsuario);
            this.frmGroup.get("txtEmail").setValue(user.emailUsuario);
          } 
    }
 
    getQuotation() {
        this.quotation.numPersonas=this.numPersons.nativeElement.value;
        this.quotation.nombre=this.frmGroup.get("txtName").value;
        this.quotation.email= this.frmGroup.get("txtEmail").value;
        this.quotation.fechaInicio=this.frmGroup.get("txtDate").value;
        this.quotation.fechaFin=this.frmGroup.get("txtDate").value;
        this.quotation.seleccionProvedores = this.arryPrices
        this.quotation.fechaCotizacion = new Date();
        localStorage.setItem("Cotizacion",JSON.stringify(this.quotation))
        this.router.navigate(['/cart']);
   }

    getProviderDetail(item:any) {
        const modalRef = this.modalService.open(ProviderModalComponent);
        modalRef.componentInstance.name = item;
    }
}
