#!/bin/bash
set -e

echo "Rolling back PostgreSQL from 15.8 to 9.6.13..."

echo "WARNING: This will restore from backup and may result in data loss!"
echo "Make sure you have recent backups before proceeding."

BACKUP_DIR="/var/lib/postgresql/backup"

if [ ! -d "$BACKUP_DIR" ]; then
    echo "ERROR: Backup directory not found. Cannot rollback without backups."
    exit 1
fi

LATEST_BACKUP=$(ls -t $BACKUP_DIR/full_backup_*.sql | head -n1)

if [ -z "$LATEST_BACKUP" ]; then
    echo "ERROR: No backup files found. Cannot rollback."
    exit 1
fi

echo "Restoring from backup: $LATEST_BACKUP"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f "$LATEST_BACKUP"

echo "Rollback completed. Please verify data integrity."
