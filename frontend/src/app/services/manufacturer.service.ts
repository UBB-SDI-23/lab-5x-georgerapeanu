import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ManufacturerDTO } from '../dto/ManufacturerDTO';
import { Observable } from 'rxjs';
import { GenericPageDTO } from '../dto/GenericPageDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {

  constructor(private http: HttpClient) { }

  getAllManufacturers(pageNumber: number, pageSize: number): Observable<GenericPageDTO<ManufacturerDTO>> {
    return this.http.get<GenericPageDTO<ManufacturerDTO>>(environment.apiURL + "/manufacturers" + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
  }

  getManufacturerById(id: number): Observable<ManufacturerDTO> {
    return this.http.get<ManufacturerDTO>(environment.apiURL + "/manufacturers/" + id.toString());
  }

  editManufacturer(manufacturer: ManufacturerDTO): Observable<any>{
    return this.http.patch(environment.apiURL + "/manufacturers/" + manufacturer.id.toString(), manufacturer);
  }

  createManufacturer(manufacturer: ManufacturerDTO): Observable<any>{
    return this.http.post(environment.apiURL + "/manufacturers", manufacturer);
  }

  deleteManufacturer(id: number): Observable<any> {
    console.log(environment.apiURL + "/manufacturers/" + id.toString());
    return this.http.delete(environment.apiURL + "/manufacturers/" + id.toString());
  }
}
