variable "namespace" {
  default = "onion"
}

variable "app_image" {
  description = "Container image for the application"
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

variable "replicas" {
  default = 2
}

resource "kubernetes_namespace" "onion" {
  metadata {
    name = var.namespace
  }
}

resource "kubernetes_secret" "onion_secrets" {
  metadata {
    name      = "onion-secrets"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  data = {
    DB_USERNAME = var.db_username
    DB_PASSWORD = var.db_password
    JWT_SECRET  = var.jwt_secret
    VAULT_TOKEN = "disabled"
  }
}

resource "kubernetes_config_map" "onion_config" {
  metadata {
    name      = "onion-config"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  data = {
    DB_URL        = "jdbc:postgresql://postgres-service:5432/onion_db"
    VAULT_ENABLED = "false"
  }
}

resource "kubernetes_deployment" "postgres" {
  metadata {
    name      = "postgres"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  spec {
    replicas = 1

    selector {
      match_labels = { app = "postgres" }
    }

    template {
      metadata {
        labels = { app = "postgres" }
      }

      spec {
        container {
          name  = "postgres"
          image = "postgres:latest"

          port {
            container_port = 5432
          }

          env {
            name  = "POSTGRES_DB"
            value = "onion_db"
          }

          env {
            name = "POSTGRES_USER"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.onion_secrets.metadata[0].name
                key  = "DB_USERNAME"
              }
            }
          }

          env {
            name = "POSTGRES_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.onion_secrets.metadata[0].name
                key  = "DB_PASSWORD"
              }
            }
          }

          resources {
            requests = { memory = "256Mi", cpu = "250m" }
            limits   = { memory = "512Mi", cpu = "500m" }
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "postgres" {
  metadata {
    name      = "postgres-service"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  spec {
    selector = { app = "postgres" }

    port {
      port        = 5432
      target_port = 5432
    }
  }
}

resource "kubernetes_deployment" "app" {
  metadata {
    name      = "onion-app"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  spec {
    replicas = var.replicas

    selector {
      match_labels = { app = "onion-app" }
    }

    template {
      metadata {
        labels = { app = "onion-app" }
      }

      spec {
        container {
          name  = "onion-app"
          image = var.app_image

          port {
            container_port = 8080
          }

          env_from {
            config_map_ref {
              name = kubernetes_config_map.onion_config.metadata[0].name
            }
          }

          env {
            name = "DB_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.onion_secrets.metadata[0].name
                key  = "DB_USERNAME"
              }
            }
          }

          env {
            name = "DB_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.onion_secrets.metadata[0].name
                key  = "DB_PASSWORD"
              }
            }
          }

          env {
            name = "JWT_SECRET"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.onion_secrets.metadata[0].name
                key  = "JWT_SECRET"
              }
            }
          }

          resources {
            requests = { memory = "512Mi", cpu = "250m" }
            limits   = { memory = "1Gi", cpu = "1000m" }
          }

          liveness_probe {
            http_get {
              path = "/actuator/health"
              port = 8080
            }
            initial_delay_seconds = 60
            period_seconds        = 30
          }

          readiness_probe {
            http_get {
              path = "/actuator/health"
              port = 8080
            }
            initial_delay_seconds = 30
            period_seconds        = 10
          }
        }
      }
    }
  }

  depends_on = [kubernetes_deployment.postgres]
}

resource "kubernetes_service" "app" {
  metadata {
    name      = "onion-service"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  spec {
    selector = { app = "onion-app" }

    port {
      port        = 80
      target_port = 8080
    }

    type = "LoadBalancer"
  }
}

resource "kubernetes_horizontal_pod_autoscaler_v2" "app" {
  metadata {
    name      = "onion-hpa"
    namespace = kubernetes_namespace.onion.metadata[0].name
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.app.metadata[0].name
    }

    min_replicas = 2
    max_replicas = 10

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 70
        }
      }
    }
  }
}
