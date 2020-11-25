import { Component, OnInit } from '@angular/core';
import { Spectactle } from '../../interfaces/spectacle.interface';
import { HomeService } from '../home-one/home.service';

@Component({
  selector: 'app-blog-one',
  templateUrl: './blog-one.component.html',
  styleUrls: ['./blog-one.component.scss']
})
export class BlogOneComponent implements OnInit {

  spectacles: Spectactle[];
  categoryName : String;

  constructor(private homeService: HomeService) { }

  ngOnInit(): void {
    this.categoryName = JSON.parse(localStorage.getItem('category'));
    this.getSpectacles();
  }

  private async getSpectacles() {
    this.spectacles = await this.homeService.getSpectacle();
    this.spectacles = this.spectacles.filter(ac => ac.type === this.categoryName);
    return this.spectacles;
  }

}
