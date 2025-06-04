#!/bin/bash
set -e

echo "Starting PostgreSQL 9.6 to 15 pre-migration backup..."

BACKUP_DIR="/var/lib/postgresql/backup"
mkdir -p $BACKUP_DIR

echo "Creating backup of all databases..."
pg_dumpall --host=localhost --port=5432 --username=postgres --clean --file=$BACKUP_DIR/full_backup_$(date +%Y%m%d_%H%M%S).sql

echo "Creating individual database backups..."
pg_dump --host=localhost --port=5432 --username=postgres --format=custom --file=$BACKUP_DIR/IDP_backup_$(date +%Y%m%d_%H%M%S).dump IDP
pg_dump --host=localhost --port=5432 --username=postgres --format=custom --file=$BACKUP_DIR/IDP_PAAS_backup_$(date +%Y%m%d_%H%M%S).dump IDP_PAAS
pg_dump --host=localhost --port=5432 --username=postgres --format=custom --file=$BACKUP_DIR/i2p_backup_$(date +%Y%m%d_%H%M%S).dump i2p

echo "Pre-migration backup completed successfully"
