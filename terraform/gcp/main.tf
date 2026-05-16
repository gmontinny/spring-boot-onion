terraform {
  required_version = ">= 1.5"

  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.10"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.25"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

variable "project_id" {
  description = "GCP Project ID"
}

variable "region" {
  default = "us-central1"
}

variable "cluster_name" {
  default = "onion-gke"
}

variable "app_image" {
  description = "GCR/Artifact Registry image URI for the application"
}

variable "db_username" {
  sensitive = true
}

variable "db_password" {
  sensitive = true
}

variable "jwt_secret" {
  sensitive = true
}

# VPC
resource "google_compute_network" "onion" {
  name                    = "${var.cluster_name}-vpc"
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "onion" {
  name          = "${var.cluster_name}-subnet"
  ip_cidr_range = "10.0.0.0/24"
  region        = var.region
  network       = google_compute_network.onion.id

  secondary_ip_range {
    range_name    = "pods"
    ip_cidr_range = "10.1.0.0/16"
  }

  secondary_ip_range {
    range_name    = "services"
    ip_cidr_range = "10.2.0.0/20"
  }
}

# GKE Cluster
resource "google_container_cluster" "onion" {
  name     = var.cluster_name
  location = var.region

  network    = google_compute_network.onion.name
  subnetwork = google_compute_subnetwork.onion.name

  initial_node_count       = 1
  remove_default_node_pool = true

  ip_allocation_policy {
    cluster_secondary_range_name  = "pods"
    services_secondary_range_name = "services"
  }
}

resource "google_container_node_pool" "default" {
  name       = "default-pool"
  location   = var.region
  cluster    = google_container_cluster.onion.name
  node_count = 2

  autoscaling {
    min_node_count = 2
    max_node_count = 5
  }

  node_config {
    machine_type = "e2-medium"
    disk_size_gb = 30

    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}

# Kubernetes provider
data "google_client_config" "default" {}

provider "kubernetes" {
  host                   = "https://${google_container_cluster.onion.endpoint}"
  token                  = data.google_client_config.default.access_token
  cluster_ca_certificate = base64decode(google_container_cluster.onion.master_auth[0].cluster_ca_certificate)
}

# Deploy app to GKE
module "k8s_app" {
  source = "../modules/k8s"

  app_image   = var.app_image
  db_username = var.db_username
  db_password = var.db_password
  jwt_secret  = var.jwt_secret
  replicas    = 2

  depends_on = [google_container_node_pool.default]
}

output "cluster_endpoint" {
  value = google_container_cluster.onion.endpoint
}

output "cluster_name" {
  value = google_container_cluster.onion.name
}
