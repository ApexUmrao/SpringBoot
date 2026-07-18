package com.apex.kafka.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	@Value("${TopicName}")
	private String topicName;
	
	@Value("${Partition}")
	private int partition;
	
	@Value("${Replicas}")
	private int replicas;
	
	@Value("${TopicNameJSON}")
	private String topicNameJSON;
	
	@Value("${PartitionJSON}")
	private int partitionJSON;
	
	@Value("${ReplicasJSON}")
	private int replicasJSON;
	
	@Bean
	public NewTopic createTopic() {
		return TopicBuilder.name(topicName)
							.partitions(partition)
							.replicas(replicas).build();	
	}
	
	@Bean
	public NewTopic createTopicJSON() {
		return TopicBuilder.name(topicNameJSON)
							.partitions(partitionJSON)
							.replicas(replicasJSON).build();	
	}
}
