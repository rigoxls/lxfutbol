import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Lodge} from '../../interfaces/lodge.interface';
import {LodgeService} from './lodge.service';
import {NgbDate, NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-lodge-details',
    templateUrl: './lodge-details.component.html',
    styleUrls: ['./lodge-details.component.scss']
})
export class LodgeDetailsComponent implements OnInit {

    @ViewChild('nPersons') nPersons: ElementRef;
    @ViewChild('nRooms') nRooms: ElementRef;

    public hideDuplexFilter = false;
    public hideSingleFilter = false;
    public hideFamiliarFilter = false;
    public selectActionName = 'Seleccionar';

    lodges: Lodge[] = [];
    bkLodges: Lodge[];

    hoveredDate: NgbDate | null = null;

    fromDate: NgbDate | null;
    toDate: NgbDate | null;

    constructor(private lodgeService: LodgeService, private calendar: NgbCalendar,
                public formatter: NgbDateParserFormatter, private router: Router) {
        this.getLodges();
        setTimeout(() => {
            const lis = document.getElementsByClassName('option');
            // @ts-ignore
            for (const li of lis) {
                li.addEventListener('click', (data) => {
                    const text = data.target.innerText;

                    switch (text) {
                        case 'Ordenar: Destacados':
                            this.sortLodges(1);
                            break;
                        case 'Ordenar: Precio mas bajo':
                            this.sortLodges(2);
                            break;
                        case 'Ordenar: Precio mas alto':
                            this.sortLodges(3);
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

    public async getLodges(byDates = false): Promise<Lodge[]> {
        if (byDates) {
            this.lodges = await this.lodgeService.getLodges({fromDate: this.getDate(this.fromDate), toDate: this.getDate(this.toDate)});
        } else {
            this.lodges = await this.lodgeService.getLodges();
        }

        this.bkLodges = this.lodges;
        return this.lodges;
    }

    public sortLodges(sort) {
        this.lodges = this.bkLodges;
        if (sort === 1) {
            return;
        }
        this.lodges.sort((a, b) => {
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
        switch (type) {
            case 'Duplex':
                this.hideDuplexFilter = true;
                break;
            case 'Single':
                this.hideSingleFilter = true;
                break;
            case 'Familiar':
                this.hideFamiliarFilter = true;
                break;
        }

        this.lodges = this.lodges.filter(lodge => {
            return lodge.type !== type;
        });
    }

    selectLodge(lodgeNumber) {
        if (this.lodges.length === 1) {
            this.selectActionName = 'Seleccionar';
            this.lodges = this.bkLodges;
        } else {
            this.selectActionName = 'Elegir Nuevamente';
            this.bkLodges = this.lodges;
            this.lodges = this.lodges.filter(lodge => {
                return lodge.number === lodgeNumber;
            });
            document.documentElement.scrollTop = 450;
        }
    }

    selectAndContinue() {
        if (this.lodges.length === 1) {
            const spectacleBook = {
                id: this.lodges[0].number,
                type: 'lodging',
                persons: this.nPersons.nativeElement.value,
                nRooms: this.nRooms.nativeElement.value,
                name: this.lodges[0].name,
                price: this.lodges[0].price * this.nPersons.nativeElement.value,
                dateInit: this.getDate(this.fromDate),
                dateEnd: this.getDate(this.toDate),
            };

            localStorage.setItem('lodgeBook', JSON.stringify(spectacleBook));
        } else {
            localStorage.removeItem('lodgeBook');
        }

        this.router.navigate(['/transport-details']);
    }

    getDate(jsonDate) {
        return `${jsonDate['year']}-${jsonDate['month']}-${jsonDate['day']}`;
    }

}
