import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { TreeNode } from 'primeng/api';
import { ITreeNodeData } from '../../../models/user-role-permission.model';

import { UserRolePermissionApiService } from '../../../services/user-role-permission-api.service';

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
        { field: 'view', header: 'View' },
        { field: 'add', header: 'Add' },
        { field: 'delete', header: 'Delete' },
        { field: 'edit', header: 'Edit' },
    ];
    roleId: number;

    // public method
    constructor(
        private activatedRoute: ActivatedRoute,
        private userRolePermissionApiService: UserRolePermissionApiService
    ) { }

    /**
     * Component lifecycle ngOnit
     *
     * @returns {void}
     * @memberof RolePermissionComponent
     */
    ngOnInit(): void {
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
        let data = this.getDetails();
        data.screens.map((data, index) => {
            let isChecked: boolean;
            const treeStructure = this.dataTreeStructure(data);
            const value: TreeNode = {
                data: treeStructure,
                expanded: false,
                children: []
            }
            this.treeNode.push(value);
            data.childs?.length ? isChecked = this.innerNodes(this.treeNode[index], data.childs) : null;
            const selectedNodes = this.selectedNodes;
            isChecked && treeStructure.isChecked ? (this.selectedNodes = [], selectedNodes.push(this.treeNode[index]), this.selectedNodes = [...selectedNodes]) : null
        })
    }

    /**
    * set inner Nodes for tree table
    * @returns {boolean}
    * @memberof RolePermissionComponent
    */
    innerNodes(parentNode, childs) {
        const treeNodesData = [];
        childs.map((child, index) => {
            let treeStructure = this.dataTreeStructure(child);
            const value: TreeNode = {
                data: treeStructure,
                expanded: false,
                children: []
            }
            treeStructure.isChecked ? treeNodesData.push(value) : '';
            parentNode['children'].push(value)
            if (childs?.length) {
                this.innerNodes(parentNode['children'][index], child.childs);
            }
        })
        if (parentNode['children'].length === treeNodesData.length) {
            this.selectedNodes = [...this.selectedNodes, ...treeNodesData];
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
        let isChecked: boolean;
        data.edit && data.delete && data.view && data.add ? isChecked = true : isChecked = false;
        return <ITreeNodeData>{ name: data.name, add: data.add, "edit": data.edit, "delete": data.delete, "view": data.view, id: data.id, moduleId: data.moduleId, isChecked: isChecked }
    }

    /**
    * select or unselect node based on status 
    * @memberof RolePermissionComponent
    */
    nodeSelect(event, selctionStatus) {
        let node = event?.originalEvent?.rowNode?.node;
        node?.children?.length ? this.nodeSelectUnSelect(node, selctionStatus) : null;
        this.cols.map((col) => {
            node.data[col.field] = selctionStatus;
        })

    }

    /**
    * select or unselect node based on status 
    * @memberof RolePermissionComponent
    */
    nodeSelectUnSelect(node, selctionStatus) {
        node.children.map((value) => {
            this.cols.map((col) => {
                value.data[col.field] = selctionStatus;
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
        }

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

}
