import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutViewComponent } from './about-view.component';
import { aboutViewRouter } from './about-view.router';
import { TranslateModule,  TranslateLoader } from "@ngx-translate/core";
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { SafePipeModule } from '../safe-pipe.module';


@NgModule({
  imports: [
    CommonModule,
    aboutViewRouter,
	TranslateModule,
	HttpClientModule,
	FormsModule,
	SafePipeModule
  ],
  declarations: [AboutViewComponent
				 
				 ]
})
export class AboutViewModule { }
