/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { CreateApplicationComponent } from "./create-application.component";
import { createAppRouter } from "./create-application.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { CollapseModule } from 'ngx-bootstrap/collapse';


@NgModule({
  imports: [
    CommonModule,
    createAppRouter,
    HttpClientModule,
    FormsModule,
    TranslateModule,
    CollapseModule
  ],
  declarations: [CreateApplicationComponent]
})
export class CreateApplicationModule { }
