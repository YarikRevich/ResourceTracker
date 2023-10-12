variable "resourcetracker_aci_container_location" {
  default    = ""
  description = "Location of resourcetracker-aci-container"
}

variable "resourcetracker_aci_container_dns_name" {
  default    = ""
  description = "DNS name of resourcetracker-aci-container"
}

variable "resourcetracker_context" {
  default     = ""
  description = "Context gotten from CLI as a data to be processed in a remote resource"
}
