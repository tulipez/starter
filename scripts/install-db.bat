@echo off

set dossier_PostgreSQL=C:\Program Files\PostgreSQL\13

@echo on

set PGPASSWORD=system
"%dossier_PostgreSQL%\bin\dropDB" -h localhost -p 5432 -U admin starter
"%dossier_PostgreSQL%\bin\createDB" -E UTF8 -h localhost -p 5432 -U admin -O admin starter

@echo off

pause