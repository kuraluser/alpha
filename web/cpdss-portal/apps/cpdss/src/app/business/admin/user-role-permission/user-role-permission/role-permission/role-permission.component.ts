import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';

import { TreeNode } from 'primeng/api';
import { ITreeNodeData, IUserDetail, IUserRolePermissionResponse, IScreenNode, IUserPermissionModel, IUserDetailsResponse, IUserPermissionScreen, ISavePermissionResponse } from '../../../models/user-role-permission.model';

import { UserRolePermissionApiService } from '../../../services/user-role-permission-api.service';
import { UserRolePermissionTransformationService } from '../../../services/user-role-permission-transformation.service';
import { PermissionsService } from '../../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../../shared/services/app-configuration/app-configuration.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../../shared/models/common.model';

/**
 * Component class of role permission for user role
 *
 * @export
 * @class RolePermissionComponent
 * @implements {OnInit}
 */

@Component({
    selector: 'cpdss-portal-role-permission',
    templateUrl: './role-permission.component.html',
    styleUrls: ['./role-permission.component.scss']
})
export class RolePermissionComponent implements OnInit {

    treeNode: any;
    selectedNodes: TreeNode[] = [];
    cols = [
        { field: 'view', header: 'View', isViewable: 'isViewVisible' },
        { field: 'add', header: 'Add', isViewable: 'isAddVisible' },
        { field: 'delete', header: 'Delete', isViewable: 'isDeleteVisible' },
        { field: 'edit', header: 'Edit', isViewable: 'isEditVisible' },
    ];
    roleId: number;
    selectedUser: IUserDetail[] = [];
    roleDetailsForm: FormGroup;
    errorMessages: any;
    userDetails: IUserDetail[] = [];
    public saveRoleBtnPermissionContext:IPermissionContext;

    // public method
    constructor(
        private fb: FormBuilder,
        private activatedRoute: ActivatedRoute,
        private ngxSpinnerService: NgxSpinnerService,
        private translateService: TranslateService,
        private router: Router,
        private permissionsService: PermissionsService,
        private messageService: MessageService,
        private userRolePermissionApiService: UserRolePermissionApiService,
        private userRolePermissionTransformationService: UserRolePermissionTransformationService
    ) { }

    /**
     * Component lifecycle ngOnit
     *
     * @returns {void}
     * @memberof RolePermissionComponent
     */
    ngOnInit(): void {
        this.getPagePermission();
        this.errorMessages = this.userRolePermissionTransformationService.setValidationErrorMessage();
        this.roleDetailsForm = this.fb.group({
            'roleName': ['', [Validators.required , Validators.pattern('^[a-zA-Z0-9 ]+')]],
            'roleDescription': ['', [Validators.required]],
            'type': ['shore']
        });
        this.activatedRoute.paramMap.subscribe(params => {
            this.roleId = Number(params.get('roleId'));
            this.treeNode = [];
            this.getUserRolePermission();
        })
    }

      /**
   * Get page permission
   *
   * @memberof RolePermissionComponent
   */
  getPagePermission() {
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['UserRoleListing']);
    if(!permission.edit) {
        this.router.navigate(['/access-denied']);
    }
  }

    /**
    * get User Role Permission
    * @memberof RolePermissionComponent
    */
    async getUserRolePermission() {
        this.ngxSpinnerService.show();
        const userDetailsRes: IUserRolePermissionResponse = await this.userRolePermissionApiService.getUserRolePermission(this.roleId).toPromise();
        try {
            this.ngxSpinnerService.hide();
            const treeNode = [];
            if (userDetailsRes.responseStatus.status === '200') {
                this.getUserDetails(userDetailsRes.users);
                this.roleDetailsForm.controls['roleName'].setValue(userDetailsRes.role?.name);
                this.roleDetailsForm.controls['roleDescription'].setValue(userDetailsRes.role?.description);
                const userDetails = userDetailsRes.screens;
                userDetails.map((userDetail: IScreenNode, index) => {
                    let isChecked: boolean;
                    const treeStructure = this.dataTreeStructure(userDetail);
                    const value: TreeNode = {
                        data: treeStructure,
                        expanded: false,
                        children: []
                    }
                    treeNode.push(value);
                    if (userDetail.childs && userDetail.childs.length) {
                        isChecked = this.innerNodes(treeNode[index], userDetail.childs);
                        isChecked && treeStructure.isChecked ? (treeNode[index]['data']['nodeChecked'] = true , this.selectedNodes = [...this.selectedNodes , treeNode[index]]) : null
                    } else {
                        treeStructure.isChecked ? (treeNode[index]['data']['nodeChecked'] = true , this.selectedNodes = [...this.selectedNodes, treeNode[index]]) : null;
                    }
                })
            }
            this.treeNode = [...treeNode];
        }
        catch (error) {
            const translationKeys = await this.translateService.get(['USER_PERMISSION_INVALID_USER_ERROR', 'USER_PERMISSION_INVALID_USER']).toPromise();
            if (error.error.errorCode === 'ERR-RICO-205') {
                this.messageService.add({ severity: 'error', summary: translationKeys['USER_PERMISSION_INVALID_USER_ERROR'], detail: translationKeys['USER_PERMISSION_INVALID_USER'] });
            }
            this.ngxSpinnerService.hide();
        }

    }

    /**
    * set inner Nodes for tree table
    * @returns {boolean}
    * @memberof RolePermissionComponent
    */
    innerNodes(parentNode, childs) {
        const treeNodesData = [];
        childs.map((child, index) => {
            let isChecked: boolean;
            let treeStructure = this.dataTreeStructure(child);
            const value: TreeNode = {
                data: treeStructure,
                expanded: false,
                children: []
            }
            parentNode['children'].push(value)
            if (child.childs?.length) {
                isChecked = this.innerNodes(parentNode['children'][index], child.childs);
                if(isChecked && treeStructure.isChecked){
                    value.data['nodeChecked'] = true;
                    this.selectedNodes = [...this.selectedNodes , parentNode['children'][index]];
                    treeNodesData.push(value)
                } 
            } else if(treeStructure.isChecked){
                    value.data['nodeChecked'] = true;
                    this.selectedNodes = ([...this.selectedNodes , value]);
                    treeNodesData.push(value);
            }
        })
        if (parentNode['children'].length === treeNodesData.length) {
            return true
        } else {
            return false
        }
    }

    /**
    * set inner Nodes tree data object
    * @returns {ITreeNodeData}
    * @memberof RolePermissionComponent
    */
    dataTreeStructure(data) {
        const roleScreen = data.roleScreen;
        let isChecked: boolean;
        (!data.isAddVisible || roleScreen?.canAdd) && (!data.isEditVisible || roleScreen?.canEdit) && (!data.isDeleteVisible || roleScreen?.canDelete) && (!data.isViewVisible || roleScreen?.canView) ? isChecked = true : isChecked = false;
        return <ITreeNodeData>{
            name: data.name,
            add: roleScreen?.canAdd ? true : false,
            edit: roleScreen?.canEdit ? true : false,
            delete: roleScreen?.canDelete ? true : false,
            view: roleScreen?.canView ? true : false,
            id: data.id,
            moduleId: data.moduleId,
            isChecked: isChecked,
            isAddVisible: data.isAddVisible,
            isDeleteVisible: data.isDeleteVisible,
            isEditVisible: data.isEditVisible,
            isViewVisible: data.isViewVisible,
            nodeChecked: false
        }
    }

    /**
    * select or unselect node based on status 
    * @memberof RolePermissionComponent
    */
    nodeSelectUnSelect(node: any, selctionStatus: boolean) {
        node.children.map((value) => {
            this.cols.map((col) => {
                value.data[col.isViewable] ? value.data[col.field] = selctionStatus : null;
                value.children?.length ? this.nodeSelectUnSelect(value, selctionStatus) : null
            })
        })
    }

    /**
    * select or unselect node based on status 
    * @memberof RolePermissionComponent
    */
    parentNodeChange(node: any, rowData: any) {
        rowData['nodeChecked'] = !rowData['nodeChecked'];
        const treeNode = node?.node;
        treeNode?.children?.length ? this.nodeSelectUnSelect(treeNode, rowData['nodeChecked']) : null;
        this.cols.map((col) => {
            treeNode.data[col.isViewable] ? treeNode.data[col.field] = rowData['nodeChecked'] : null;
        });
        this.childParentNodeRelation(this.treeNode);
    }


    /**
    * change check box status
    * @memberof RolePermissionComponent
    */
    checkboxChange($event, rowData, rowNode, field) {
        rowData[field] = !rowData[field];
        if (!rowData[field]) {
            this.childParentNodeRelation(this.treeNode);
        } else {
            this.childParentNodeRelation(this.treeNode);
        }
    }

    /**
    * parent child relation ship
    * @memberof RolePermissionComponent
    */
    childParentNodeRelation(treeNode) {
        const treeNodesData = [];
        treeNode?.map((node) => {
            let isChecked: boolean; 

            if(node.children?.length) {
                isChecked = this.childParentNodeRelation(node.children);
                if(isChecked && this.isNodeChecked(node.data)) {
                    node.data['nodeChecked'] = true;
                    treeNodesData.push(node.children);
                }  else {
                    node.data['nodeChecked'] = false;
                }
            } else if(this.isNodeChecked(node.data)){
                node.data['nodeChecked'] = true;
                treeNodesData.push(node);
            } else {
                node.data['nodeChecked'] = false;
            }
            
        })
        if (treeNode?.length === treeNodesData.length) {
            return true
        } else {
            return false
        }
    }

    /**
    * check if node is selected
    * @memberof RolePermissionComponent
    */
    isNodeChecked(nodeDetails) {
        let isChecked:boolean;
        (!nodeDetails.isAddVisible || nodeDetails?.add) && (!nodeDetails.isEditVisible || nodeDetails?.edit) && (!nodeDetails.isDeleteVisible || nodeDetails?.delete) && (!nodeDetails.isViewVisible || nodeDetails?.view) ? isChecked = true : isChecked = false;
        return isChecked
    }

    /**
    * is current node ready to check
    * @memberof RolePermissionComponent
    */
    checkNodeSelected(nodeData) {
        return (!nodeData.isAddVisible || nodeData?.add) && (!nodeData.isEditVisible || nodeData?.edit) && (!nodeData.isDeleteVisible || nodeData?.delete) && (!nodeData.isViewVisible || nodeData?.view);
    }

    /**
     * Get field errors
     * @param {string} formControlName
     * @returns {ValidationErrors}
     * @memberof RolePermissionComponent
     */
    fieldError(formControlName: string): ValidationErrors {
        const formControl = this.field(formControlName);
        return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
    }

    /**
    * Get form control of  roleDetailsForm
    * @param {string} formControlName
    * @returns {FormControl}
    * @memberof RolePermissionComponent
    */
    field(formControlName: string): FormControl {
        const formControl = <FormControl>this.roleDetailsForm.get(formControlName);
        return formControl;
    }

    /**
    * Save permission
    * @memberof RolePermissionComponent
    */
    async saveRoleDetails() {
        if (this.roleDetailsForm.valid) {
            const translationKeys = await this.translateService.get(['USER_PERMISSION_SELECT_USER_ERROR', 'USER_PERMISSION_SELECT_USER','USER_PERMISSION_CREATE_SUCCESS', 'USER_PERMISSION_CREATED_SUCCESSFULLY', 'USER_PERMISSION_CREATE_ERROR', 'USER_PERMISSION_ALREADY_EXIST']).toPromise();
            const selectedUser = this.selectedUser?.map((user) => {
                return user.id
            })
            const treeNodeScreen: IUserPermissionScreen[] = [];
            this.treeNode?.map((node) => {
                this.setTreeNode(node, node.data, treeNodeScreen)
            })
            const userPermission: IUserPermissionModel = {
                role: {
                    description: this.roleDetailsForm.value.roleDescription,
                    name: this.roleDetailsForm.value.roleName
                },
                screens: treeNodeScreen,
                roleId: this.roleId,
                userId: selectedUser ? selectedUser : []
            }
            this.ngxSpinnerService.show();
           
            const savePermissionRes: ISavePermissionResponse = await this.userRolePermissionApiService.rolePermission(userPermission).toPromise();
            try {
                this.ngxSpinnerService.hide();
                if (savePermissionRes.responseStatus.status === '200') {
                    this.messageService.add({ severity: 'success', summary: translationKeys['USER_PERMISSION_CREATE_SUCCESS'], detail: translationKeys['USER_PERMISSION_CREATED_SUCCESSFULLY'] });
                    this.router.navigate(['./'], { relativeTo: this.activatedRoute.parent });
                }
            }
            catch (error) {
                if (error.error.errorCode === 'ERR-RICO-400') {
                    this.messageService.add({ severity: 'error', summary: translationKeys['NEW_ROLE_CREATE_ERROR'], detail: translationKeys['ROLE_ALREADY_EXIST'] });
                }
                this.ngxSpinnerService.hide();
            }
        } else {
            this.roleDetailsForm.markAllAsTouched();
        }
    }

    /**
    * Set tree nodes
    * @memberof RolePermissionComponent
    */
    setTreeNode(node: TreeNode, nodeData: ITreeNodeData, treeNodeScreen: IUserPermissionScreen[]) {
        treeNodeScreen.push({
            id: nodeData.id,
            name: nodeData.name,
            add: nodeData.add,
            edit: nodeData.edit,
            view: nodeData.view,
            delete: nodeData.delete
        })
        if (node.children?.length) {
            node.children?.map((children) => {
                this.setTreeNode(children, children.data, treeNodeScreen)
            })
        }
    }

    /**
    * selected user details
    * @memberof RolePermissionComponent
    */
    userSelected(selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
    * cancel button to , navigate to listing page
    * @memberof RolePermissionComponent
    */
    cancel() {
        this.router.navigate(['./'], { relativeTo: this.activatedRoute.parent });
    }

    /**
     * get User Details
     * @memberof RolePermissionComponent
    */
    async getUserDetails(users) {
        this.ngxSpinnerService.show();
        const userDetailsRes: IUserDetailsResponse = await this.userRolePermissionApiService.getUserDetails().toPromise();
        this.ngxSpinnerService.hide();
        if (userDetailsRes.responseStatus.status === '200') {
            let userDetails = userDetailsRes.users;
            userDetails?.map((userDetail) => {
                this.userDetails.push({ ...userDetail, 'name': userDetail.firstName + ' ' + userDetail.lastName });
            });
            users?.map((userId) => {
                const selectedUser = this.userDetails?.filter((userDetail) => userDetail.id === userId.id);
                selectedUser && selectedUser.length ? this.selectedUser = [...this.selectedUser, selectedUser[0]] : null;
            })
        }
    }
}
