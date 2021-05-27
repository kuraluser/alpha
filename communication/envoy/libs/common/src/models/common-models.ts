/**
 * Common data objects and interfces
 */

import { ProcessStatusLevels } from "@envoy/common";

/**
 * Common event data
 */
export interface EventData {
  shipId: string;
  clientId: string;
  messageId: string;
  messageType: string;
  sequence: number;
  uniqueId: string;
  fileName: string;
  checksum: string;
  algo: string;
  size: number;
}

/**
 * Interface for inbound event data
 */
export interface InboundEventData extends EventData {
  _id?: string,
  process: 'upload' | 'split' | 'send' | 'finish' | 'confirm' | 'cancel'
  processStatus?: ProcessStatusLevels;
  split?: 'y' | 'n'
  filePath?: string;
  partNumber?: string;
  total?: number;
  startIndex?: string;
  createdtimeStamp?: number;
  missingPackets?: string;
  ioClient?: SocketIOClient.Socket | SocketIO.Socket;
  splitRead?: string,
  splitReadTimeStamp?: number;
  transportRead?: string;
  transportReadTimeStamp?: number;
  finishRead?: string;
  finishReadTimeStamp?: number;
  confirmRead?: string;
  confirmReadTimeStamp?: number;
}

/**
 * Interface for outbound event data
 */
export interface OutboundEventData extends EventData {
  _id?: string;
  total: number;
  filePath: string;
  createdtimeStamp: number;
  partNumber?: string;
  process?: 'received' | 'verified' | 'shared';
  processStatus?: ProcessStatusLevels;
  missingPackets?: string;
  ioClient?: SocketIOClient.Socket | SocketIO.Socket;
  resendRead?: string;
  verifyRead?: string;
  verifyReadTimeStamp?: number;
}

//Response model for upload events
export interface InboundEventUploadResponse {
  statusCode: string;
  message: string;
  messageId: string;
  shipId?: string;
}

//Response model for status of events
export interface EventStatusResponse {
  statusCode: string;
  message: string;
  messageId: string;
  eventUploadStatus?: string,
  eventUploadPacketStatus?: string,
  eventDownloadStatus?: string,
  eventDownloadPacketStatus?: string
}

// Inbound event data transfer object
export interface InboundEventDataTransfer {
  clientId: string;
  fileName: string;
  messageId?: string;
  messageType?: string;
  checksum?: string;
  size?: number;
  total?: number;
  seq?: number;
  algo?: string;
  createdtimeStamp?: number;
  buf: any;
}

// Resend event data transfer object
export interface ResendEventDataTransfer {
  clientId: string,
  messageId: string,
  uniqueId: string,
  missingPackets?: string
}

//Log event data transfer object
export interface LogEventDataTransfer {
  logTime: string,
  buf: any;
}

//Data for Cancellation cache object
export interface CancelledCacheData {
  shipId: string;
  clientId: string;
  messageId: string;
  uniqueId: string;
}