/**
 * Interface for menuItem
 */
export interface IMenuItem {
    menu: string;
    menuIcon: string;
    menuLink: string;
    subMenu: ISubMenu[];
    permissionMapping: string;
    isSubMenuOpen: boolean;
    addVesselId?: boolean;
    addVoyageId?: boolean;
    addLoadableStudyId?: boolean;
    addLoadablePatternId?: boolean;
}
/**
 * Interface for submenu
 */
export interface ISubMenu {
    name: string;
    subMenuLink: string;
    permissionMapping: string;
}

/**
 * Interface for permission details
 *
 * @export
 * @interface IPermission
 */
export interface IPermission {
    add: boolean;
    edit: boolean;
    delete: boolean;
    view: boolean;
}