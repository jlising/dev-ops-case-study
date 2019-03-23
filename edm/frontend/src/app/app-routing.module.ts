import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { EmployeesComponent } from './components/employees/employees.component';
import { AuthComponent } from './components/auth/auth.component';
import { AuthGuardService } from './components/auth/auth-guard.service';
import { PageNotFoundComponent } from './components/common/page-not-found.component';
import { DefaultPageComponent } from './components/common/default-page.component';
import { ProfileComponent } from './components/profile/profile.component';

import { MeetingsComponent } from './components/meetings/meetings.component';
import { MeetingEditComponent } from './components/meetings/meeting-edit.component';
import { MeetingAddComponent } from './components/meetings/meeting-add.component';

import { ActionItemsComponent } from './components/action-items/action-items.component';

const routes: Routes = [
    { path: '', component: DefaultPageComponent, pathMatch: 'full' },
    { path: 'profile',  component: ProfileComponent, canActivate: [AuthGuardService], pathMatch: 'full'  },
    { path: 'employees',  component: EmployeesComponent, canActivate: [AuthGuardService], pathMatch: 'full' }, // match full in searching path so accounts/:id is not included
    { path: 'meetings',  component: MeetingsComponent, canActivate: [AuthGuardService], pathMatch: 'full' },
    { path: 'meetings/add',  component: MeetingAddComponent, canActivate: [AuthGuardService], pathMatch: 'full' },
    { path: 'meetings/:id',  component: MeetingEditComponent,
         children: [
              { path: '', redirectTo: 'meetings', pathMatch: 'full' },
              { path: 'edit', component: MeetingEditComponent }
            ],
         canActivate: [AuthGuardService]},

    { path: 'action-items',  component: ActionItemsComponent, canActivate: [AuthGuardService], pathMatch: 'full' },

    { path: 'login',  component: AuthComponent},
    { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
