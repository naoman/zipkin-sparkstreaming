/**
 * Copyright 2017 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.autoconfigure.sparkstreaming;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.sparkstreaming.MessageStreamFactory;
import zipkin.sparkstreaming.SpanProcessor;
import zipkin.sparkstreaming.SparkStreamingJob;
import zipkin.sparkstreaming.TraceConsumer;

@Configuration
@EnableConfigurationProperties(ZipkinSparkStreamingProperties.class)
@ConditionalOnBean({MessageStreamFactory.class, TraceConsumer.class, SpanProcessor.class})
public class ZipkinSparkStreamingAutoConfiguration {

  @Bean
  SparkStreamingJob sparkStreaming(ZipkinSparkStreamingProperties sparkStreaming,
                                   MessageStreamFactory messageStreamFactory,
                                   TraceConsumer traceConsumer,
                                   SpanProcessor spanProcessor) {
    return sparkStreaming.toBuilder()
        .spanMessagesFactory(messageStreamFactory)
        .traceConsumer(traceConsumer)
        .spanProcessor(spanProcessor)
        .build()
        .start();
  }
}
