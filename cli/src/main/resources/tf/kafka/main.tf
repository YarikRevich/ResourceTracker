provider "kafka" {
  bootstrap_servers = ["localhost:9092"]
}

resource "kafka_logs_topic" "logs" {
  name               = "systemd_logs"
  replication_factor = 1
  partitions         = 100

  config = {
    "segment.ms"     = "20000"
    "cleanup.policy" = "compact"
  }
}

resource "kafka_status_topic" "status" {
  name               = "systemd_logs"
  replication_factor = 1
  partitions         = 100

  config = {
    "segment.ms"     = "20000"
    "cleanup.policy" = "compact"
  }
}