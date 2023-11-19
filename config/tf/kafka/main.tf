provider "kafka" {
  bootstrap_servers = ["localhost:9092"]
}

resource "kafka_logs_topic" "logs" {
  name               = "logs"
  replication_factor = 1
  partitions         = 1

  config = {
    "segment.ms"     = "20000"
    "cleanup.policy" = "compact"
  }
}