# File Upload App
![image](https://github.com/TytanMikJas/AWS-File-Uploader/assets/126624787/fc0c1fb2-42e8-44e7-b5d5-6f090eaf8930)

## Project Overview

The File Upload App is a web application that allows users to upload, edit, delete, and download files stored in AWS S3. It features a React front-end built with Vite and a backend powered by Spring Boot, using PostgreSQL for data persistence.

## Features

- **File Upload**: Users can upload files of any format to AWS S3 via the web interface.
- **File Management**: Users can view a list of uploaded files, edit file names, delete files, and download files.
- **Data Persistence**: File metadata such as file IDs, names, and S3 URLs are stored in PostgreSQL.
- **Responsive UI**: A user-friendly interface built with React.

## Technologies Used

- **Frontend**: React, Vite, Tailwind, Eslint, Zustand, Axios
- **Backend**: Spring Boot, Jpa, Hybernate
- **Database**: PostgreSQL + Docker Desktop
- **Storage**: AWS S3

# Instrukcja jak tego użyć dla fellow students
### Tutorial YouTube
- https://www.youtube.com/watch?v=PxO8L9s3xOw
- Należy w tutorialu zrobić wszystko poza (1:40 - 5:54), ponieważ mamy konta studenckie i nie jest nam potrzebny setup roles ani users.
- Aby zdobyć link do ARN należy przejść w AWS do Services -> IAM -> Roles -> LabRole i skopiować
- Należy dodać dodatkowo GET oraz DELETE analogicznie do tego jak chłop dodaje PUT, bo jest to wymagane do zadania.
![image](https://github.com/TytanMikJas/AWS-File-Uploader/assets/126624787/48bbf0b7-905b-4eba-be66-5c037d051584)

### Apka
#### Backend
- Otworzyć w IntelliJ, ja korzystam z ultimate oraz podłączam się do bazki w postgresie która działa na dockerku na porcie 5432
- Skonfigurować url do bazki w **src/main/resources/application.yml**.
- Następnie, aby móc na bierząco podglądać co się dzieje w bazce skonfigurować z prawej połączenie do database
![image](https://github.com/TytanMikJas/AWS-File-Uploader/assets/126624787/b771cd20-3dc8-4126-9b4b-5b7b7aeaf179)

#### Frontend
- npm i
- npm run dev
