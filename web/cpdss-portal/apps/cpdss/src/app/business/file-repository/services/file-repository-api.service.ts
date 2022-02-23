import { Injectable } from '@angular/core';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { IFileRepoStateChange, IFileRepoResponse, IDeleteResponse, ISaveUpdateFileResponse } from './../models/file-repository.model';
import { Observable } from 'rxjs';
/**
 * Api Service for file repository
 *
 * @export
 * @class FileRepositoryApiService
 */

@Injectable()
export class FileRepositoryApiService {

  constructor(
    private commonApiService: CommonApiService
  ) { }

  /**
   * Method for getting ullage details
   *
   * @param {IFileRepoStateChange} data
   * @memberof FileRepositoryApiService
   */
  getFiles(data: IFileRepoStateChange, vesselId: number) {
    const params: any = `vesselId=${vesselId}${data.pageSize ? '&pageSize=' + data.pageSize : ''}${data.pageNo ? '&pageNo=' + data.pageNo : ''}${data.sortBy ? '&sortBy=' + data.sortBy : ''}${data.orderBy ? '&orderBy=' + data.orderBy : ''}${data.voyageNumber ? '&voyageNumber=' + data.voyageNumber : ''}${data.fileName ? '&fileName=' + data.fileName : ''}${data.fileType ? '&fileType=' + data.fileType : ''}${data.section ? '&section=' + data.section : ''}${data.category ? '&category=' + data.category : ''}${data.createdDate !== ''? '&createdDate=' + data.createdDate : ''}${data.createdBy ? '&createdBy=' + data.createdBy : ''}`;
    return this.commonApiService.get<IFileRepoResponse>(`file-repo?${params}`);
  }

  /**
   * Method for getting ullage details
   *
   * @param {IFileRepoStateChange} data
   * @memberof FileRepositoryApiService
   */
  deleteFile(id: number, data: IFileRepoStateChange) {
    return this.commonApiService.delete<IDeleteResponse>(`file-repo/${id}`);
  }

  /**
   * Method for download file
   *
   * @param {number} id
   * @memberof FileRepositoryApiService
   */
  downloadFile(id: number): Observable<any> {
    return this.commonApiService.get<any>(`file-repo/${id}/download`, { responseType: 'blob' as 'json' });
  }

  /**
   * Method for save file
   *
   * @param {FormData} data
   * @memberof FileRepositoryApiService
   */
  saveFile(data: FormData) {
    return this.commonApiService.postFormData<ISaveUpdateFileResponse>(`file-repo`, data);
  }

  /**
   * Method for update file
   * @param {number} id
   * @param {FormData} data
   * @memberof FileRepositoryApiService
   */
  updateFile(id: number, data: FormData) {
    return this.commonApiService.putFormData<ISaveUpdateFileResponse>(`file-repo/${id}`, data);
  }
}
