import { NgModule } from '@angular/core';
import { OntimizeWebModule } from 'ontimize-web-ngx';

import { SharedModule } from '../shared/shared.module';
import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './main.component';
import { AlertsComponent } from './alerts/alerts.component';
import { AlertDialogComponent } from './alerts/alert-dialog/alert-dialog.component';


@NgModule({
  imports: [
    SharedModule,
    OntimizeWebModule,
    MainRoutingModule
  ],
  declarations: [
    MainComponent,
    AlertsComponent,
    AlertDialogComponent,
  ]
})
export class MainModule { }
