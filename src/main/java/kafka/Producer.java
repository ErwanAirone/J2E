package kafka;

/*
1 topic comporte plusieurs partitions,
chaque partition peut être assigné (tête de lecture) à un seul consumer du consumer group
un consumer group lit toutes les partitions du topic en se les répartissant au sein gu groupe
plusieurs consumer group peuvent lire les mêmes partitions au sein d'un topic
s'il y a plus de consumer que de partition, il sera inactif
 */

/* ARCHI
producer (publish(topic, message))
topic (id, list(partition) - publish(message) -> choisir partition)
partition (id, offset, list(message))
consumer group (id, list(consumer) - sort()) -> consumer (id, partition_id - subscribe(topic), poll(nb, timeout))
 */

public class Producer {
}
