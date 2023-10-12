provider "kafka" {
  bootstrap_servers = ["localhost:9092"]
}

resource "kafka_topic" "logs" {
  name               = "systemd_logs"
  replication_factor = 2
  partitions         = 100

  config = {
    "segment.ms"     = "20000"
    "cleanup.policy" = "compact"
  }
}