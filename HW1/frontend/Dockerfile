FROM node:lts-alpine AS build

WORKDIR /app

COPY package.json package.json
RUN npm install

COPY . .
RUN npm run build

FROM build AS base

WORKDIR /app

COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public

CMD ["npm", "start"]