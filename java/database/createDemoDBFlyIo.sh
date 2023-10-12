#!/bin/bash
export PGPASSWORD='postgres1'
export PGPORT=15432
BASEDIR=$(dirname $0)
DATABASE=pizza_ordering
psql -U postgres -p $PGPORT -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres -p $PGPORT $DATABASE &&
psql -U postgres -p $PGPORT -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -p $PGPORT -d $DATABASE -f "$BASEDIR/data.sql" &&
psql -U postgres -p $PGPORT -d $DATABASE -f "$BASEDIR/testOrders.sql" &&
psql -U postgres -p $PGPORT -d $DATABASE -f "$BASEDIR/user.sql"