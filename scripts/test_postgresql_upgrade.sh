#!/bin/bash
set -e

echo "Testing PostgreSQL 15.8 upgrade for openIDP..."

if ! docker info > /dev/null 2>&1; then
    echo "ERROR: Docker is not running"
    exit 1
fi

echo "Building PostgreSQL 15 Docker image..."
cd datafiles
docker build -f Dockerfile.postgres -t idp-postgres:15.8 .
cd ..

echo "Testing PostgreSQL 15.8 startup..."
docker run --rm -d \
    --name test-postgres-15 \
    -e POSTGRES_DB=postgres \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=admin \
    -v $(pwd)/datafiles/postgresinit:/docker-entrypoint-initdb.d \
    idp-postgres:15.8

echo "Waiting for database to initialize..."
sleep 30

echo "Testing database connectivity..."
docker exec test-postgres-15 psql -U postgres -d postgres -c "SELECT version();"

echo "Testing database creation..."
docker exec test-postgres-15 psql -U postgres -d postgres -c "\l"

echo "Testing table creation..."
docker exec test-postgres-15 psql -U postgres -d IDP -c "\dt"
docker exec test-postgres-15 psql -U postgres -d IDP_PAAS -c "\dt"
docker exec test-postgres-15 psql -U postgres -d i2p -c "\dt"

echo "Testing performance monitoring..."
docker exec test-postgres-15 psql -U postgres -d postgres -c "SELECT * FROM connection_stats LIMIT 1;"

echo "Cleaning up test container..."
docker stop test-postgres-15

echo "PostgreSQL 15.8 upgrade test completed successfully!"
