
SELECT 'Database connectivity test' as test_name, version() as postgresql_version;

SELECT datname FROM pg_database WHERE datname IN ('IDP', 'IDP_PAAS', 'i2p');

\c IDP
SELECT 'IDP' as database_name, count(*) as table_count FROM information_schema.tables WHERE table_schema = 'public';

\c IDP_PAAS
SELECT 'IDP_PAAS' as database_name, count(*) as table_count FROM information_schema.tables WHERE table_schema = 'public';

\c i2p
SELECT 'i2p' as database_name, count(*) as table_count FROM information_schema.tables WHERE table_schema = 'public';

\c IDP
SELECT count(*) as application_count FROM tapplication_info;
SELECT count(*) as pipeline_count FROM tpipeline_info;

\c i2p
SELECT count(*) as app_info_count FROM appinfo;
SELECT count(*) as build_info_count FROM buildinfo;

SELECT extname FROM pg_extension WHERE extname = 'plpgsql';

SELECT 'Migration validation completed successfully' as status;
