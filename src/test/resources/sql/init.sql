CREATE TABLE IF NOT EXISTS flash_card (
  id SERIAL PRIMARY KEY,
  english varchar(255) NOT NULL,
  translation varchar(255) NOT NULL,
  target_language varchar(64) NOT NULL,
  tries int NOT NULL DEFAULT 0,
  correct_answers int NOT NULL DEFAULT 0
);

INSERT INTO flash_card (id, english, translation, target_language, tries, correct_answers) VALUES
  (100, 'man', 'homme', 'FR', 0 , 0),
  (101, 'boy', 'garçon', 'FR', 0 , 0),
  (102, 'ten', 'dix', 'FR', 0 , 0),
  (103, 'sun', 'solei', 'FR', 0 , 0),
  (104, 'closed', 'ferme', 'FR', 0 , 0),
  (105, 'tree', 'arbol', 'FR', 0 , 0),
  (106, 'table', 'table', 'FR', 0 , 0),
  (107, 'door', 'porte', 'FR', 0 , 0),
  (108, 'cat', 'chat', 'FR', 0 , 0),
  (109, 'dog', 'chien', 'FR', 0 , 0),
  (110, 'up', 'sur', 'FR', 0 , 0),
  (111, 'black', 'nior', 'FR', 0 , 0);
