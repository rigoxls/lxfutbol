import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {HomeService} from './home.service';
import {Activity} from '../../interfaces/spectacle.interface';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CONFIG} from '../../../../assets/config';

@Component({
    selector: 'app-one-four',
    templateUrl: './home-one.component.html',
    styleUrls: ['./home-one.component.scss']
})
export class HomeOneComponent implements OnInit {
    model: NgbDateStruct;
    spectacles: Activity[];
    bkSpectacle: Activity[];

    @ViewChild('actividades') actividades: ElementRef;

    places = JSON.parse(localStorage.getItem('places'));
    imagePath = CONFIG.imagePath;

    form = new FormGroup({
        searchActivity: new FormControl('', Validators.minLength(2)),
        searchPlace: new FormControl('Lugares'),
        searchCalendar: new FormControl(''),
    });

    constructor(private homeService: HomeService, private router: Router) {
    }

    ngOnInit(): void {
        this.getSpectacle();
    }

    private async getSpectacle(): Promise<Activity[]> {
        this.spectacles = await this.homeService.getSpectacle();
        this.bkSpectacle = this.spectacles;
        return this.spectacles;
    }

    private async getSpectacleByParam(param?: string): Promise<Activity[]> {
        const filterSpectacle = await this.homeService.getSpectacle(param);
        const filteredAct = filterSpectacle.map(el => el[0]);
        /*
        this.spectacles = this.spectacles.filter(act => {
            if (filteredAct.includes(act.idActividad)) {
                return true;
            }
            return false;
        });
         */
        return this.spectacles;
    }

    onSubmit(): void {
        if (!this.form.value.searchActivity) {
            this.getSpectacle();
        } else {
            this.spectacles = this.bkSpectacle;
            localStorage.setItem('fechaViaje', JSON.stringify(this.form.value.searchCalendar));
            this.getSpectacleByParam(this.form.value.searchActivity);
        }
        this.actividades.nativeElement.scrollIntoView();
    }

}
