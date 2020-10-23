/**
 * Interface for menuItem
 */
export interface IMenuItem {
    menu: string;
    menuIcon: string;
    menuLink: string;
    subMenu: ISubMenu[];
}
/**
 * Interface for submenu
 */
export interface ISubMenu {
    name: string;
}