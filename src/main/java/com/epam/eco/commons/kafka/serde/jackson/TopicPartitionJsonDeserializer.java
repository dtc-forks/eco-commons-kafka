/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.commons.kafka.serde.jackson;

import java.io.IOException;

import org.apache.kafka.common.TopicPartition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Raman_Babich
 */
public class TopicPartitionJsonDeserializer extends StdDeserializer<TopicPartition> {

    private static final long serialVersionUID = 1L;

    public TopicPartitionJsonDeserializer() {
        super(TopicPartition.class);
    }

    @Override
    public TopicPartition deserialize(
            JsonParser jsonParser,
            DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node == null || node.isNull()) {
            return null;
        }

        String topic =
                JsonDeserializerUtils.readFieldAsString(node, TopicPartitionFields.TOPIC, false, ctxt);
        Integer partition =
                JsonDeserializerUtils.readFieldAsInteger(node, TopicPartitionFields.PARTITION, false, ctxt);
        return new TopicPartition(topic, partition);
    }

}
