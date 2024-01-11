import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-alert-dialog',
  template: `
    <h1 mat-dialog-title>{{ data.title }}</h1>
    <div mat-dialog-content>
      {{ data.message }}
    </div>
    <div mat-dialog-actions>
      <button mat-button [mat-dialog-close]="'OK'">OK</button>
    </div>
  `,
})
export class AlertDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}