package onboarding.helm.values

import onboarding.helm.schema.PodinfoStack

// One executable Model script is the durable entry point for the complete stack.
PodinfoStack.Create.With {
    backend('backend') {
        ui 'Backend with Redis'
        redis true
        resources '100m', '64Mi', '200m', '128Mi'
    }
    frontend('frontend') {
        ui 'Frontend to backend'
        backend 'backend'
        ingress 'frontend.example.test'
        resources '50m', '64Mi'
    }
}
