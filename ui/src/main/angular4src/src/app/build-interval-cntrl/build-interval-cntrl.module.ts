import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BuildIntervalCntrlComponent } from "./build-interval-cntrl.component";
import {BuildScheduleRouter} from "./build-interval-cntrl.router";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";
import { DateTimePickerModule } from "ng-pick-datetime";

import { TreeviewModule } from "ngx-treeview";
import { TriggerModule } from "../triggerPipeline/triggerPipeline.module";
import { CollapseModule } from 'ngx-bootstrap/collapse';




@NgModule({
    declarations: [
    BuildIntervalCntrlComponent
    ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    AngularMultiSelectModule,
    DateTimePickerModule,
    TreeviewModule.forRoot(),
    BuildScheduleRouter,
    TranslateModule,
    TriggerModule,
    CollapseModule
  ],
  bootstrap: [BuildIntervalCntrlComponent]
})
export class BuildIntervalModule { }
