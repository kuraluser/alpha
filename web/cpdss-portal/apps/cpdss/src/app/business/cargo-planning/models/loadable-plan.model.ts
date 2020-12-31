

/**
 * Interface for loadable quality plan table 
 *
 * @export
 * @interface ITableHeaderModel
 */
export interface ITableHeaderModel {
    field?: string;
    header: string;
    rowspan?: number;
    colspan?: number,
    subColumns?:ColumHeader[];  
}

/**
 * Interface for table header
*/
interface ColumHeader {
    field: string;
    header: string;
    rowspan?: number;
}