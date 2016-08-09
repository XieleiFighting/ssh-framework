package com.hades.ssh.service.maintain;

import com.hades.ssh.entity.maintain.TaskDefinition;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:12:03
 * <p>Version: 1.0
 */
public interface DynamicTaskApi {

	public void addTaskDefinition(TaskDefinition taskDefinition);
    public void updateTaskDefinition(TaskDefinition taskDefinition);
    public void deleteTaskDefinition(boolean forceTermination, Long... taskDefinitionIds);


    public void startTask(Long... taskDefinitionIds);
    public void stopTask(boolean forceTermination, Long... taskDefinitionId);
}
