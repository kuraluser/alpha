import { Entity, Column, PrimaryGeneratedColumn, Long } from 'typeorm';

@Entity({ name: 'outboundevent_store' })
export class OutBoundEventDataEntity {

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

  @Column({ nullable: true })
  process: string;

  @Column({ name: 'file_path' })
  filePath: string;

  @Column({ name: 'created_timestamp', type: 'bigint' })
  createdtimeStamp: bigint;

  @Column({ name: 'modified_timestamp', type: 'bigint', nullable: true })
  modifiedtimeStamp: bigint;

  @Column({ name: 'process_status', nullable: true })
  processStatus: string;

  @Column({ type: 'bigint', nullable: true })
  total: bigint;

  @Column({ type: 'bigint', nullable: true })
  partNumber: bigint;

  @Column({ name: 'missing_packets', nullable: true })
  missingPackets: string;

  @Column({ name: 'resend_read', nullable: true, default: null })
  resendRead: string;

  @Column({ name: 'resend_read_timestamp', type: 'bigint', nullable: true })
  resendReadTimeStamp: bigint;

  @Column({ name: 'verify_read', nullable: true, default: null })
  verifyRead: string;

  @Column({ name: 'verify_read_timestamp', type: 'bigint', nullable: true })
  verifyReadTimeStamp: bigint;

}