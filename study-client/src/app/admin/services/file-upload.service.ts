import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
import 'rxjs/Rx';
import { map } from 'rxjs/operators';
import { strictEqual } from 'assert';


@Injectable()
export class FileUploadService {

    private baseUrl: string = environment.BASE_URL + 'questionnaire'

    constructor(private _http: HttpClient) {

    }

    uploadFile(formData: FormData) {
        let headers = {
            headers: {
                // 'Content-Type': 'multipart/form-data',
                'Accept': 'application/json',
                'enctype':"multipart/form-data"
            }
        };
        return this._http.post(this.baseUrl + "/upload-solution", formData, headers).pipe(
            map(response => {return response}));
    }
}