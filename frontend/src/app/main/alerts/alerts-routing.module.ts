import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AlertDialogComponent } from "./alert-dialog/alert-dialog.component";

const routes: Routes = [
  {
    path: "",
    component: AlertDialogComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AlertsRoutingModule {}