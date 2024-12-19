INSERT INTO users (username, email, password)
VALUES
    ("jenshansen", "jens.hansen@a.com", "hemmeligkode123"),
    ("mariapedersen", "maria.pedersen@b.com", "stærkkode456"),
    ("peterlarsen", "peter.larsen@c.com", "kodeord789"),
    ("annesoerensen", "anne.soerensen@d.com", "sikker1234"),
    ("karlaandersen", "karla.andersen@e.com", "hemmelig5678");

INSERT INTO projects (userid, projectname, description, total_hours, is_completed, working_days)
VALUES
    (1, "Byg nyt hus", "Projekt for opførelse af et nyt hus.", 120, 0, 30),
    (2, "Renover køkken", "Komplet renovering af køkken.", 50, 1, 10),
    (3, "Lægning af fliser", "Projekt for fliselægning i haven.", 40, 0, 15),
    (4, "Installation af solceller", "Installation af solcelleanlæg.", 60, 1, 20),
    (5, "Malerarbejde", "Maling af hele huset.", 30, 0, 7);

INSERT INTO subtasks (projectid, subtaskname, is_completed, total_hours, subtask_description, working_days)
VALUES
    (1, "Grave fundament", 0, 20, "Gravning af fundament til huset.", 5),
    (1, "Rejse vægge", 0, 30, "Opstilling af vægge.", 10),
    (2, "Fjerne gamle skabe", 1, 10, "Fjernelse af de gamle køkkenelementer.", 2),
    (3, "Planlægning af fliser", 0, 5, "Planlægning af mønster og materialer.", 1),
    (4, "Montering af paneler", 1, 25, "Montering af solcellepaneler på taget.", 8);
INSERT INTO tasks (subtaskid, taskname, description, hours_spent, is_completed, working_days)
VALUES
    (1, "Leje maskiner", "Leje af gravemaskiner til fundament.", 5, 1, 2),
    (2, "Montere isolering", "Montering af isoleringsmaterialer i væggene.", 15, 0, 4),
    (3, "Bortskaffe affald", "Bortskaffelse af byggeaffald.", 8, 1, 1),
    (4, "Klip fliser", "Tilpasning af fliserne efter mål.", 3, 0, 1),
    (5, "Test solcellepaneler", "Test og kontrol af solcelleanlægget.", 10, 1, 2);