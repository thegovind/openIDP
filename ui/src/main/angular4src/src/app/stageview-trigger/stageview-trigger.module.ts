/**
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.”
*
**/
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { StageviewTriggerComponent } from "./stageview-trigger.component";
import { stageviewTriggerRouter } from "./stageview-trigger.router";
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { SafePipeModule } from "../safe-pipe.module";


@NgModule({
  imports: [
    CommonModule,
    stageviewTriggerRouter,
    HttpClientModule,
    FormsModule,
    SafePipeModule,
    TranslateModule
  ],
  declarations: [StageviewTriggerComponent
  ]
})
export class StageviewTriggerModule { }
