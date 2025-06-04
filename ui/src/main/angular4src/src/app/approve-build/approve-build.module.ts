/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ApproveBuildComponent } from "./approve-build.component";
import { approvebuildRouter } from "./approve-build.router";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { TranslateModule } from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule,
    approvebuildRouter,
    HttpClientModule,
    FormsModule,
    AngularMultiSelectModule,
    TranslateModule
  ],
  declarations: [ApproveBuildComponent]
})
export class ApproveBuildModule { }
