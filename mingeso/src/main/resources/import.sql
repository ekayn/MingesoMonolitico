INSERT INTO suppliers(code,category,name,retention) VALUES ('01537','A','Mesttle','No');
INSERT INTO suppliers(code,category,name,retention) VALUES ('02994','B','Krozi','Si');
INSERT INTO suppliers(code,category,name,retention) VALUES ('01332','C','Leches Lachen','No');
INSERT INTO suppliers(code,category,name,retention) VALUES ('01234','D','Pom Milk','Si');

INSERT INTO grease_and_solids(code,grease,solid) VALUES ('01537','20','15');
INSERT INTO grease_and_solids(code,grease,solid) VALUES ('02994','8','1');
INSERT INTO grease_and_solids(code,grease,solid) VALUES ('01332','35','18');
INSERT INTO grease_and_solids(code,grease,solid) VALUES ('01234','15','20');

INSERT INTO collections(supplier,date,milk,shift) VALUES ('01537','2023/03/18','20','T');
INSERT INTO collections(supplier,date,milk,shift) VALUES ('02994','2023/03/18','15','T');
INSERT INTO collections(supplier,date,milk,shift) VALUES ('01332','2023/03/19','55','M');
INSERT INTO collections(supplier,date,milk,shift) VALUES ('01234','2023/03/20','10','M');
INSERT INTO collections(supplier,date,milk,shift) VALUES ('01537','2023/03/22','15','T');
INSERT INTO collections(supplier,date,milk,shift) VALUES ('01234','2023/03/25','21','M');

INSERT INTO registers(code,milk,grease,solid) VALUES ('01537','1','0','0');
INSERT INTO registers(code,milk,grease,solid) VALUES ('02994','1','0','0');
INSERT INTO registers(code,milk,grease,solid) VALUES ('01332','1','0','0');
INSERT INTO registers(code,milk,grease,solid) VALUES ('01234','1','0','0');
