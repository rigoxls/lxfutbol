import {Component, OnInit} from '@angular/core';
import {ListActivityService} from './list-activity.service';
import {NgbModalConfig, NgbModal} from '@ng-bootstrap/ng-bootstrap';


@Component({
    selector: 'app-list-activity',
    templateUrl: './list-activity.component.html',
    styleUrls: ['./list-activity.component.scss'],
    providers: [NgbModalConfig, NgbModal]
})
export class ListActivityComponent implements OnInit {

    public activities = [];
    public activityId = null;

    public showMessage = false;
    public message = '';
    public errorMessage = false;

    constructor(
        private listActivityService: ListActivityService,
        private modalService: NgbModal) {
        this.getActivities();
    }

    ngOnInit(): void {
    }

    async getActivities() {
        this.activities = await this.listActivityService.getActivities();
        this.activities.sort((a, b) => {
            return (a['nombreActividad'].toLowerCase() > b['nombreActividad'].toLowerCase()) ? 1 : -1;
        });
    }

    open(content, activityId) {
        this.activityId = activityId;
        this.modalService.open(content);
    }

    async delete() {
        const delResult = await this.listActivityService.deleteActivity(this.activityId);
        if (delResult['status']) {
            this.showMessage = true;
            this.message = 'Actividad eliminada exitosamente';
            window.scrollTo(0, 0);
            this.getActivities();
        } else {
            this.showMessage = true;
            this.errorMessage = true;
            this.message = 'Ha ocurrido un error eliminando la actividad';
            window.scrollTo(0, 0);
        }
        this.modalService.dismissAll();
    }
}
