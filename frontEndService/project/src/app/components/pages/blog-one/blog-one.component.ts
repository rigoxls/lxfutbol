import { Component, OnInit } from '@angular/core';
import { Spectactle } from '../../interfaces/spectacle.interface';
import { HomeService } from '../home-one/home.service';

@Component({
  selector: 'app-blog-one',
  templateUrl: './blog-one.component.html',
  styleUrls: ['./blog-one.component.scss']
})
export class BlogOneComponent implements OnInit {

  activities: Spectactle[];
  categoriaNombre : String;

  constructor(private homeService: HomeService) { }

  ngOnInit(): void {
    this.categoriaNombre = JSON.parse(localStorage.getItem('categoria'));
    this.getActivities();
  }

  private async getActivities() {
      /* RIGO
    this.activities = await this.homeService.getActivities();
    this.activities = this.activities.filter(ac => ac.categoria === this.categoriaNombre);
    return this.activities;
*/
  }

}
