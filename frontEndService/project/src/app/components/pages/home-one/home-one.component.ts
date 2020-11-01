import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {HomeService} from './home.service';
import {Activity} from '../../interfaces/activity.interface';
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
    activities: Activity[];
    bkActivities: Activity[];

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
        this.getActivities();
    }

    private async getActivities(): Promise<Activity[]> {
        this.activities = await this.homeService.getActivities();
        this.bkActivities = this.activities;
        return this.activities;
    }

    private async getActivitiesByParam(param?: string): Promise<Activity[]> {
        const filterActivities = await this.homeService.getActivities(param);
        const filteredAct = filterActivities.map(el => el[0]);
        this.activities = this.activities.filter(act => {
            if (filteredAct.includes(act.idActividad)) {
                return true;
            }
            return false;
        });
        return this.activities;
    }

    onSubmit(): void {
        if (!this.form.value.searchActivity) {
            this.getActivities();
        } else {
            this.activities = this.bkActivities;
            localStorage.setItem('fechaViaje', JSON.stringify(this.form.value.searchCalendar));
            this.getActivitiesByParam(this.form.value.searchActivity);
        }
        this.actividades.nativeElement.scrollIntoView();
    }

}
