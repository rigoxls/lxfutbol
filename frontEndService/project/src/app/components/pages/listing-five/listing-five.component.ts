import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { Spectactle } from '../../interfaces/spectacle.interface';
import { HomeService } from '../home-one/home.service';

@Component({
  selector: 'app-listing-five',
  templateUrl: './listing-five.component.html',
  styleUrls: ['./listing-five.component.scss']
})
export class ListingFiveComponent implements OnInit {

  spectacles: Spectactle[];
  cityName: string;

  constructor(private homeService: HomeService) {
  }

  ngOnInit(): void {
    this.cityName = JSON.parse(localStorage.getItem('city'));
    this.getSpectacles();
  }

  private async getSpectacles(): Promise<Spectactle[]> {
    this.spectacles = await this.homeService.getSpectacle();
    this.spectacles = this.spectacles.filter(ac => ac.city === this.cityName);
    
    return this.spectacles;

  }

}
