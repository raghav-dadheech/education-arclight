import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { ForgetPasswordComponent } from './user/forget-password/forget-password.component'
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { DashboardComponent } from './admin/components/dashboard/dashboard.component';
import { SidebarComponent } from './admin/components/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditorComponent } from './admin/components/editor/editor.component';
import { HomeScreenComponent } from './admin/components/home-screen/home-screen.component';
import { JwtInterceptor } from './services/JwtInterceptor'
import { HttpClientModule , HTTP_INTERCEPTORS} from '@angular/common/http';
import { FileUploadService } from  './admin/services/file-upload.service';
import { QuestionService } from './admin/services/question.service';
import { ExamSharingService } from './admin/services/exam-sharing.service';
import { UserService } from './services/user.service'
import { QuestionsListComponent } from './admin/components/questions-list/questions-list.component';
import {MathJaxModule} from 'ngx-mathjax';
import { FlexLayoutModule } from '@angular/flex-layout';
// import { MaterialModule } from "./material.module";
import { AlertsComponent } from "./alerts/alerts.component" 
import { DataTablesModule } from 'angular-datatables';
import { QuestionsTrashComponent } from './admin/components/questions-trash/questions-trash.component'
import { ExamComponent } from './admin/components/exam/exam.component'
import { ExamPaperComponent } from './admin/components/exam-paper/exam-paper.component'
import { PapersComponent } from './admin/components/papers/papers.component'
import { PaperQuestionsComponent } from './admin/components/paper-questions/paper-questions.component'
import { ToastrModule } from 'ngx-toastr';
import { AppConstants } from './services/AppConstants';
import { ResetPasswordComponent } from './user/reset-password/reset-password.component'
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { UserRoleDirective } from './directives/user-role.directive'
import { InvitationAddComponent } from './admin/components/invitation-add/invitation-add.component';
import { CommonService } from './admin/services/common.service';
import { InvitationsListComponent } from './admin/components/invitations-list/invitations-list.component';
import { UserProfileComponent } from './admin/components/user-profile/user-profile.component';

export interface SchemaMetadata { name: string; }
export const NO_ERRORS_SCHEMA: SchemaMetadata = {
  name: 'no-errors-schema'
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ForgetPasswordComponent,
    DashboardComponent,
    SidebarComponent,
    EditorComponent,
    HomeScreenComponent,
    QuestionsListComponent,
    AlertsComponent,
    QuestionsTrashComponent,
    ExamComponent,
    ExamPaperComponent,
    PapersComponent,
    PaperQuestionsComponent,
    ResetPasswordComponent,
    ChangePasswordComponent,
    UserRoleDirective,
    InvitationAddComponent,
    InvitationsListComponent,
    UserProfileComponent
  ],
  imports: [
    // MaterialModule,
    MathJaxModule.forRoot({
      version: '2.7.5',
      config: 'TeX-AMS_HTML',
      hostname: 'cdnjs.cloudflare.com'
    }),
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    AngularFontAwesomeModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    DataTablesModule,
    ToastrModule.forRoot()
  ],
  providers: [
    FileUploadService,
    QuestionService,
    ExamSharingService,
    UserService,
    CommonService,
    AppConstants,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  schemas: [
    NO_ERRORS_SCHEMA
  ],
  entryComponents: [AlertsComponent]
})
export class AppModule { }
