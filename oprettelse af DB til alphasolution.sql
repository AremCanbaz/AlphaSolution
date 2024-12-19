CREATE SCHEMA alphasolution;

CREATE TABLE users (
  userid INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (userid),
  UNIQUE KEY email (email),
  UNIQUE KEY unique_username (username)
);

CREATE TABLE projects (
  projectid INT NOT NULL AUTO_INCREMENT,
  userid INT NOT NULL,
  projectname VARCHAR(255) NOT NULL,
  description TEXT,
  total_hours INT DEFAULT 0,
  is_completed TINYINT(1) DEFAULT 0,
  working_days INT DEFAULT NULL,
  PRIMARY KEY (projectid),
  KEY userid (userid),
  CONSTRAINT projects_ibfk_1 FOREIGN KEY (userid) REFERENCES users (userid)
);

CREATE TABLE subtasks (
  subtaskid INT NOT NULL AUTO_INCREMENT,
  projectid INT NOT NULL,
  subtaskname VARCHAR(255) NOT NULL,
  is_completed TINYINT(1) DEFAULT 0,
  total_hours INT DEFAULT 0,
  subtask_description VARCHAR(255) DEFAULT 'Ingen beskrivelse',
  working_days INT DEFAULT NULL,
  PRIMARY KEY (subtaskid),
  KEY projectid (projectid),
  CONSTRAINT subtasks_ibfk_1 FOREIGN KEY (projectid) REFERENCES projects (projectid) ON DELETE CASCADE
);

CREATE TABLE tasks (
  taskid INT NOT NULL AUTO_INCREMENT,
  subtaskid INT NOT NULL,
  taskname VARCHAR(255) NOT NULL,
  description TEXT,
  hours_spent INT NOT NULL,
  is_completed TINYINT(1) DEFAULT 0,
  working_days INT DEFAULT NULL,
  PRIMARY KEY (taskid),
  KEY subtaskid (subtaskid),
  CONSTRAINT tasks_ibfk_1 FOREIGN KEY (subtaskid) REFERENCES subtasks (subtaskid) ON DELETE CASCADE
);
