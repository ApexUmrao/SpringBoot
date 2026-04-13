# ─────────────────────────────────────────────
# Stage 1 — Build the Vite bundle
# ─────────────────────────────────────────────
FROM node:24-alpine AS builder

WORKDIR /app

# Install dependencies before copying source (layer cache)
COPY package.json package-lock.json ./
RUN npm ci --silent

# Accept the backend URL at build time
# Vite bakes VITE_* variables into the bundle at build time
ARG VITE_BACK_END_URL
ARG VITE_STRIPE_PUBLISHABLE_KEY
ARG VITE_FRONTEND_URL

ENV VITE_BACK_END_URL=$VITE_BACK_END_URL
ENV VITE_STRIPE_PUBLISHABLE_KEY=$VITE_STRIPE_PUBLISHABLE_KEY
ENV VITE_FRONTEND_URL=$VITE_FRONTEND_URL

COPY . .
RUN npm run build

# ─────────────────────────────────────────────
# Stage 2 — Serve with Nginx
# ─────────────────────────────────────────────
FROM nginx:1.27-alpine

# Remove the default Nginx page
RUN rm /etc/nginx/conf.d/default.conf

# Custom config: proxy /api → backend, serve React on /
COPY nginx.conf /etc/nginx/conf.d/app.conf

# Copy the built bundle
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]