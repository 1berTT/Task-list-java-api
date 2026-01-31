CREATE TABLE task (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    due_date TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id UUID NOT NULL,
    category_task_id UUID NOT NULL,

    CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_task_category FOREIGN KEY (category_task_id) REFERENCES category_task(id)
);