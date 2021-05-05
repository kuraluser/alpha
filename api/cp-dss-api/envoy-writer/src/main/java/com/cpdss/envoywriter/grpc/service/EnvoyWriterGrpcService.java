package com.cpdss.envoywriter.grpc.service;

import org.springframework.transaction.annotation.Transactional;


import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @Author jerin.g
 *
 */
@Log4j2
@GrpcService
@Transactional
public class EnvoyWriterGrpcService {

}
