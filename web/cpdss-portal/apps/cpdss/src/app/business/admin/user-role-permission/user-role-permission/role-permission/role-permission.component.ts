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

    // public method
    constructor(
        private fb: FormBuilder,
        private activatedRoute: ActivatedRoute,
        private ngxSpinnerService: NgxSpinnerService,
        private translateService: TranslateService,
        private router: Router,
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
        this.errorMessages = this.userRolePermissionTransformationService.setValidationErrorMessage();
        this.roleDetailsForm = this.fb.group({
            'roleName': ['', [Validators.required]],
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
    * get User Role Permission
    * @memberof RolePermissionComponent
    */
    async getUserRolePermission() {
        this.ngxSpinnerService.show();
        const userDetailsRes: IUserRolePermissionResponse = await this.userRolePermissionApiService.getUserRolePermission(this.roleId).toPromise();
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
                    isChecked && treeStructure.isChecked ? (this.selectedNodes = [...this.selectedNodes , treeNode[index]]) : null
                } else {
                    treeStructure.isChecked ? (this.selectedNodes = [...this.selectedNodes, treeNode[index]]) : null;
                }
            })
        }
        this.treeNode = [...treeNode];

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
                    this.selectedNodes = [...this.selectedNodes , parentNode['children'][index]];
                    treeNodesData.push(value)
                } 
            } else if(treeStructure.isChecked){
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
            isViewVisible: data.isViewVisible
        }
    }

    /**
    * select or unselect node based on status 
    * @memberof RolePermissionComponent
    */
    nodeSelect(event: any, selctionStatus: boolean) {
        let node = event?.originalEvent?.rowNode?.node;
        node?.children?.length ? this.nodeSelectUnSelect(node, selctionStatus) : null;
        this.cols.map((col) => {
            node.data[col.isViewable] ? node.data[col.field] = selctionStatus : null;
        })
        const selectedNodes = this.selectedNodes;
        this.selectedNodes?.map((selectedNode, index) => {
            if (!this.checkNodeSelected(selectedNode.data)) {
                selectedNodes.splice(index, 1);
            }
        });
        this.selectedNodes = [...selectedNodes];
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
    * change check box status
    * @memberof RolePermissionComponent
    */
    checkboxChange($event, rowData, rowNode, field) {
        rowData[field] = !rowData[field];
        let selectedNodes = this.selectedNodes;
        if (!rowData[field]) {
            let nodeToBeReomoved;
            this.selectedNodes && this.selectedNodes.map((selectedNode, index) => {
                if (selectedNode.data.name == rowNode.node.data.name) {
                    selectedNodes.splice(index, 1);
                    nodeToBeReomoved = rowNode.node;
                }
            });
            rowNode.parent ? this.getParentDetails(rowNode, selectedNodes) : (this.selectedNodes = [...selectedNodes]);
        } else {
            let nodeData = rowNode.node?.data;
            let allNodeChildren = [];
            rowNode.node?.children?.length ? allNodeChildren = this.getAllNodeChildren(rowNode.node?.children, allNodeChildren) : null;
            if (this.checkNodeSelected(nodeData)) {
                let childNodeSelected: boolean = true;
                for (let i = 0; i < allNodeChildren.length; i++) {
                    let userselected;
                    for (let user = 0; user < this.selectedNodes.length; user++) {
                        if (this.selectedNodes[user].data?.id == allNodeChildren[i].id) {
                            userselected = true;
                        }
                    }
                    if (!userselected) {
                        childNodeSelected = false;
                        break;
                    }
                }
                childNodeSelected ? this.selectedNodes = [... this.selectedNodes, rowNode.node] : null;
                childNodeSelected && rowNode.parent ? this.checkParentNodeSelected(rowNode.parent) : null;
            }

        }
    }

    /**
    * get all node children 
    * @memberof RolePermissionComponent
    */
    getAllNodeChildren(nodeChildren, nodeChildrenArray) {
        nodeChildren?.map((childrenTreeNode) => {
            nodeChildrenArray.push({ name: childrenTreeNode.data.name, id: childrenTreeNode.data.id });
            childrenTreeNode.children?.length ? this.getAllNodeChildren(childrenTreeNode.children, nodeChildrenArray) : null;
        })
        return nodeChildrenArray;
    }

    /**
    * check parent node selected
    * @memberof RolePermissionComponent
    */
    checkParentNodeSelected(parentNode) {
        let parentSelectedNode = [];
        parentNode?.children?.map((node) => {
            this.selectedNodes?.map((selectedNode) => {
                if (node.data.name === selectedNode.data.name) {
                    parentSelectedNode.push(node);
                }
            })
        });

        if (parentSelectedNode.length === parentNode?.children.length && this.checkNodeSelected(parentNode.data)) {
            this.selectedNodes = [... this.selectedNodes, parentNode];
            parentNode.parent ? this.checkParentNodeSelected(parentNode.parent) : null;
        }
    }

    /**
    * is current node ready to check
    * @memberof RolePermissionComponent
    */
    checkNodeSelected(nodeData) {
        return (!nodeData.isAddVisible || nodeData?.add) && (!nodeData.isEditVisible || nodeData?.edit) && (!nodeData.isDeleteVisible || nodeData?.delete) && (!nodeData.isViewVisible || nodeData?.view);
    }

    /**
    * get parent details from current node
    * @memberof RolePermissionComponent
    */
    getParentDetails(rowNodes, selectedNodes) {
        const rowNode = rowNodes.parent;
        this.selectedNodes && this.selectedNodes.map((selectedNode, index) => {
            if (selectedNode.data.name == rowNode.data?.name) {
                selectedNodes.splice(index, 1);
            }
        })
        rowNode.parent ? this.getParentDetails(rowNode, selectedNodes) : this.selectedNodes = [...selectedNodes];
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
            const translationKeys = await this.translateService.get(['USER_PERMISSION_CREATE_SUCCESS', 'USER_PERMISSION_CREATED_SUCCESSFULLY', 'USER_PERMISSION_CREATE_ERROR', 'USER_PERMISSION_ALREADY_EXIST']).toPromise();
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
    * get dummy details
    * @memberof RolePermissionComponent
    */
    getDetails() {
        return {
            "responseStatus": {
                "status": "200"
            },
            "screens": [
                {
                    "id": 3,
                    "name": "Loading",
                    "languageKey": null,
                    "add": true,
                    "edit": true,
                    "delete": true,
                    "view": true,
                    "moduleId": 3,
                    "childs": [
                        {
                            "id": 4,
                            "name": "Discharging",
                            "languageKey": null,
                            "add": true,
                            "edit": true,
                            "delete": true,
                            "view": true,
                            "moduleId": 3,
                            "childs": [
                                {
                                    "id": 6,
                                    "name": "Voyage",
                                    "languageKey": null,
                                    "add": true,
                                    "edit": true,
                                    "delete": true,
                                    "view": true,
                                    "moduleId": 4,
                                    "childs": [],
                                    "roleScreen": {
                                        "id": null,
                                        "role": null,
                                        "screen": null,
                                        "canAdd": null,
                                        "canEdit": null,
                                        "canView": null,
                                        "canDelete": null,
                                        "canPrint": null,
                                        "companyId": null
                                    }
                                }
                            ],
                            "roleScreen": {
                                "id": null,
                                "role": null,
                                "screen": null,
                                "canAdd": null,
                                "canEdit": null,
                                "canView": null,
                                "canDelete": null,
                                "canPrint": null,
                                "companyId": null
                            }
                        },
                        {
                            "id": 5,
                            "name": "Bunkering",
                            "languageKey": null,
                            "add": true,
                            "edit": true,
                            "delete": true,
                            "view": true,
                            "moduleId": 3,
                            "childs": [],
                            "roleScreen": {
                                "id": null,
                                "role": null,
                                "screen": null,
                                "canAdd": null,
                                "canEdit": null,
                                "canView": null,
                                "canDelete": null,
                                "canPrint": null,
                                "companyId": null
                            }
                        }
                    ],
                    "roleScreen": {
                        "id": null,
                        "role": null,
                        "screen": null,
                        "canAdd": null,
                        "canEdit": null,
                        "canView": null,
                        "canDelete": null,
                        "canPrint": null,
                        "companyId": null
                    }
                },
                {
                    "id": 2,
                    "name": "Status",
                    "languageKey": "STATUS",
                    "add": true,
                    "edit": true,
                    "delete": true,
                    "view": false,
                    "moduleId": 2,
                    "childs": [
                        {
                            "id": 10,
                            "name": "On hand quantities",
                            "languageKey": "CARGOPLANNING_ONHANDQUANTITITES",
                            "add": true,
                            "edit": true,
                            "delete": true,
                            "view": true,
                            "moduleId": 2,
                            "childs": [],
                            "roleScreen": {
                                "id": null,
                                "role": null,
                                "screen": null,
                                "canAdd": null,
                                "canEdit": null,
                                "canView": null,
                                "canDelete": null,
                                "canPrint": null,
                                "companyId": null
                            }
                        },
                        {
                            "id": 11,
                            "name": "Cargo Nomination",
                            "languageKey": "CARGOPLANNING_CARGONOMINATION",
                            "add": true,
                            "edit": true,
                            "delete": true,
                            "view": true,
                            "moduleId": 2,
                            "childs": [],
                            "roleScreen": {
                                "id": null,
                                "role": null,
                                "screen": null,
                                "canAdd": null,
                                "canEdit": null,
                                "canView": null,
                                "canDelete": null,
                                "canPrint": null,
                                "companyId": null
                            }
                        },
                        {
                            "id": 12,
                            "name": "Ports",
                            "languageKey": "CARGOPLANNING_PORTS",
                            "add": true,
                            "edit": true,
                            "delete": true,
                            "view": true,
                            "moduleId": 2,
                            "childs": [],
                            "roleScreen": {
                                "id": null,
                                "role": null,
                                "screen": null,
                                "canAdd": null,
                                "canEdit": null,
                                "canView": null,
                                "canDelete": null,
                                "canPrint": null,
                                "companyId": null
                            }
                        }
                    ],
                    "roleScreen": {
                        "id": null,
                        "role": null,
                        "screen": null,
                        "canAdd": true,
                        "canEdit": true,
                        "canView": true,
                        "canDelete": true,
                        "canPrint": null,
                        "companyId": null
                    }
                },
                {
                    "id": 1,
                    "name": "Cargo Planning",
                    "languageKey": "CARGOPLANNING",
                    "add": false,
                    "edit": true,
                    "delete": true,
                    "view": true,
                    "moduleId": 1,
                    "childs": [],
                    "roleScreen": {
                        "id": null,
                        "role": null,
                        "screen": null,
                        "canAdd": true,
                        "canEdit": true,
                        "canView": true,
                        "canDelete": true,
                        "canPrint": null,
                        "companyId": null
                    }
                }
            ]
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
