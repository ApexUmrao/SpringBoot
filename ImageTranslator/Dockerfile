# ══════════════════════════════════════════════════════════
# FILE 1: Dockerfile  (place in project root folder)
# ══════════════════════════════════════════════════════════

# ── Stage 1: Build ────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml first (cached layer — only re-downloads deps if pom changes)
COPY pom.xml .


# This is the biggest speedup — ~/.m2 is reused between docker build runs
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:resolve dependency:resolve-plugins -B -q

# Copy source and build
COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests -B -q \
    -Dmaven.javadoc.skip=true \
    -Dmaven.source.skip=true

# ── Stage 2: Run ──────────────────────────────────────────
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Install Hindi/Devanagari + Unicode fonts
RUN apt-get update && apt-get install -y --no-install-recommends \
    # Noto fonts — best Unicode coverage (Hindi, Arabic, Chinese, Japanese, Korean)
    fonts-noto \
    fonts-noto-core \
    fonts-noto-extra \
    # Specifically for Devanagari (Hindi)
    fonts-noto-ui-core \
    fonts-deva \
    fonts-lohit-deva \
    # General Unicode fallback
    fonts-liberation \
    fontconfig \
    && fc-cache -fv \
    && rm -rf /var/lib/apt/lists/*

# Create output directory inside container
RUN mkdir -p /app/translated-images

# Copy built JAR from builder stage
COPY --from=builder /app/target/ImageTranslator.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


# ══════════════════════════════════════════════════════════
# FILE 2: docker-compose.yml  (place in project root folder)
# ══════════════════════════════════════════════════════════
# version: '3.8'
#
# services:
#   image-translator:
#     build: .
#     container_name: image-translator
#     ports:
#       - "8080:8080"
#     environment:
#       # ✅ Replace with your real OpenAI key
#       - SPRING_AI_OPENAI_API_KEY=sk-YOUR_OPENAI_API_KEY_HERE
#       - APP_TARGET_LANGUAGE=Hindi
#       - APP_OUTPUT_DIRECTORY=/app/translated-images
#     volumes:
#       # ✅ Maps container output folder to your Windows folder
#       # Change left side to your desired Windows path
#       - C:/Users/shivanshu.umrao/translated-images:/app/translated-images
#     restart: unless-stopped


# ══════════════════════════════════════════════════════════
# FILE 3: .dockerignore  (place in project root folder)
# ══════════════════════════════════════════════════════════
# target/
# .mvn/
# .git/
# .gitignore
# *.md
# Dockerfile
# docker-compose.yml