#!/bin/bash

# Function to clean up processes on exit
cleanup() {
  echo "Stopping servers..."

  # Use pkill to target the process groups of the named commands.
  # This is more robust than relying on the PIDs of the parent shell commands.
  pkill -P $CLIENT_PID
  pkill -P $SERVER_PID

  echo "Servers stopped."
}

# Function to start both servers
start() {
  # Register cleanup to execute on script exit
  trap cleanup EXIT SIGINT SIGTERM

  # Start the Next.js client
  echo "Starting Next.js client..."
  cd client
  npm run dev & CLIENT_PID=$!
  cd ..

  # Start the Spring Boot server
  echo "Starting Spring Boot server..."
  cd server
  mvn spring-boot:run & SERVER_PID=$!
  cd ..

  # Wait for both servers to exit
  wait $CLIENT_PID $SERVER_PID
}

# Export the start function so it's available in the current shell
export -f start
