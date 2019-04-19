import {Routes} from '@angular/router';
import { ManagerLoginComponent } from './components/manager-login/manager-login.component';
import { EmployeeLoginComponent } from './components/employee-login/employee-login.component';
import { HelpPageComponent } from './components/help-page/help-page.component';

/*
Routing is a feature that Angular uses to achieve true single
application format.

Routing entails setting up a page to be injected through
the URL that we are connected to. THe URLS themselves dont point
to seperate files to be displayed due to the fact that this would 
be a multipage application
*/

export const appRoutes: Routes = [
{
    path: 'managerlogin',
    component: ManagerLoginComponent
},
{
    path:'employeelogin',
    component:EmployeeLoginComponent
},
{
    path:'helpPage',
    component:HelpPageComponent
}
]