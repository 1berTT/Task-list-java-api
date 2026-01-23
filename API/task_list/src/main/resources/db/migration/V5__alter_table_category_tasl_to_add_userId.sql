ALTER TABLE category_task ADD COLUMN user_id UUID NOT NULL;
ALTER TABLE category_task ADD CONSTRAINT fk_category_task_user FOREIGN KEY (user_id) REFERENCES users(id);