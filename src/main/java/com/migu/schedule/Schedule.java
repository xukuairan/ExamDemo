package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 *类名和方法不能修改
 */
public class Schedule {

    /**
     * 已注册节点
     */
    private static List<Integer> registeredNodes;

    /**
     * 准备调度任务
     */
    private static List<TaskInfo> tasks;

    /**
     * 任务资源消耗
     */
    private static Map<Integer, Integer> taskConsumptionMap;

    static {
        registeredNodes = new CopyOnWriteArrayList<>();
        tasks = new LinkedList<>();
        taskConsumptionMap = new LinkedHashMap<>();
    }

    /**
     * 系统初始化，会清空所有数据，包括已经注册到系统的服务节点信息、以及添加的任务信息，全部都被清理。
     * 执行该命令后，系统恢复到最初始的状态。
     * <p>
     * 初始化成功，返回E001初始化成功。
     * 未做此题返回 E000方法未实现。
     *
     * @return
     */
    public int init() {
        registeredNodes.clear();
        tasks.clear();
        return ReturnCodeKeys.E001;
    }

    /**
     * 输出说明：
     * 注册成功，返回E003:服务节点注册成功。
     * 如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
     * 如果服务节点编号已注册, 返回E005:服务节点已注册。
     *
     * @param nodeId nodeId 服务节点编号, 每个服务节点全局唯一的标识, 取值范围： 大于0；
     * @return
     */
    public int registerNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (registeredNodes.contains(nodeId)) {
            return ReturnCodeKeys.E005;
        }
        Integer node = new Integer(nodeId);
        //该节点被注册，等待添加任务
        registeredNodes.add(node);
        return ReturnCodeKeys.E003;
    }

    /**
     * 功能说明:
     * 1、	从系统中删除服务节点；
     * 2、	如果该服务节点正运行任务，则将运行的任务移到任务挂起队列中，等待调度程序调度。
     * <p>
     * 输出说明：
     * 注销成功，返回E006:服务节点注销成功。
     * 如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
     * 如果服务节点编号未被注册, 返回E007:服务节点不存在。
     *
     * @param nodeId nodeId服务节点编号, 每个服务节点全局唯一的标识, 取值范围： 大于0。
     * @return
     */
    public int unregisterNode(int nodeId) {
        if (nodeId <= 0) {
            return ReturnCodeKeys.E004;
        }
        if (!registeredNodes.contains(nodeId)) {
            return ReturnCodeKeys.E007;
        }
        Integer node = new Integer(nodeId);
        //注销节点
        registeredNodes.remove(node);
        return ReturnCodeKeys.E006;
    }


    /**
     * 功能说明:
     * 将新的任务加到系统的挂起队列中，等待服务调度程序来调度
     *
     * 输出说明：
     * 添加成功，返回E008任务添加成功。
     * 如果任务编号小于等于0, 返回E009:任务编号非法。
     * 如果相同任务编号任务已经被添加, 返回E010:任务已添加
     *
     * @param taskId      任务编号；取值范围： 大于0。
     * @param consumption 资源消耗率
     * @return
     */
    public int addTask(int taskId, int consumption) {
        if(taskId <= 0 ){
            return ReturnCodeKeys.E009;
        }
        if (taskConsumptionMap.containsKey(taskId)){
            return ReturnCodeKeys.E010;
        }
        taskConsumptionMap.put(taskId, consumption);
        return ReturnCodeKeys.E008;
    }

    /**
     * 将在挂起队列中的任务 或 运行在服务节点上的任务删除。
     *
     * 输出说明：
     * 删除成功，返回E011:任务删除成功。
     * 如果任务编号小于等于0, 返回E009:任务编号非法。
     * 如果指定编号的任务未被添加, 返回E012:任务不存在。
     *
     * @param taskId 任务编号；取值范围： 大于0。
     * @return
     */
    public int deleteTask(int taskId) {
        if(taskId <= 0){
            return ReturnCodeKeys.E009;
        }
        if (!taskConsumptionMap.containsKey(taskId)){
            return ReturnCodeKeys.E012;
        }
        taskConsumptionMap.remove(taskId);
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }
}

