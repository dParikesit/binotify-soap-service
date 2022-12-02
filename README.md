# BINOTIFY SOAP SERVICE

Aplikasi ini dibuat untuk pemenuhan Tugas Besar 2 IF3110 Web Based Development tahun 2022/2023.
Aplikasi ini digunakan sebagai SOAP endpoint.

## Requirement
- Docker dan docker-compose 3.9
- Java 8 (jika tidak pakai docker)
- Maven (jika tidak pakai docker)

## Installation
1. Install docker dan docker-compose pada komputer. Panduan instalasi docker dan docker compose pada repo binotify-config
2. Clone repository dan masuk ke folder
```
git clone https://gitlab.informatika.org/if3110-2022-k01-02-20/binotify-soap-service.git
cd binotify-soap-service
```

#### Development
```
# DOCKER
# Masuk ke repo binotify-config
docker compose up

# TANPA DOCKER
mvn -B dependency:resolve
mvn -B clean package
java -jar target/binotify-soap.jar
```

#### Deskripsi Singkat
Web service merupakan aplikasi yang berisi sekumpulan basis data (database) dan perangkat lunak (software) atau bagian dari program perangkat lunak yang diakses secara remote oleh piranti dengan perantara tertentu. SOAP (Simple Object Access Protocol) adalah standar untuk bertukar pesan-pesan berbasis XML melalui jaringan komputer atau sebuah jalan untuk program yang berjalan pada suatu sistem operasi (OS) untuk berkomunikasi dengan program pada OS yang sama maupun berbeda dengan menggunakan HTTP dan XML sebagai mekanisme untuk pertukaran data, maka SOAP dapat berkomunikasi dengan berbagai aplikasi meskipun terdapat perbedaan sistem operasi, teknologi, dan bahasa pemrogramannya. 

#### Database
```
logs
log_id SERIAL PRIMARY KEY,
ip_addr VARCHAR(32) NOT NULL,
endpoint VARCHAR(256) NOT NULL,
description VARCHAR(256) NOT NULL,
requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

subs
creator_id INT NOT NULL,
subscriber_id INT NOT NULL,
status ENUM("PENDING", "ACCEPTED", "REJECTED") DEFAULT "PENDING" NOT NULL,
CONSTRAINT PK_Subs PRIMARY KEY (creator_id, subscriber_id)

apikey
key_id SERIAL PRIMARY KEY,
api_key CHAR(128) NOT NULL,
client_type ENUM("REST", "FRONTEND") NOT NULL
```

## Endpoint
```
URL     : `http://localhost:3003/`
WSDL    : `http://localhost:3003/subs?wsdl`

Method:
1. subscribe
2. getPending
3. accept
4. reject
5. getSubStatus
6. getSubStatusBatch
```

## Pembagian Kerja

### SOAP
Feature | NIM
--- | ---
Request Subscription | 13520087, 13520058, 13520057
Check Request | 13520087
Security | 13520087
Database | 13520087
Approval Status Subscription | 13520087
```
P.S : NIM Pertama pada tabel merupakan penanggung jawab serta pembuat fitur utama
```

## Bagian Bonus
- Docker (13520087)

## Author
NIM | NAMA
--- | ---
13520057 | Marcellus Michael Herman Kahari
13520058 | Kristo Abdi Wiguna
13520087 | Dimas Shidqi Parikesit