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
package org.kie.hacep.core.infra;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import static org.junit.Assert.*;
import org.junit.Test;
import org.kie.hacep.EnvConfig;
import org.kie.hacep.core.infra.DefaultSessionSnapShooter;
import org.kie.hacep.core.infra.SessionSnapshooter;
import org.kie.hacep.core.infra.SnapshotInfos;
import org.kie.hacep.core.infra.utils.SnapshotOnDemandUtils;

public class SnapshotOnDemandUtilsTest {

    @Test(expected = org.apache.kafka.common.KafkaException.class)
    public void askAKafkaConsumerWithoutServerUpTest(){
        EnvConfig config = EnvConfig.getDefaultEnvConfig();
        config.local(false);
        config.underTest(false);
        KafkaConsumer consumer = SnapshotOnDemandUtils.getConfiguredSnapshotConsumer(config);
        assertNull(consumer);
    }

    @Test(expected = org.apache.kafka.common.KafkaException.class)
    public void askASnapshotWithoutServerUTest(){
        EnvConfig config = EnvConfig.getDefaultEnvConfig();
        config.local(false);
        config.underTest(false);
        SessionSnapshooter sessionSnapshooter = new DefaultSessionSnapShooter(config);
        SnapshotInfos infos = SnapshotOnDemandUtils.askASnapshotOnDemand(config, sessionSnapshooter );
        assertNull(infos);
    }
}