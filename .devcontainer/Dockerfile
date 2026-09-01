FROM mcr.microsoft.com/devcontainers/base:bookworm

# Java 17
RUN apt-get update \
    && apt-get install -y \
        openjdk-17-jdk \
        gradle \
        docker.io \
    && rm -rf /var/lib/apt/lists/*

# Node.js 22
RUN curl -fsSL https://deb.nodesource.com/setup_22.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g @angular/cli \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /workspaces/Flox