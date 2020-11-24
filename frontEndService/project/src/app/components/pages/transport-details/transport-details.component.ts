import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Transport} from '../../interfaces/transport.interface';
import {NgbCalendar, NgbDate, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {TransportService} from './transport.service';

@Component({
    selector: 'app-transport-details',
    templateUrl: './transport-details.component.html',
    styleUrls: ['./transport-details.component.scss']
})
export class TransportDetailsComponent implements OnInit {
    @ViewChild('nPersons') nPersons: ElementRef;

    public hideLandFilter = false;
    public hidePlaneFilter = false;

    public selectActionName = 'Seleccionar';

    transports: Transport[] = [];
    bkTransports: Transport[];

    hoveredDate: NgbDate | null = null;

    fromDate: NgbDate | null;
    toDate: NgbDate | null;

    constructor(private transportService: TransportService, private calendar: NgbCalendar, public formatter: NgbDateParserFormatter) {
        this.getTransports();
        setTimeout(() => {
            const lis = document.getElementsByClassName('option');
            // @ts-ignore
            for (const li of lis) {
                li.addEventListener('click', (data) => {
                    const text = data.target.innerText;

                    switch (text) {
                        case 'Ordenar: Destacados':
                            this.sortTransports(1);
                            break;
                        case 'Ordenar: Precio mas bajo':
                            this.sortTransports(2);
                            break;
                        case 'Ordenar: Precio mas alto':
                            this.sortTransports(3);
                            break;
                    }

                }, false);
            }

        }, 1000);

    }

    ngOnInit(): void {
        const spectacleBooked = JSON.parse(localStorage.getItem('spectacleBook'));
        const initDate = spectacleBooked.date.split('-');
        if (Array.isArray(initDate)) {
            this.fromDate = new NgbDate(parseInt(initDate[0], 10), parseInt(initDate[1], 10), (parseInt(initDate[2], 10)));
            this.toDate = new NgbDate(parseInt(initDate[0], 10), parseInt(initDate[1], 10), (parseInt(initDate[2], 10) + 3));
        }
    }

    public async getTransports(): Promise<Transport[]> {
        this.transports = await this.transportService.getTransports();
        this.bkTransports = this.transports;
        return this.transports;
    }

    public sortTransports(sort) {
        this.transports = this.bkTransports;
        if (sort === 1) {
            return;
        }
        this.transports.sort((a, b) => {
            if (sort === 2) {
                return (a.price > b.price) ? 1 : -1;
            } else {
                return (a.price > b.price) ? -1 : 1;
            }

        });
    }

    onDateSelection(date: NgbDate) {
        if (!this.fromDate && !this.toDate) {
            this.fromDate = date;
        } else if (this.fromDate && !this.toDate && date && date.after(this.fromDate)) {
            this.toDate = date;
        } else {
            this.toDate = null;
            this.fromDate = date;
        }
    }

    isHovered(date: NgbDate) {
        return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
    }

    isInside(date: NgbDate) {
        return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
    }

    isRange(date: NgbDate) {
        return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
    }

    validateInput(currentValue: NgbDate | null, input: string): NgbDate | null {
        const parsed = this.formatter.parse(input);
        return parsed && this.calendar.isValid(NgbDate.from(parsed)) ? NgbDate.from(parsed) : currentValue;
    }

    filterType(type) {
        switch (parseInt(type, 10)) {
            case 1:
                this.hidePlaneFilter = true;
                break;
            case 2:
                this.hideLandFilter = true;
                break;
        }

        this.transports = this.transports.filter(trans => {
            return trans.type !== type;
        });
    }

    selectTransport(transportId) {
        if (this.transports.length === 1) {
            this.selectActionName = 'Seleccionar';
            this.transports = this.bkTransports;
        } else {
            this.selectActionName = 'Elegir Nuevamente';
            this.bkTransports = this.transports;
            this.transports = this.transports.filter(trans => {
                return trans.id === transportId;
            });
            document.documentElement.scrollTop = 450;
        }
    }

    selectAndContinue() {
        const spectacleBook = {
            id: this.transports[0].id,
            type: 'lodging',
            persons: this.nPersons.nativeElement.value,
            name: this.transports[0].name,
            price: this.transports[0].price * this.nPersons.nativeElement.value,
            dateInit: this.getDate(this.fromDate),
            dateEnd: this.getDate(this.toDate)
        };

        localStorage.setItem('lodgeBook', JSON.stringify(spectacleBook));
    }

    getDate(jsonDate) {
        return `${jsonDate['day']}/${jsonDate['month']}/${jsonDate['year']}`;
    }

}
