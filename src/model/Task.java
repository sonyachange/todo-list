package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Serializable {
    private final String name;
    private final String category;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime deadline;
    private TaskStatus status;

    public Task(String name, String category, Integer priority,
                LocalDateTime createdAt, LocalDateTime deadline, TaskStatus status) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name)
                && Objects.equals(category, task.category)
                && Objects.equals(priority, task.priority)
                && Objects.equals(createdAt, task.createdAt)
                && Objects.equals(deadline, task.deadline)
                && status == task.status ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, priority, createdAt, deadline, status);
    }
}
