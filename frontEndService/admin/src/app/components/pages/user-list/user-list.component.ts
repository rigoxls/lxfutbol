import { Component, OnInit } from '@angular/core';
import {ListUserService} from '../user-list/list-user.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

    public users = [];
    public userId = null;

    public showMessage = false;
    public message = '';
    public errorMessage = false;

    constructor(
        private listUserService: ListUserService,
        private modalService: NgbModal) {
        this.getUsers();
    }

    ngOnInit(): void {
    }

    async getUsers() {
        this.users = await this.listUserService.getUsers();
        /*this.users.sort((a, b) => {
            return (a['nombreActividad'].toLowerCase() > b['nombreActividad'].toLowerCase()) ? 1 : -1;
        });*/
    }

    open(content, userId) {
        this.userId = userId;
        this.modalService.open(content);
    }

    async delete() {
        const delResult = await this.listUserService.deleteUser(this.userId);
        if (delResult['status']) {
            this.showMessage = true;
            this.message = 'Usuario eliminado exitosamente';
            window.scrollTo(0, 0);
            this.getUsers();
        } else {
            this.showMessage = true;
            this.errorMessage = true;
            this.message = 'Ha ocurrido un error eliminando el usuario';
            window.scrollTo(0, 0);
        }
        this.modalService.dismissAll();
    }

}
