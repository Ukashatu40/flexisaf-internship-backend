-- V1__Initial_Schema.sql

-- 1. Create the custom Enum type for UserRole
-- Must be created before the table that uses it
CREATE TYPE user_role AS ENUM ('STUDENT', 'TEACHER', 'ADMIN', 'STAFF');

-- 2. Create the Cohort Table
CREATE TABLE cohort (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- 3. Create the PlatformUser Table (The one Hibernate is failing to find)
CREATE TABLE platform_users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(75) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,

    -- Unique constraint defined in JPA
    email VARCHAR(255) NOT NULL UNIQUE,

    date_of_birth DATE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),

    -- References the custom enum type
    user_role user_role NOT NULL,

    bio_notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,

    -- Embedded fields
    city VARCHAR(50),
    country VARCHAR(50),

    -- Foreign Key to Cohort
    cohort_id BIGINT,

    CONSTRAINT fk_cohort
        FOREIGN KEY (cohort_id)
        REFERENCES cohort(id)
);

-- 4. Create the Course Table
CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,

    -- Foreign Key to PlatformUser (teacher)
    teacher_id BIGINT,

    CONSTRAINT fk_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES platform_users(user_id)
);