cd C:\DEV\project-appointBack\flyio
del fly.toml
flyctl launch --name "projectappoint" --org "Marcin" --region "waw" --build-only --release-command-timeout "3"
