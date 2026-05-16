terraform {
  required_version = ">= 1.5"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.80"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.25"
    }
  }
}

provider "azurerm" {
  features {}
}

variable "location" {
  default = "East US"
}

variable "cluster_name" {
  default = "onion-aks"
}

variable "app_image" {
  description = "ACR image URI for the application"
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

# Resource Group
resource "azurerm_resource_group" "onion" {
  name     = "${var.cluster_name}-rg"
  location = var.location
}

# AKS Cluster
resource "azurerm_kubernetes_cluster" "onion" {
  name                = var.cluster_name
  location            = azurerm_resource_group.onion.location
  resource_group_name = azurerm_resource_group.onion.name
  dns_prefix          = var.cluster_name

  default_node_pool {
    name       = "default"
    node_count = 2
    vm_size    = "Standard_B2s"

    auto_scaling_enabled = true
    min_count            = 2
    max_count            = 5
  }

  identity {
    type = "SystemAssigned"
  }

  network_profile {
    network_plugin = "azure"
    network_policy = "calico"
  }
}

# Kubernetes provider
provider "kubernetes" {
  host                   = azurerm_kubernetes_cluster.onion.kube_config[0].host
  client_certificate     = base64decode(azurerm_kubernetes_cluster.onion.kube_config[0].client_certificate)
  client_key             = base64decode(azurerm_kubernetes_cluster.onion.kube_config[0].client_key)
  cluster_ca_certificate = base64decode(azurerm_kubernetes_cluster.onion.kube_config[0].cluster_ca_certificate)
}

# Deploy app to AKS
module "k8s_app" {
  source = "../modules/k8s"

  app_image   = var.app_image
  db_username = var.db_username
  db_password = var.db_password
  jwt_secret  = var.jwt_secret
  replicas    = 2

  depends_on = [azurerm_kubernetes_cluster.onion]
}

output "cluster_name" {
  value = azurerm_kubernetes_cluster.onion.name
}

output "kube_config" {
  value     = azurerm_kubernetes_cluster.onion.kube_config_raw
  sensitive = true
}
