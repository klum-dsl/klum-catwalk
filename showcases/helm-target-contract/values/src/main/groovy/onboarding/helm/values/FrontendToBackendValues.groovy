package onboarding.helm.values

import onboarding.helm.schema.PodinfoRelease

// A Model is an executable configuration script, not a wrapper class.
PodinfoRelease.Create.With('frontend') {
    ui 'Frontend to backend'
    backend 'backend'
    ingress 'frontend.example.test'
    resources '50m', '64Mi'
}
