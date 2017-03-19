#!/usr/bin/env bash
#
# Copyright (c) 2017 Michael Schoene.
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
set -o pipefail
set -o nounset

readonly PROGNAME=$(basename "$0")

function usage_exit {
    echo "usage: $PROGNAME [<MySQL root password>]" 1>&2
    exit "$1"
}

function error_exit {
    local errmsg="$1"
    local errno="$2"

    echo "${PROGNAME}: $errmsg" 1>&2
    exit "$errno"
}

function cleanup_on_exit {
   if [ -n "$MYSQL_OPTION_FILE" ]; then rm -f "$MYSQL_OPTION_FILE"; fi
}

function evaluate_cmdline {
    if [ "$#" -gt "1" ]; then usage_exit 1; fi

    if [ "$#" -eq "1" ]; then
        MYSQL_ROOT_PWD="$1"
    else
        echo "MySQL root password: "
        read -r -s MYSQL_ROOT_PWD
    fi
}

function create_MYSQL_OPTION_FILE {
    local db_user="$1"
    local db_pwd="$2"
    local db_database="$3"
    local db_hostname="$4"

    MYSQL_OPTION_FILE=$(mktemp)
    local rc=$?
    if [ $rc != 0 ]; then error_exit "Creating MySQL client option file failed." $rc; fi

    {
    echo "[client]"
    echo "user = $db_user"
    echo "password = $db_pwd"
    echo "database = $db_database"
    echo "host = $db_hostname"
    } >> "$MYSQL_OPTION_FILE"
}

function create_database {
    for sqlScript in \
        ./create_db_mysql.sql \
        ./create_schema_mysql.sql \
        ./create_initial_entries.sql
    do
        echo "Execute $sqlScript"
        mysql --defaults-extra-file="$MYSQL_OPTION_FILE" < "$sqlScript"
        local rc=$?
        if [ $rc != 0 ]; then error_exit "Creating database failed." $rc; fi
    done
}

#
# Main
#
evaluate_cmdline "$@"

trap cleanup_on_exit EXIT

create_MYSQL_OPTION_FILE "root" "$MYSQL_ROOT_PWD" "power" "localhost"

create_database

echo "Created 'power' database."
