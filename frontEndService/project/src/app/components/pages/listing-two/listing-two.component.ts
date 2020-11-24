import {Component, OnInit} from '@angular/core';
import {Spectactle} from '../../interfaces/spectacle.interface';
import {Lodge} from '../../interfaces/lodge.interface';
import {LodgeService} from './lodge.service';
import {NgbDate, NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';


@Component({
    selector: 'app-listing-two',
    templateUrl: './listing-two.component.html',
    styleUrls: ['./listing-two.component.scss']
})
export class ListingTwoComponent implements OnInit {

    lodges: Lodge[];
    bkLodges: Lodge[];

    hoveredDate: NgbDate | null = null;

    fromDate: NgbDate | null;
    toDate: NgbDate | null;

    constructor(private lodgeService: LodgeService, private calendar: NgbCalendar, public formatter: NgbDateParserFormatter) {
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
    }

    private async getLodges(): Promise<Lodge[]> {
        this.lodges = await this.lodgeService.getLodges();
        this.bkLodges = this.lodges;
        return this.lodges;
    }

    private sortLodges(sort) {
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

}
