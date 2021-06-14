import { Entity, Column, PrimaryGeneratedColumn, Long } from 'typeorm';

@Entity({ name: 'inboundevent_store' })
export class InBoundEventDataEntity {

  @PrimaryGeneratedColumn({ type: 'bigint' })
  id: bigint;

  @Column({ name: 'client_id' })
  clientId: string;

  @Column({ name: 'message_id' })
  messageId: string;

  @Column({ name: 'message_type' })
  messageType: string;

  @Column({ type: 'bigint' })
  sequence: bigint;

  @Column({ type: 'bigint' })
  size: bigint;

  @Column({ name: 'ship_id' })
  shipId: string;

  @Column({ name: 'unique_id' })
  uniqueId: string;

  @Column({ name: 'file_name' })
  fileName: string;

  @Column()
  checksum: string;

  @Column()
  algo: string;

  @Column()
  split: string;

  @Column()
  process: string;

  @Column({ name: 'file_path' })
  filePath: string;

  @Column({ name: 'created_timestamp', type: 'bigint' })
  createdtimeStamp: bigint;

  @Column({ name: 'modified_timestamp', type: 'bigint', nullable: true })
  modifiedtimeStamp: bigint;

  @Column({ name: 'process_status' })
  processStatus: string;

  @Column({ name: 'start_index', nullable: true })
  startIndex: string;

  @Column({ type: 'bigint', nullable: true })
  total: bigint;

  @Column({ name: 'part_number', nullable: true })
  partNumber: string;

  @Column({ name: 'missing_packets', nullable: true })
  missingPackets: string;

  @Column({ name: 'split_read', nullable: true })
  splitRead: string;

  @Column({ name: 'split_read_timestamp', type: 'bigint', nullable: true })
  splitReadTimeStamp: bigint;

  @Column({ name: 'transport_read', nullable: true, default: null })
  transportRead: string;

  @Column({ name: 'transport_read_timestamp', type: 'bigint', nullable: true })
  transportReadTimeStamp: bigint;

  @Column({ name: 'finish_read', nullable: true, default: null })
  finishRead: string;

  @Column({ name: 'finish_read_timestamp', type: 'bigint', nullable: true })
  finishReadTimeStamp: bigint;

  @Column({ name: 'confirm_read', nullable: true, default: null })
  confirmRead: string;

  @Column({ name: 'confirm_read_timestamp', type: 'bigint', nullable: true })
  confirmReadTimeStamp: bigint;

  @Column({ name: 'cancel_read', nullable: true, default: null })
  cancelRead: string;

  @Column({ name: 'cancel_read_timestamp', type: 'bigint', nullable: true })
  cancelReadTimeStamp: bigint;
  
}