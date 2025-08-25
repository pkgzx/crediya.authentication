CREATE TABLE IF NOT EXISTS "Permission" (
    id BIGSERIAL PRIMARY KEY,
    resource VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "Role" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS "RolePermission" (
    id BIGSERIAL PRIMARY KEY,
    roleId BIGINT NOT NULL REFERENCES "Role"(id) ON DELETE CASCADE,
    permissionId BIGINT NOT NULL REFERENCES "Permission"(id) ON DELETE CASCADE,
    UNIQUE(roleId, permissionId)
);

CREATE TABLE IF NOT EXISTS "TypeIdentification" (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);


CREATE TABLE IF NOT EXISTS "User" (
  id VARCHAR(200) PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  lastName VARCHAR(200) NOT NULL,
  identification VARCHAR(200) UNIQUE,
  password VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  address VARCHAR(200) NOT NULL,
  birthday DATE,
  roleId BIGINT REFERENCES "Role"(id),
  baseSalaryCurrency VARCHAR(10),
  baseSalaryValue NUMERIC(19,2),
  typeIdentificationId BIGINT NOT NULL REFERENCES "TypeIdentification"(id)
);


