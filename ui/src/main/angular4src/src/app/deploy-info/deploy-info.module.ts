/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DeployInfoComponent } from "./deploy-info.component";
import { deployInfoRouter } from "./deploy-info.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";
import {IdpEncryptionModule } from "../idpEncryption.module";
import {NgJsonEditorModule} from 'ang-jsoneditor';
import { CollapseModule } from 'ngx-bootstrap/collapse';




@NgModule({
  imports: [
    CommonModule,
    deployInfoRouter,
    NgJsonEditorModule,
    HttpClientModule,
    FormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    IdpEncryptionModule,
    TranslateModule,
    CollapseModule
  ],
  declarations: [DeployInfoComponent]
})

export class DeployInfoModule { }
