/* Licensed at AlphaOri Technologies */
package com.cpdss.envoywriter.grpc.service;

import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Transactional
public class EnvoyWriterGrpcService {}
