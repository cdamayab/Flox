import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  private readonly baseUrl = this.resolveBaseUrl();

  constructor(private http: HttpClient) {}

  private resolveBaseUrl(): string {
    const host = typeof window !== 'undefined' ? window.location.hostname : 'localhost';
    const port = '8081';

    if (host === 'localhost' || host === '127.0.0.1') {
      return `http://localhost:${port}`;
    }

    return `http://${host}:${port}`;
  }

  getTableData(tableName: string, filters: any): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/${tableName}`);
  }
}
