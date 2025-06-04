# PostgreSQL Upgrade Guide: 9.6.13 to 15.8

## Overview
This document outlines the upgrade process for PostgreSQL in the openIDP platform from version 9.6.13 to 15.8, including migration procedures and performance optimizations.

## Changes Made

### 1. Docker Image Update
- Updated PostgreSQL Docker image from `postgres:9.6.13` to `postgres:15.8` in `build.sh`

### 2. Database Migration Scripts
- Created migration directory: `datafiles/postgresinit/migrations/`
- Added pre-migration backup script: `pre_migration_backup.sh`
- Added post-migration validation script: `post_migration_validation.sql`
- Added PostgreSQL 15 optimization configuration: `postgresql15_optimizations.conf`

### 3. Connection Pool Optimizations
Enhanced connection pooling in all database context classes:
- **Dashboard Module**: `PostGreSqlDbContext.java` and `IDPPostGreSqlDbContext.java`
- **Scheduler Module**: `PostGreSqlDbContext.java`

#### New Connection Pool Settings:
- `maxTotal`: 50 (dashboard), 30 (scheduler)
- `maxIdle`: 20 (dashboard), 15 (scheduler)
- `minIdle`: 5 (dashboard), 3 (scheduler)
- `maxWaitMillis`: 30000ms
- Connection validation and eviction policies
- Abandoned connection detection and cleanup

### 4. Database Schema Updates
- Updated dump headers in all initialization scripts to reflect PostgreSQL 15.8
- Enhanced database initialization script with better logging and validation

## Migration Process

### Automatic Migration
The upgrade is designed to be seamless:
1. New containers will use PostgreSQL 15.8
2. Database initialization scripts are compatible with PostgreSQL 15
3. Connection pooling optimizations are applied automatically

### Manual Migration (if needed)
If migrating existing data:
1. Run pre-migration backup script
2. Export data from PostgreSQL 9.6
3. Import data into PostgreSQL 15
4. Run post-migration validation

## Performance Improvements

### Connection Pooling
- Increased maximum connections for better concurrency
- Added connection validation to prevent stale connections
- Implemented abandoned connection cleanup
- Added connection eviction policies for optimal resource usage

### PostgreSQL 15 Optimizations
- Optimized memory settings for container environment
- Enhanced WAL configuration for better write performance
- Improved query planner settings
- Added comprehensive logging for monitoring

## Verification Steps

1. **Database Startup**: Verify PostgreSQL 15.8 container starts successfully
2. **Schema Creation**: Confirm all three databases (IDP, IDP_PAAS, i2p) are created
3. **Application Connectivity**: Test all microservices can connect to the database
4. **Dashboard Functionality**: Verify Grafana dashboard displays metrics correctly
5. **Performance Testing**: Monitor connection pool usage and query performance

### Automated Testing
Run the automated test script to verify the upgrade:
```bash
chmod +x scripts/test_postgresql_upgrade.sh
./scripts/test_postgresql_upgrade.sh
```

### Manual Verification
1. Start the stack with new PostgreSQL version:
   ```bash
   docker-compose up postgres
   ```
2. Check logs for successful initialization
3. Connect to each database and verify tables exist
4. Test application connectivity

## Rollback Procedure

If issues occur:
1. Revert `build.sh` to use `postgres:9.6.13`
2. Restore from backup if data migration was performed
3. Remove connection pool optimizations if they cause issues

## Benefits

### PostgreSQL 15.8 Features
- Improved query performance and optimization
- Enhanced security features
- Better memory management
- Improved replication and backup capabilities

### Connection Pool Optimizations
- Better handling of concurrent connections
- Reduced connection overhead
- Improved resource utilization
- Enhanced monitoring and debugging capabilities

## Monitoring

Monitor the following after upgrade:
- Database connection counts
- Query performance metrics
- Connection pool statistics
- Application response times
- Grafana dashboard functionality

## Support

For issues related to the PostgreSQL upgrade:
1. Check application logs for connection errors
2. Monitor PostgreSQL logs for performance issues
3. Verify connection pool metrics
4. Review Grafana dashboard connectivity
