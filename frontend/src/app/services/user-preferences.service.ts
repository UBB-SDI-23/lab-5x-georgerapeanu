import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class UserPreferencesService {

  constructor(private cookieService: CookieService) { 

  }

  setPageSizePreference(pageSize: number) {
    this.cookieService.set("page-size", pageSize.toString());
  }

  getPageSizePreferences(): number | null {
    if(!this.cookieService.check("page-size")){
      return null;
    }
    return parseInt(this.cookieService.get("page-size"));
  }

  clearAll() {
    this.cookieService.delete("page-size");
  }
}
