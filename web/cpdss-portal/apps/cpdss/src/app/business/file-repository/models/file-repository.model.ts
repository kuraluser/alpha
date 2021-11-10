import { IResponse, IResponseStatus, ValueObject } from "../../../shared/models/common.model";

/**
 * Interface for file repository state change
 *
 * @export
 * @interface IFileRepoStateChange
 */
export interface IFileRepoStateChange {
    fileName?: string;
    pageNo?: number;
    voyageNumber?: string;
    pageSize?: number;
    sortBy?: string;
    orderBy?: string;
    fileType?: string;
    section?: string;
    category?: string;
    createdDate?: string;
    createdBy?: string;
}

/**
 * Interface for file repository response data
 *
 * @export
 * @interface IFileRepoResponse
 */
export interface IFileRepoResponse {
    responseStatus: IResponse;
    fileRepos: IFileRepositoryData[];
    totalElements: number;
}

/**
 * Interface for file repository data
 *
 * @export
 * @interface IFileRepositoryData
 */
export interface IFileRepositoryData {
    category: string;
    createdBy: string;
    createdDate: string;
    fileName: string;
    fileType: string;
    id: number;
    isSystemGenerated: boolean;
    section: string;
    voyageNumber: string;
    isDeletable?: boolean;
    isEditable?: boolean;
    view?: boolean;
    fileIcon?: string;
    uploadType?: string;
}

/**
 * Interface for delete file
 *
 * @export
 * @interface IDeleteResponse
 */
export interface IDeleteResponse {
    responseStatus: IResponse;
    id: number;
}

/**
 * Interface for delete file
 *
 * @export
 * @interface ISaveUpdateFileResponse
 */
 export interface ISaveUpdateFileResponse {
    responseStatus: IResponse;
    id: number;
}
