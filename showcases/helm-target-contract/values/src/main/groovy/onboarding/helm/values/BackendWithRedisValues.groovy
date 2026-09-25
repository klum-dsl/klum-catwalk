package onboarding.helm.values

import onboarding.helm.schema.PodinfoRelease

// Four declarative lines produce the complete backend values tree.
PodinfoRelease.Create.With('backend') {
    ui 'Backend with Redis'
    redis true
    resources '100m', '64Mi', '200m', '128Mi'
}
