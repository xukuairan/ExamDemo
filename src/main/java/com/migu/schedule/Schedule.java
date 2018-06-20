package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.sql.Array;
import java.util.*;
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
    private static List<TaskInfo> scheduleTasks;

    /**
     * 任务资源消耗
     */
    private static Map<Integer, Integer> taskConsumptionMap;

    static {
        registeredNodes = new CopyOnWriteArrayList<>();
        scheduleTasks = new LinkedList<>();
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
        scheduleTasks.clear();
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
     * <p>
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
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (taskConsumptionMap.containsKey(taskId)) {
            return ReturnCodeKeys.E010;
        }
        taskConsumptionMap.put(taskId, consumption);
        return ReturnCodeKeys.E008;
    }

    /**
     * 将在挂起队列中的任务 或 运行在服务节点上的任务删除。
     * <p>
     * 输出说明：
     * 删除成功，返回E011:任务删除成功。
     * 如果任务编号小于等于0, 返回E009:任务编号非法。
     * 如果指定编号的任务未被添加, 返回E012:任务不存在。
     *
     * @param taskId 任务编号；取值范围： 大于0。
     * @return
     */
    public int deleteTask(int taskId) {
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        if (!taskConsumptionMap.containsKey(taskId)) {
            return ReturnCodeKeys.E012;
        }
        taskConsumptionMap.remove(taskId);
        return ReturnCodeKeys.E011;
    }

    /**
     * 如果调度阈值取值错误，返回E002调度阈值非法。
     * 如果获得最佳迁移方案, 进行了任务的迁移,返回E013: 任务调度成功;
     * 如果所有迁移方案中，总会有任意两台服务器的总消耗率差值大于阈值。则认为没有合适的迁移方案,返回 E014:无合适迁移方案;
     *
     * @param threshold 系统任务调度阈值，取值范围： 大于0；
     * @return
     */
    public int scheduleTask(int threshold) {
        if (threshold <= 0) {
            return ReturnCodeKeys.E002;
        }
        //所有任务的消耗率综合
        int totalConsumption = 0;


        return ReturnCodeKeys.E013;
    }

    /**
     * 查询获得所有已添加任务的任务状态,  以任务列表方式返回
     * <p>
     * 未做此题返回 E000方法未实现。
     * 如果查询结果参数tasks为null，返回E016:参数列表非法
     * 如果查询成功, 返回E015: 查询任务状态成功;查询结果从参数Tasks返回。
     *
     * @param tasks 保存所有任务状态列表；要求按照任务编号升序排列,
     *              如果该任务处于挂起队列中, 所属的服务编号为-1;
     *              在保存查询结果之前,要求将列表清空.
     * @return
     */
    public int queryTaskStatus(List<TaskInfo> tasks) {
        if (null == tasks || tasks.isEmpty()) {
            return ReturnCodeKeys.E016;
        }
        List<TaskInfo> copyList = new ArrayList<>(tasks);

        return ReturnCodeKeys.E015;
    }


    public static void main(String[] args) {
        //数组
        Integer[] consumptions = {2, 14, 4, 16, 6, 5, 3};

        int[] nodes = {1, 6, 7};
        analysis(consumptions, nodes, 10);

    }

    public static void analysis(Integer[] array, int[] nodes, int threshold) {
        List<Map<Integer, List<Integer>>> results = new ArrayList<>();
        //逆序
        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return -1;
                }
                if (o1 < o2) {
                    return 1;
                }
                return 0;
            }
        });
        List<Integer> consumptionLists = new ArrayList<>();
        //总负载
        int totalConsumptions = 0;
        for (Integer i : array) {
            totalConsumptions += i;
            consumptionLists.add(i);
        }
        System.out.println("总负载：" + totalConsumptions);
        //计算中间zhi
        int average = totalConsumptions % nodes.length == 0 ? totalConsumptions / nodes.length : totalConsumptions / nodes.length + 1;
        System.out.println("平均：" + average);

        //节点最小总负载
        int minNodeConsumptions = 0;
        if (nodes.length > 2) {
            minNodeConsumptions = (totalConsumptions + 10) / nodes.length - 10;
        } else {
            minNodeConsumptions = totalConsumptions - 10;
        }

        System.out.println("min" + minNodeConsumptions);
        // 遍历数组寻找负载均衡方案
        for (int j = 0; j < nodes.length; j++) {
            Map<Integer, List<Integer>> result = new HashMap<>();
            result.put(nodes[j], null);
            System.out.println("当前node：" + nodes[j]);

            List<Integer> nodeConsumptions = new ArrayList<>();
            Iterator<Integer> it = consumptionLists.iterator();
            while(it.hasNext()){
                Integer consumption = it.next();
                System.out.println("consumption：" + consumption);
                if (consumption > minNodeConsumptions) {
                    System.out.println("---");
                    nodeConsumptions.add(consumption);
                    continue;
                }else{

                }
            }
        }
        System.out.println(results);
    }

}

