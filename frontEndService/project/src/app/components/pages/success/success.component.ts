import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../interfaces/usuario.interface';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.scss']
})
export class SuccessComponent implements OnInit {

  userPaying: Usuario;
  constructor() { }

  ngOnInit(): void {

    this.userPaying =  JSON.parse(localStorage.getItem('userPaying'));
  }

}
