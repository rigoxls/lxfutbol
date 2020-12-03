import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../interfaces/usuario.interface';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  userPaying: Usuario;
  constructor() { }

  ngOnInit(): void {
    this.userPaying =  JSON.parse(localStorage.getItem('userPaying'));
  }

}
