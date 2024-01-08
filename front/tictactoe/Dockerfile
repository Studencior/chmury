# Stage 1: Build the Angular application
FROM node:16 as build

# Set the working directory
WORKDIR /app 
COPY ./ /app 

# WORKDIR /app
# COPY package.json package-lock.json ./
RUN npm install
# COPY . .
RUN npm run build

# Stage 2: Serve the app using nginx
# FROM nginx:alpine
FROM nginx:latest 
COPY --from=build /app/dist/tictactoe /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
# EXPOSE 4200