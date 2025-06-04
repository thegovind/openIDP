/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { firstValueFrom } from "rxjs";
import { IdpdataService } from "./idpdata.service";
@Injectable()
export class SubscriptionService {
  subscriptionUrl: String = "https://dummyuser:8090/subscription";
  constructor(private http: HttpClient,
    private IdpdataService: IdpdataService,
    private _cookieService: CookieService
    ) {
        // IdpdataService.subscriptionServerURL=this.subscriptionUrl;
    }
  getActiveServices(): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/service/active";
    let headers = new HttpHeaders();
    let cookie;
    headers = headers.set("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers = headers.set("Authorization", "Bearer " + cookie);
    const options = { headers: headers };
    const data = "";
    return firstValueFrom(this.http.post(url, data, options))
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
  getAllSubscriptions(): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/active";
    let headers = new HttpHeaders();
    let cookie;
    headers = headers.set("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers = headers.set("Authorization", "Bearer " + cookie);
    const options = { headers: headers };
    const data = "";
    return firstValueFrom(this.http.post(url, data, options))
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
  validateLicense(licenseKey: String): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/validate";
    let headers = new HttpHeaders();
    let cookie;
    headers = headers.set("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers = headers.set("Authorization", "Bearer " + cookie);
    const options = { headers: headers };
    const data = licenseKey;
    return firstValueFrom(this.http.post(url, data, options))
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }

  addLicense(licenseKey: String): Promise<any> {
    const url = this.IdpdataService.subscriptionServerURL + "/licenseService/license/add";
    let headers = new HttpHeaders();
    let cookie;
    headers = headers.set("Content-type", "application/json");
    if (this._cookieService.get("access_token")) {
        cookie = this._cookieService.get("access_token");
    }
    headers = headers.set("Authorization", "Bearer " + cookie);
    const options = { headers: headers };
    const data = licenseKey;
    return firstValueFrom(this.http.put(url, data, options))
        .then(response => {
            return response; })
        .catch(Error => console.log(Error));
  }
}
