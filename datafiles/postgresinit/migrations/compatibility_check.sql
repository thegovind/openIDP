
SELECT 'Checking PostgreSQL version compatibility' as status;

SELECT extname, extversion FROM pg_extension WHERE extname = 'plpgsql';

SELECT DISTINCT data_type 
FROM information_schema.columns 
WHERE table_schema IN ('public', 'idpoauth')
ORDER BY data_type;

SELECT sequence_name, data_type, start_value, minimum_value, maximum_value, increment
FROM information_schema.sequences
WHERE sequence_schema IN ('public', 'idpoauth');

SELECT routine_name, routine_type, data_type
FROM information_schema.routines
WHERE routine_schema IN ('public', 'idpoauth');

SELECT 
    tc.table_name, 
    kcu.column_name, 
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name 
FROM information_schema.table_constraints AS tc 
JOIN information_schema.key_column_usage AS kcu
    ON tc.constraint_name = kcu.constraint_name
    AND tc.table_schema = kcu.table_schema
JOIN information_schema.constraint_column_usage AS ccu
    ON ccu.constraint_name = tc.constraint_name
    AND ccu.table_schema = tc.table_schema
WHERE tc.constraint_type = 'FOREIGN KEY'
    AND tc.table_schema IN ('public', 'idpoauth');

SELECT 'PostgreSQL 15 compatibility check completed successfully' as result;
