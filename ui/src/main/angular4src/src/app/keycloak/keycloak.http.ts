/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpRequest, HttpResponse} from "@angular/common/http";
import {KeycloakService} from "./keycloak.service";
import {Observable} from "rxjs";

@Injectable()
export class KeycloakHttp {
    constructor(private http: HttpClient, private keycloakService: KeycloakService) {
    }

    request(url: string, options?: any): Observable<any> {
        const tokenPromise: Promise<string> = this.keycloakService.getToken();
        
        return new Observable(observer => {
            tokenPromise.then(token => {
                const headers = new HttpHeaders({
                    'Authorization': `Bearer ${token}`
                });
                
                this.http.get(url, { headers, ...options }).subscribe(
                    response => observer.next(response),
                    error => observer.error(error),
                    () => observer.complete()
                );
            });
        });
    }
}

export function keycloakHttpFactory(http: HttpClient, keycloakService: KeycloakService) {
    return new KeycloakHttp(http, keycloakService);
}
