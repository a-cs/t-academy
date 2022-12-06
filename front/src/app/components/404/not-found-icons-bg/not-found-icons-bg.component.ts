import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-not-found-icons-bg',
  templateUrl: './not-found-icons-bg.component.html',
  styleUrls: ['./not-found-icons-bg.component.css']
})
export class NotFoundIconsBgComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log(Math.floor(Math.random() * 2))
  }


}
