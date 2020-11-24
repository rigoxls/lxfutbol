import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-spectacle-details',
    templateUrl: './spectacle-details.component.html',
    styleUrls: ['./spectacle-details.component.scss']
})
export class SpectacleDetailsComponent implements OnInit {

    @ViewChild('nPersons') nPersons: ElementRef;

    private id: number;
    public spectacle: any;
    public errors = true;

    public zone = 1;

    public seats: any;

    public bookedSeats = [];

    public northSeats = [
        [Array(10).fill('').map((x, i) => `CA${i}`)], [Array(10).fill('').map((x, i) => `CB${i}`)],
        [Array(10).fill('').map((x, i) => `CC${i}`)], [Array(10).fill('').map((x, i) => `CD${i}`)],
        [Array(10).fill('').map((x, i) => `CE${i}`)], [Array(10).fill('').map((x, i) => `CF${i}`)],
        [Array(10).fill('').map((x, i) => `CG${i}`)], [Array(10).fill('').map((x, i) => `CH${i}`)],
        [Array(10).fill('').map((x, i) => `CI${i}`)], [Array(10).fill('').map((x, i) => `CJ${i}`)]
    ];

    public westSeats = [
        [Array(10).fill('').map((x, i) => `WK${i}`)], [Array(10).fill('').map((x, i) => `WL${i}`)],
        [Array(10).fill('').map((x, i) => `WM${i}`)], [Array(10).fill('').map((x, i) => `WN${i}`)],
        [Array(10).fill('').map((x, i) => `WO${i}`)], [Array(10).fill('').map((x, i) => `WP${i}`)],
        [Array(10).fill('').map((x, i) => `WQ${i}`)], [Array(10).fill('').map((x, i) => `WR${i}`)],
        [Array(10).fill('').map((x, i) => `WS${i}`)], [Array(10).fill('').map((x, i) => `WT${i}`)]
    ];

    public eastSeats = [
        [Array(10).fill('').map((x, i) => `U${i}`)], [Array(10).fill('').map((x, i) => `V${i}`)],
        [Array(10).fill('').map((x, i) => `W${i}`)], [Array(10).fill('').map((x, i) => `X${i}`)],
        [Array(10).fill('').map((x, i) => `Y${i}`)], [Array(10).fill('').map((x, i) => `Z${i}`)],
        [Array(10).fill('').map((x, i) => `AA${i}`)], [Array(10).fill('').map((x, i) => `AB${i}`)],
        [Array(10).fill('').map((x, i) => `AC${i}`)], [Array(10).fill('').map((x, i) => `AD${i}`)]
    ];

    public southSeats = [
        [Array(10).fill('').map((x, i) => `BB${i}`)], [Array(10).fill('').map((x, i) => `BA${i}`)],
        [Array(10).fill('').map((x, i) => `CB${i}`)], [Array(10).fill('').map((x, i) => `DB${i}`)],
        [Array(10).fill('').map((x, i) => `EB${i}`)], [Array(10).fill('').map((x, i) => `FB${i}`)],
        [Array(10).fill('').map((x, i) => `GB${i}`)], [Array(10).fill('').map((x, i) => `HB${i}`)],
        [Array(10).fill('').map((x, i) => `IB${i}`)], [Array(10).fill('').map((x, i) => `JB${i}`)]
    ];

    constructor(private route: ActivatedRoute, private router: Router) {
        this.seats = this.northSeats;
    }

    ngOnInit(): void {
        this.id = parseInt(this.route.snapshot.paramMap.get('id'), 10);

        const spectacles = JSON.parse(localStorage.getItem('spectacles'));
        const spectacle = spectacles.filter(spec => {
            return spec['id'] === this.id;
        });

        this.spectacle = spectacle[0];
    }

    changeZone(zoneId) {
        this.zone = zoneId;

        switch (zoneId) {
            case 1:
                this.seats = this.northSeats;
                break;
            case 2:
                this.seats = this.westSeats;
                break;
            case 3:
                this.seats = this.eastSeats;
                break;
            case 4:
                this.seats = this.southSeats;
                break;
        }
    }

    countSeats(seat) {
        if (this.bookedSeats.includes(seat)) {
            this.bookedSeats = [...new Set(this.bookedSeats)];
            this.bookedSeats = this.bookedSeats.filter(value => {
                return value !== seat;
            });
        } else {
            this.bookedSeats.push(seat);
            this.bookedSeats = [...new Set(this.bookedSeats)];
        }

        this.errors = (!this.bookedSeats.length);

        this.nPersons.nativeElement.value = (this.bookedSeats.length) ? this.bookedSeats.length : 1;
    }

    selectAndContinue() {
        if (!this.bookedSeats.length) {
            this.errors = true;
        } else {
            this.errors = false;
            const spectacleBook = {
                id: this.id,
                seats: this.bookedSeats,
                name: this.spectacle.description,
                price: this.spectacle.price * this.bookedSeats.length,
                date: this.spectacle.date
            };
            localStorage.setItem('spectacleBook', JSON.stringify(spectacleBook));
            this.router.navigate(['/lodge-details']);

        }
    }
}
