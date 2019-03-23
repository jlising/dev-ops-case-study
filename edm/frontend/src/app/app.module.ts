import { BrowserModule, Title } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

//Third party components
import { ToasterModule, ToasterService } from 'angular2-toaster';
import { PaginationModule } from 'ngx-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';

//App components
import { AppGlobal } from './app.global';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeesComponent } from './components/employees/employees.component';
import { AuthComponent } from './components/auth/auth.component';
import { AjaxLoaderComponent } from './components/ajax-loader/ajax-loader.component';
import { ProfileComponent } from './components/profile/profile.component';
import { EmployeeSearchDialogComponent } from './components/profile/employee-search-dialog.component';
import { EmployeeProfileDialogComponent } from './components/employees/employee-profile-dialog.component';
import { EmployeeW3DialogComponent } from './components/employees/employee-w3-dialog.component';
import { MeetingsComponent } from './components/meetings/meetings.component';
import { MeetingEditComponent } from './components/meetings/meeting-edit.component';
import { MeetingAddComponent } from './components/meetings/meeting-add.component';
import { ActionItemsComponent } from './components/action-items/action-items.component';
import { MeetingEmployeeSearchDialogComponent } from './components/meetings/meeting-employee-search-dialog.component';

// Templates
import { HeaderComponent } from './components/common/header.component';
import { FooterComponent } from './components/common/footer.component';
import { PageNotFoundComponent } from './components/common/page-not-found.component';
import { DefaultPageComponent } from './components/common/default-page.component';

// Services
import { EmployeesService } from './components/employees/employees.service';
import { MyHttpInterceptor } from './components/common/my.http.interceptor';
import { EmployeeBasicInformationService } from './components/profile/employee-basic-information.service';
import { EmployeeContactDetailsService } from './components/profile/employee-contact-details.service';
import { EmployeeProjectService } from './components/profile/employee-project.service';
import { EmployeeReimbursementService } from './components/profile/employee-reimbursement.service';
import { MeetingsService } from './components/meetings/meetings.service';
import { ActionItemsService } from './components/action-items/action-items.service';

import { AuthService } from './components/auth/auth.service';
import { AjaxLoaderService } from './components/ajax-loader/ajax-loader.service';
import { AuthGuardService } from './components/auth/auth-guard.service';
import { ActionItemDialogComponent } from './components/action-items/action-item-dialog.component';
import { AssignActionItemDialogComponent } from './components/action-items/assign-action-item-dialog.component';


@NgModule({
  declarations: [
   HeaderComponent,
   FooterComponent,
   PageNotFoundComponent,
   DefaultPageComponent,
   EmployeesComponent,

   AuthComponent,
   AjaxLoaderComponent,
   AppComponent,
   ProfileComponent,
   EmployeeSearchDialogComponent,
   EmployeeProfileDialogComponent,
   EmployeeW3DialogComponent,

   MeetingsComponent,
   ActionItemsComponent,
   MeetingEditComponent,
   MeetingAddComponent,
   MeetingEmployeeSearchDialogComponent,
   ActionItemDialogComponent,
   AssignActionItemDialogComponent
  ],
  entryComponents: [ EmployeeSearchDialogComponent,
                     EmployeeProfileDialogComponent,
                     EmployeeW3DialogComponent,
                     MeetingEmployeeSearchDialogComponent,
                     ActionItemDialogComponent,
                     AssignActionItemDialogComponent],
  imports: [
     BrowserModule, //Configure browser-based application to transition from a server-rendered app. If not imported, ngIf, routings won't work.
     BrowserAnimationsModule,
     AppRoutingModule, //Routing
     HttpClientModule,
     FormsModule,
     MatDialogModule,
     MatDatepickerModule,
     MatNativeDateModule,
     PaginationModule.forRoot(),
     ToasterModule
  ],
  providers: [  Title,
                AppGlobal,
                EmployeesService,
                EmployeeBasicInformationService,
                EmployeeContactDetailsService,
                EmployeeProjectService,
                EmployeeReimbursementService,
                MeetingsService,
                ActionItemsService,
                AuthGuardService, AuthService, ToasterService, AjaxLoaderService,
             				{ provide: LocationStrategy, useClass: HashLocationStrategy},
                             {
                                     provide: HTTP_INTERCEPTORS,
                                     useClass: MyHttpInterceptor,
                                     multi: true,
                             }
             			 ],
  bootstrap: [AppComponent]
})
export class AppModule { }
