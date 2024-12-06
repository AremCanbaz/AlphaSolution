create schema alphasolution;

create table users (
    UserId int auto_increment primary key,    -- Id for hver bruger
    UserName varchar(255) not null,           -- brugernavn
    Email varchar(255) not null unique,       -- email-adresse
    Passwordd varchar(255) not null,       -- kodeord
    CreatedAt timestamp default current_timestamp -- oprettelse
);

create table projects (
    ProjectId int auto_increment primary key, -- id nummer for projektet
    UserId int not null,					-- foreign key
    ProjectName varchar(255) not null,       -- navn på projektet
    Description text,                        -- beskrivelse af projektet
    TotalHours int default 0,      -- samlede timer for projektet
    IsCompleted oolean default false,
    Foreign key (userid) references users(userid)
);
create table subtasks (
    SubtaskId int auto_increment primary key, -- id forunderopgave
    ProjectId int not null,                   -- foreign key for projektet
    SubTaskName varchar(255) not null,        -- navn på opgaven
    IsCompleted boolean default false,        -- status: om opgaven 
    TotalHours int default 0,      -- samlede timer for subtask
    SubtaskDescription text,
    foreign key (projectid) references projects(projectid) on delete cascade
);

create table tasks (
    TaskId int auto_increment primary key,    -- id for opgave
    SubtaskId int not null,                   -- foreign key til underopgaven
    TaskName varchar(255) not null,           -- navn på opgaven
    Description text,                         -- beskrivelse af opgaven
    HoursSpent int not null,        -- timer brugt på opgaven
    IsCompleted boolean default false,        -- status: om opgaven
    foreign key (SubtaskId) references subtasks(SubtaskId) on delete cascade
);

