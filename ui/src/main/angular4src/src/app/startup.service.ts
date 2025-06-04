/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map } from "rxjs/operators";

@Injectable()
export class StartupService {
    private _startupData: any;
    constructor(private http: HttpClient) { }

    // This is the method you want to call at bootstrap
    // Important: It should return a Promise
    load(): Promise<any> {

        this._startupData = null;
        let headers = new HttpHeaders({'Authorization': 'Basic '+btoa("idpadmin:idpadmin@123")});
        return this.http
            .get("properties", { headers: headers })
            .pipe(map((res: any) => res))
            .toPromise()
            .then((data: any) => this._startupData = data)
            .catch((err: any) => Promise.resolve());
    }
    getData(): any {
        return this._startupData;
        }
}
