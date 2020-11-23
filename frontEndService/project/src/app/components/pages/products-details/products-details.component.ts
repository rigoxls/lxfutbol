import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-products-details',
    templateUrl: './products-details.component.html',
    styleUrls: ['./products-details.component.scss']
})
export class ProductsDetailsComponent implements OnInit {

    private id: number;
    public spectacle: any;

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.id = parseInt(this.route.snapshot.paramMap.get('id'), 10);

        const spectacles = JSON.parse(localStorage.getItem('spectacles'));
        const spectacle = spectacles.filter(spec => {
            return spec['id'] === this.id;
        });

        this.spectacle = spectacle[0];

    }

}
