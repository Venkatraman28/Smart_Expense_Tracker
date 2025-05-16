import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
  private http = inject(HttpClient);
  users: User[] = [];


  getAllUsers() {
    this.http.get<User[]>('http://localhost:8080/api/v1/users').subscribe({
      next: (res) => {
        console.log(JSON.stringify(res));
        
        this.users = res
      }, 
      error: (err) => {
        console.error("Error: " + err);
      }
    })
  }
}

interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  createdDate: Date;
  lastModifiedDate: Date;
}