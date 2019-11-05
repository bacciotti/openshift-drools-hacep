/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.hacep.consumer;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.hacep.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieContainerUtils {

    private static final Logger logger = LoggerFactory.getLogger(KieContainerUtils.class);

    public static KieContainer getKieContainer(EnvConfig envConfig, KieServices srv){
        KieContainer kieContainer;
        if(srv != null) {
            if (envConfig.isUpdatableKJar()) {
                if (logger.isInfoEnabled()) {
                    logger.info("Creating new KieContainer with KJar:{}", envConfig.getKJarGAV());
                }
                kieContainer = srv.newKieContainer(GAVUtils.getReleaseID(envConfig.getKJarGAV(), srv));
                KieScanner scanner = srv.newKieScanner(kieContainer);
                scanner.scanNow();
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("Creating new Kie Session with the Kjar deployed");
                }
                kieContainer = KieServices.get().newKieClasspathContainer();
            }
            return kieContainer;
        }else{
            throw new RuntimeException("KieServices is null");
        }
    }
}
