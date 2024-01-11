import { AlertsRoutingModule } from './alerts-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OntimizeWebModule } from 'ontimize-web-ngx';
import { MatDialogModule } from '@angular/material/dialog';
import { AlertDialogComponent } from './alert-dialog/alert-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    OntimizeWebModule,
    MatDialogModule,
    AlertsRoutingModule
  ],
  declarations: [AlertDialogComponent]
})
export class AlertsModule { }