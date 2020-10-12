import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './admin/components/dashboard/dashboard.component';
import { LoginComponent } from './user/login/login.component';
import { EditorComponent } from './admin/components/editor/editor.component';
import { HomeScreenComponent } from './admin/components/home-screen/home-screen.component';
import { QuestionsListComponent } from './admin/components/questions-list/questions-list.component';
import { QuestionsTrashComponent } from './admin/components/questions-trash/questions-trash.component';
import { ExamComponent } from './admin/components/exam/exam.component';
import { ExamPaperComponent } from './admin/components/exam-paper/exam-paper.component';
import { PapersComponent } from './admin/components/papers/papers.component';
import { PaperQuestionsComponent } from './admin/components/paper-questions/paper-questions.component';
import { ForgetPasswordComponent } from './user/forget-password/forget-password.component';
import { RegisterComponent } from './user/register/register.component';
import { ResetPasswordComponent } from './user/reset-password/reset-password.component';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { AuthGuardService } from './services/auth-guard.service';
import { InvitationAddComponent } from './admin/components/invitation-add/invitation-add.component';
import { InvitationsListComponent } from './admin/components/invitations-list/invitations-list.component';
import { UserProfileComponent } from './admin/components/user-profile/user-profile.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forget-password', component: ForgetPasswordComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'reset-password', component: ResetPasswordComponent},
  // { path: '**', component: DashboardComponent,
  //   children: [
  //     { path: '', component: HomeScreenComponent, outlet: 'dashboard-outlet' }
  //   ]
  // },
  { path: 'dashboard', component: DashboardComponent, 
    children: [
      { path: '', component: HomeScreenComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService] },
      // { path: 'home', component: HomeScreenComponent, outlet: 'dashboard-outlet'  },
      // { path: 'editor', component: EditorComponent, outlet: 'dashboard-outlet'}
    ]
  },
  { path: 'home', component: DashboardComponent, 
    children: [
      { path: '', component: HomeScreenComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService]  }
    ]
  },
  { path: 'change-password', component: DashboardComponent, 
    children: [
      { path: '', component: ChangePasswordComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService]  }
    ]
  },
  { path: 'editor', component: DashboardComponent, 
    children: [
      { path: '', component: EditorComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'editor/:questionId', component: DashboardComponent, 
    children: [
      { path: '', component: EditorComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'questions-list', component: DashboardComponent, 
    children: [
      { path: '', component: QuestionsListComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'questions-trash', component: DashboardComponent, 
    children: [
      { path: '', component: QuestionsTrashComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'exam', component: DashboardComponent, 
    children: [
      { path: '', component: ExamComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'STUDENT' ]}  }
    ]
  },
  { path: 'exam-paper', component: DashboardComponent, 
    children: [
      { path: '', component: ExamPaperComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'STUDENT' ]}  }
    ]
  },
  { path: 'papers', component: DashboardComponent, 
    children: [
      { path: '', component: PapersComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'paper-questions/:paperId', component: DashboardComponent, 
    children: [
      { path: '', component: PaperQuestionsComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN', 'QUESTION_AGENT' ]}  }
    ]
  },
  { path: 'invite', component: DashboardComponent, 
    children: [
      { path: '', component: InvitationAddComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN' ]}  }
    ]
  },
  { path: 'invitations', component: DashboardComponent, 
    children: [
      { path: '', component: InvitationsListComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService], data: {roles:[ 'SUPER_ADMIN', 'ADMIN' ]}  }
    ]
  },
  { path: 'profile', component: DashboardComponent, 
    children: [
      { path: '', component: UserProfileComponent, outlet: 'dashboard-outlet', canActivate: [AuthGuardService]  }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
