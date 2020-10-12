import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from '../../services/common.service';
import { AppConstants } from 'src/app/services/AppConstants';
import { type } from 'jquery';
declare var $;
@Component({
  selector: 'app-invitations-list',
  templateUrl: './invitations-list.component.html',
  styleUrls: ['./invitations-list.component.css']
})
export class InvitationsListComponent implements OnInit {

  constructor(private router: Router,
    private toastr: ToastrService,
    private _commonService : CommonService,
    private AppConstants : AppConstants) { }

    dataTableInstance = null;
    activeInvitation = null;
    invitationRequestType = null;
    dataTable: any;
    dtOptions: any;
    invitations : any;
    @ViewChild('invitationTable', {static: true}) invitationTable;
  ngOnInit() {
    this.listInvitations();
    $(document).ready(function() {
      $('#dataTable1').DataTable({
        "fixedHeader": {
          header: true,
        },
        "scrollY":'300px'
      });
      $('#dataTable2').DataTable();
  } );
  }

  listInvitations() {
    $('#spinner').css('display', 'flex')
    this._commonService.listInvitations().subscribe((response) => {
      console.log(response)
      this.invitations = response;
      this.drawTables();
      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
    });
  }

  inviteUsers() {
    this.router.navigateByUrl("/invite");
  }

  resendInvitation(invitation, component) {
    var username = invitation.email ? invitation.email : invitation.phone
    $('#confirmationModalTitle').html('Please Confirm');
    $('#confirmationModalBody').html('Are you sure, you want ro resend invitation to ' + username);
    $('#confirmationModal').modal('show');
    component.activeInvitation = invitation;
    component.invitationRequestType = 'resend';
  }

  cancelInvitation(invitation, component) {
    var username = invitation.email ? invitation.email : invitation.phone
    $('#confirmationModalTitle').html('Please Confirm');
    $('#confirmationModalBody').html('Are you sure, you want ro cancel invitation of ' + username);
    $('#confirmationModal').modal('show');
    component.activeInvitation = invitation;
    component.invitationRequestType = 'cancel';
  }

  confirm() {
    if(this.invitationRequestType == 'resend') {
      this.resendInvitationRequest();
    } else if(this.invitationRequestType == 'cancel') {
      this.cancelInvitationRequest();
    }
  }

  resendInvitationRequest() {
    var data = {}
    data['phone'] = this.activeInvitation.phone;
    data['email'] = this.activeInvitation.email;
    data['keyword'] = this.activeInvitation.keyword;
    data['organisation'] = this.activeInvitation.organisation;
    data['role'] = this.activeInvitation.role;
    $('#spinner').css('display', 'flex')
    this._commonService.invite(
      data
      ).subscribe((response) => {
        $('#spinner').hide()
        $('#confirmationModal').modal('hide');
      console.log(response);
      if(response["status"] == "success") {
        this.openSnackBar('success', 'Success', response["message"]);
        this.listInvitations();
      } else {
        this.openSnackBar('error', 'Error', response["message"]);  
      }
    }, (error) => {
      $('#spinner').hide()
      $('#confirmationModal').modal('hide');
      console.log(error);
      this.openSnackBar('error', 'Error', 'User does not exist');
    });
  }

  cancelInvitationRequest() {
    $('#spinner').css('display', 'flex')
    this._commonService.cancelInvitation(
      this.activeInvitation.id
      ).subscribe((response) => {
        $('#spinner').hide()
        $('#confirmationModal').modal('hide');
      console.log(response);
      if(response["status"] == "success") {
        this.openSnackBar('success', 'Success', response["message"]);
        this.listInvitations();
      } else {
        this.openSnackBar('error', 'Error', response["message"]);  
      }
    }, (error) => {
      $('#spinner').hide()
      $('#confirmationModal').modal('hide');
      console.log(error);
      this.openSnackBar('error', 'Error', 'User does not exist');
    });
  }

  drawTables() {
    this.dtOptions = {
      responsive: true,
      data: this.invitations,
      columns: [
        {title: 'Keyword', data: 'keyword'},
        {title: 'Email', data: 'email'},
        {title: 'Phone', data: 'phone'},
        {title: 'Access Code', data: 'invitationToken'},
        {title: 'Invited On', data: 'invitedOnText'},
        {title: 'Role', data: 'role.role'},
        {title: 'Status', data: 'status', render : function(data, type, row) {
          if(data == 'PENDING')
            return '<span class="badge badge-warning">'+data+'</span>'
          else if(data == 'EXPIRED')
            return '<span class="badge badge-danger">'+data+'</span>'
          else if(data == 'ACCEPTED')
            return '<span class="badge badge-success">'+data+'</span>'
          else 
            return '<span class="badge badge-secondary">'+data+'</span>'
        }},
        {title: '', data: 'id',sorting:false, render : function(data, type, row) {
          return '<div class="dropdown no-arrow ">'+
                    '<a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'+
                      '<i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400" aria-hidden="true"></i>'+
                    '</a>'+
                    '<div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink" x-placement="bottom-end">'+
                      '<a class="dropdown-item" id="resendClick"><i class="fas fa-paper-plane text-primary"></i> Resend Invitation</a>'+
                      '<a class="dropdown-item" id="cancelClick"><i class="fas fa-ban text-danger"></i> Cancel Invitation</a>'+
                    '</div>'+
                  '</div> '
        }}
      ]
    };
    var component = this;
    if(component.dataTableInstance != null){
      component.dataTableInstance.destroy();
    }
    this.dataTable = $(this.invitationTable.nativeElement);
    component.dataTableInstance = this.dataTable.DataTable(this.dtOptions);

    // var table = this.dataTable;
    
    var resendInvitation = this.resendInvitation;
    var cancelInvitation = this.cancelInvitation;
    $('#resendClick').on( 'click',  function () {
      var data = component.dataTableInstance.row( $(this).parents('tr') ).data();
      resendInvitation(data, component);
    } );
    $('#cancelClick').on( 'click',  function () {
      var data = component.dataTableInstance.row( $(this).parents('tr') ).data();
      cancelInvitation(data, component);
    } );
  }

  openSnackBar(type, title, message) {
    if(type == "success")
      this.toastr.success(message, title);
    else if(type == "error")
      this.toastr.error(message, title);
    else if(type == "warn")
      this.toastr.warning(message, title);
    else
      this.toastr.info(message, title);
  }
}
