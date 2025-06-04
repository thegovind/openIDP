import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ApproveReleaseComponent } from "./approve-release.component";
import { approveReleaseRouter } from "./approve-release.router";
import { TranslateModule } from "@ngx-translate/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AngularMultiSelectModule } from "angular2-multiselect-dropdown";

@NgModule({
  imports: [
    CommonModule,
	approveReleaseRouter,
	TranslateModule,
	HttpClientModule,
	FormsModule,
	AngularMultiSelectModule
  ],
  declarations: [ApproveReleaseComponent]
})
export class ApproveReleaseModule { }
