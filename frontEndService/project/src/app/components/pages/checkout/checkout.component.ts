import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Email} from '../../interfaces/email.interface';
import {CartService} from '../cart/cart.service';
import {ToastService} from '../Toast/toast-container/toast.service';
import {NgbProgressbarConfig} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {loadStripe} from '@stripe/stripe-js';
import {QuotationDetails} from '../../interfaces/ctemporal.interface';
import {OrderPayment} from '../../interfaces/orderPayment'
import {environment} from 'src/environments/environment';

const stripePromise = loadStripe('pk_test_TYooMQauvdEDq54NiTphI7jx');

@Component({
    selector: 'app-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnInit {
    email: Email = new Email();
    frmGroup: FormGroup;
    loggedIn: boolean;
    isPaying: boolean;
    date: string;
    cotizacion: QuotationDetails;
    total: number;
    numOrden: number;
    OrderPayment = new OrderPayment();

    constructor(
        private router: Router,
        private cartService: CartService,
        public toastService: ToastService,
        private formBuilder: FormBuilder,
        config: NgbProgressbarConfig
    ) {
        config.max = 1000;
        config.striped = true;
        config.animated = true;
        config.type = 'info';
        config.height = '6px';
        this.loadContent();
    }

    loadContent() {
        this.frmGroup = this.formBuilder.group({
            txtName: [null, [Validators.required]],
            txtLastName: [null, [Validators.required]],
            txtAddress: [null, [Validators.required]],
            txtCity: [null, [Validators.required]],
            txtState: [null, [Validators.required]],
            txtEmail: [null, [Validators.required]],
            txtPhone: [null, [Validators.required]],
            txtId: [null, [Validators.required]],

        });
    }

    ngOnInit(): void {
        this.numOrden = new Date().valueOf();
        this.cotizacion = JSON.parse(localStorage.getItem('Cotizacion')) as QuotationDetails;
        this.total = JSON.parse(localStorage.getItem('Total'));
        this.setInfoProfile();
        this.getDate();


    }

    getDate() {
        if (localStorage.getItem('fechaViaje')) {
            let date = JSON.parse(localStorage.getItem('fechaViaje'));
            this.date = date.month + '/' + date.day + '/' + date.year;

        }
    }


    setInfoProfile() {
        if (localStorage.getItem('userAutenticado')) {
            let user = JSON.parse(localStorage.getItem('userAutenticado'));
            this.frmGroup.get('txtName').setValue(user.name);
            this.frmGroup.get('txtLastName').setValue(user.lastName);
            this.frmGroup.get('txtEmail').setValue(user.email);
            this.frmGroup.get('txtPhone').setValue(user.phone);
            this.frmGroup.get('txtCity').setValue('Bogotá');
            this.frmGroup.get('txtState').setValue('D.C');
            this.loggedIn = true;
        }
    }

    async goToPay() {

        this.setOrderPayment();

        this.isPaying = true;

        const payment = {total: this.total, items: 'Paquete de V+H+E', quantity: 1};

        const stripe = await stripePromise;

        const response = await fetch(`${environment.paymentServiceUrl}api/PaymentController/create-checkout-session`, {
          method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payment)
        });

        const session = await response.json();

        const result = await stripe.redirectToCheckout({
            sessionId: session.id,
        });

        if (result.error) {
            this.toastService.show('Hubo un problema al procesar su pago', {classname: 'bg-danger text-light', delay: 15000});
        }
    }
  setOrderPayment() {

    this.OrderPayment.name =  this.frmGroup.get('txtName').value;
    this.OrderPayment.email =  this.frmGroup.get('txtEmail').value;
    this.OrderPayment.lastName  =  this.frmGroup.get('txtLastName').value;
    this.OrderPayment.idDocument  =  this.frmGroup.get('txtId').value;
    this.OrderPayment.phone  =  this.frmGroup.get('txtId').value;
    this.OrderPayment.addres  =  this.frmGroup.get('txtAddress').value;
    this.OrderPayment.selectProviders = this.cotizacion.selectProviders;
    this.OrderPayment.totalOrder =  this.total;

  }

    sendEmail() {
        this.email.email = this.frmGroup.get('txtEmail').value;
        this.email.content = ``;
        this.email.subject = 'Resumen de su compra';
        this.cartService.sendEmail(this.email).subscribe((result) => {
            this.isPaying = false;
            this.router.navigate(['/']);

        });
    }
}
