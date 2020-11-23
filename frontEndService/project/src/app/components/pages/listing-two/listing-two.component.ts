import {Component, OnInit} from '@angular/core';
import {Spectactle} from '../../interfaces/spectacle.interface';
import {Lodge} from '../../interfaces/lodge.interface';
import {LodgeService} from './lodge.service';

@Component({
    selector: 'app-listing-two',
    templateUrl: './listing-two.component.html',
    styleUrls: ['./listing-two.component.scss']
})
export class ListingTwoComponent implements OnInit {

    lodges: Lodge[];

    constructor(private lodgeService: LodgeService) {
        this.getLodges();
    }

    ngOnInit(): void {
    }

    private async getLodges(): Promise<Lodge[]> {
        this.lodges = await this.lodgeService.getLodges();
        return this.lodges;
    }

}
