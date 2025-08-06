CREATE TABLE tb_departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(80) NOT NULL,
    modified_at TIMESTAMPTZ,
    modified_by VARCHAR(80)
);