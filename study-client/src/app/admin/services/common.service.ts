import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { strictEqual } from 'assert';

@Injectable()
export class CommonService {

    private baseUrl: string = environment.BASE_URL + 'user'
    constructor(private _http: HttpClient) {

    }

    listOrganisations() {
        return this._http.get(this.baseUrl + "/list-organisations").pipe(
            map(response => {return response}));
    }

    listInvitations() {
        return this._http.get(this.baseUrl + "/list-invitations").pipe(
            map(response => {return response}));
    }

    listUserRoles() {
        return this._http.get(this.baseUrl + "/list-userroles").pipe(
            map(response => {return response}));
    }

    invite(data) {
        let headers = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return this._http.post(this.baseUrl + "/invite",JSON.stringify(data), headers).pipe(
            map(response => {return response}));
    }

    cancelInvitation(invitationId:BigInteger) {
        return this._http.get(this.baseUrl + "/cancel-invitation",{
            params: {
                invitationId: invitationId.toString()
            }
          }).pipe(
            map(response => {return response}));
    }
    
    loadCountriesJson() {
        return this._http.get("assets/data/cities.json").pipe(
            map(response => {return response}));
    }
    
}