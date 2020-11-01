import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { Activity } from '../../interfaces/activity.interface';
import { HomeService } from '../home-one/home.service';

@Component({
  selector: 'app-listing-five',
  templateUrl: './listing-five.component.html',
  styleUrls: ['./listing-five.component.scss']
})
export class ListingFiveComponent implements OnInit {
  
  activities: Activity[];
  place: String;

  constructor(private homeService: HomeService) {
  }

  ngOnInit(): void {
    this.place = JSON.parse(localStorage.getItem('placesName'));
    this.getActivities();
  }

  private async getActivities(): Promise<Activity[]> {
    this.activities = await this.homeService.getActivities();   
    this.activities = this.activities.filter(ac => ac.nombreDepartamento === this.place);
    return this.activities;

  }

}
