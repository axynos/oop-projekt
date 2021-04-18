# Database Setup Instructions

NB! This README is a work in progress and so are the database creation scripts, there is a high chance that this guide won't work for you.

# Introduction

The oop todo app backend is built to use PostgreSQL as the database and Postgraphile as the middleware to create GraphQL endpoints for the backend.

# Installation

This setup is tested with PostgreSQL running on Debian linux (both server and on a Raspberry Pi). The production database currently runs on a DigitalOcean droplet.

## Instructions

### Step 1 - Install PostgreSQL

To install the latest PostgreSQL on Debian, you can follow [this](https://linuxize.com/post/how-to-install-postgresql-on-debian-10/) guide.

In short, it boils down to this:

1. Update the APT package index.

```sh
$ sudo apt update
```

2. Install PostgreSQL

```sh
$ sudo apt install postgresql postgresql-contrib
```

3. Verify installation.

```sh
$ sudo -u postgres psql -c "SELECT version();"
```

If the installation is valid, it will print out the version of PostgreSQL installed on the system.

### Step 2 - Open PostgreSQL up to outside connections.

If you want to host the backend and the database on separate hosts, you need to tell PostgreSQL to listen to outside connections.
To do this, you need to modify the `postgresql.conf` file and the `pg_hba.conf` file.

1. Set up listening addresses in `postgresql.conf`

Open `/etc/postgresql/<your postgresql version>/main/postgresql.conf` with your favourite text editor and modify it to listen to all addresses.

```
#------------------------------------------------------------------------------
# CONNECTIONS AND AUTHENTICATION
#------------------------------------------------------------------------------

# - Connection Settings -

listen_addresses = '*'     # what IP address(es) to listen on;
```

2. Set up remote logins in `pg_hba.conf`

Open `/etc/postgresql/<your postgresql version>/main/pg_hba.conf` with your favourite text editor and modify it to listen to all/your desired addresses.

```
# Remote connections
host    all             oopdb             0.0.0.0/0               md5
```

Note: opening the server up to everything on the internet is not recommended!

3. Restart the PostgreSQL service.

```sh
sudo service postgresql restart
```

4. Verify that everything is set up correctly with the `ss` utility.

```sh
$ ss -nlt | grep 5432
```

It should show something like the following.

```
LISTEN   0         128                 0.0.0.0:5432             0.0.0.0:*
LISTEN   0         128                    [::]:5432                [::]:*
```

# Step 3 - Prepare a directory for the tablespace.

For the tablespace to be created, we need a directory it can be created in.

1. Create the tablespace directory.

```sh
$ sudo mkdir -p /data/oopspace
```

The `-p` flag tells `mkdir` to create parent directories if they do not already exist.

2. Give the ownership to the `postgres` user.

```sh
$ sudo chown postgres /data/oopspace
```

# Step 4 - Run the database setup script.

If everything is set up correctly, you should be able to run the setup script without issues.

```sh
$ ./create-database.sh
```
