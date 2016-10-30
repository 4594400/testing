DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `deadline` DATE NOT NULL,
  `priority` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'TODO',
  PRIMARY KEY (`id`));

INSERT INTO tasks (name, deadline, priority) VALUES
  ('Learn JAVA', '2015-01-01', 'URGENT'),
  ('Learn JSF', '2016-10-01', 'HIGH'),
  ('Learn Groovy', '2017-07-01', 'HIGH'),
  ('Learn JavaScript', '2017-10-01', 'HIGH'),
  ('Learn Angular 2', '2018-01-01', 'MEDIUM'),
  ('Learn Scala', '2019-01-01', 'MEDIUM'),
  ('Learn Ruby', '2020-08-01', 'LOW');

