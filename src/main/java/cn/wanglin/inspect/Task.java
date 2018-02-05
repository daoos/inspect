package cn.wanglin.inspect;

/**
 * Created by wangl on 2018/01/30.
 */
public class Task {
    TaskStauts status;
    Object     result;
    String     exception;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public TaskStauts getStatus() {
        return status;
    }

    public void setStatus(TaskStauts status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isFinished() {
        return false;
    }

    public static Task initTask() {
        Task task = new Task();
        task.status = TaskStauts.INIT;
        return task;
    }
}
