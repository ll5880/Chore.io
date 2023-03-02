import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chore',
  templateUrl: './chore.component.html',
  styleUrls: ['./chore.component.css']
})
export class ChoreComponent {

  chores: any;

  constructor(private http:HttpClient) { }

  ngOnInit() {
    let response = this.http.get("http://localhost:8080/chores");
    console.log(response);
    response.subscribe((data) => this.chores = data);
  }

}
